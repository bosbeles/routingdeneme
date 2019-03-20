package com;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public class MulticastServer extends Thread {
    private InetSocketAddress groupAddress;

    public MulticastServer(InetSocketAddress groupAddress) {
        this.groupAddress = groupAddress;
    }

    private class MulticastHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

            System.out.println("receive " + msg.recipient());
        }
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni = null;
            InetAddress localAddress = null;

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address && networkInterface.getName().equals("wlan1")) {
                        localAddress = address;
                        ni = networkInterface;
                        System.out.println(localAddress + " " + ni.getName());
                        break;
                    }
                }

            }


            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channelFactory(new ChannelFactory<NioDatagramChannel>() {
                        @Override
                        public NioDatagramChannel newChannel() {
                            return new NioDatagramChannel(InternetProtocolFamily.IPv4);
                        }
                    })
                    .localAddress(localAddress, groupAddress.getPort())
                    .option(ChannelOption.IP_MULTICAST_IF, ni)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(NioDatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(new MulticastHandler());
                            ch.eventLoop().scheduleWithFixedDelay(() -> {
                                DatagramPacket packet = new DatagramPacket(Unpooled.copiedBuffer("Deneme".getBytes()), groupAddress);

                                ch.writeAndFlush(packet);
                            }, 0, 5, TimeUnit.SECONDS);
                        }
                    });

            NioDatagramChannel ch = (NioDatagramChannel) b.bind(groupAddress.getPort()).sync().channel();
            ch.joinGroup(groupAddress, ni).sync();
            ch.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        InetSocketAddress groupAddress = new InetSocketAddress("239.255.27.1", 1234);
        new MulticastServer(groupAddress).run();
    }
}
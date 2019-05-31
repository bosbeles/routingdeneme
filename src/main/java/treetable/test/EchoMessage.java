package treetable.test;


import java.util.Objects;

public class EchoMessage extends MyMessage {


    int receiver;

    public EchoMessage(int id, int sender, int receiver, String status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EchoMessage)) return false;
        EchoMessage that = (EchoMessage) o;
        return id == that.id &&
                sender == that.sender &&
                receiver == that.receiver;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver);
    }
}

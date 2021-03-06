package treetable.test.deneme;

import lombok.Data;

import java.util.Objects;

@Data
public class Echo implements Model<Echo> {

    private int data;
    private int response;
    private int sender;
    private int receiver;
    private String status;


    @Override
    public Node<Echo> createNode() {
        Echo echo = new Echo();
        echo.setData(data);
        echo.setSender(sender);
        echo.setReceiver(-1);
        Node<Echo> node = new Node<>(echo);

        return node;
    }


    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0: return data;
            case 1: return response;
            case 2: return sender;
            case 3: return receiver;
            case 4: return status;
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Echo)) return false;
        Echo echo = (Echo) o;
        return data == echo.data &&
                sender == echo.sender &&
                receiver == echo.receiver;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, sender, receiver);
    }

    @Override
    public int compareTo(Echo o) {
        if (data < o.data)
            return -1;
        if (data > o.data)
            return 1;
        if (sender < o.sender)
            return -1;
        if (sender > o.sender)
            return 1;
        if (receiver < o.receiver)
            return -1;
        if (receiver > o.receiver)
            return 1;
        return 0;
    }

}

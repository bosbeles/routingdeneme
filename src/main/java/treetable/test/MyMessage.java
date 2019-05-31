package treetable.test;

public class MyMessage implements Comparable<MyMessage> {
    int id;
    int sender;
    String status;

    @Override
    public int compareTo(MyMessage o) {
        if(id < o.id) {
            return -1;
        } else if (id > o.id) {
            return 1;
        }
        if(sender < o.sender) {
            return -1;
        } else if (sender > o.sender) {
            return  1;
        }
        return 0;
    }
}

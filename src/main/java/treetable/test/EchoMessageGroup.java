package treetable.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EchoMessageGroup extends MyMessage {



    public List<EchoMessage> children = new ArrayList<>();

    public EchoMessageGroup(int id, int sender, String status) {
        this.id = id;
        this.sender = sender;
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EchoMessageGroup)) return false;
        EchoMessageGroup that = (EchoMessageGroup) o;
        return id == that.id &&
                sender == that.sender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender);
    }
}

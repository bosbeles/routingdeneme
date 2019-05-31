package treetable.test.jre;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import java.util.Comparator;

public class ModelNode<T extends Model> extends DefaultMutableTreeTableNode implements Comparable<ModelNode<T>> {

    public ModelNode(T userObject) {
        super(userObject);
    }

    public int getColumnCount() {
        if(getUserObject() != null) {
            return ((T) getUserObject()).getColumnCount();
        }
        return 1;
    }

    public boolean isEditable(int column) {
        return false;
    }

    @Override
    public Object getValueAt(int column) {
        if(getUserObject() != null) {
            return ((T) getUserObject()).getValueAt(column);
        }
        return null;
    }

    @Override
    public int compareTo(ModelNode<T> o) {
        return ((T) userObject).compareTo((T) o.getUserObject());
    }

    public void sort() {
        children.sort(Comparator.comparing(o -> ((ModelNode) o)));
    }
}

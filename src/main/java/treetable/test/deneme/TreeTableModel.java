package treetable.test.deneme;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import java.util.ArrayList;
import java.util.List;

public class TreeTableModel<T extends Model> extends AbstractTreeTableModel {

    private final int columnCount;
    private final Node node;
    List<Node<T>> set;

    public TreeTableModel(int columnCount) {
        this.set = new ArrayList<>();
        this.columnCount = columnCount;
        node = new Node(null);
        set = node.getChildren();


    }

    @Override
    public Object getRoot() {
        return node;
    }

    public void add(T data) {
        Node node = data.createNode();
        int indexOf = set.indexOf(node);
        if(indexOf >= 0) {
            node = set.get(indexOf);
        }
        else {
            set.add(node);
        }

        node.getChildren().add(data);
        if(set.size() == 1) {
            modelSupport.fireNewRoot();
        }

    }

    public void remove(T data) {

    }


    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(Object o, int col) {
        if(o == node) {
            return o;
        }

        if (o instanceof Model) {
            return ((Model) o).getValueAt(col);
        } else if (o instanceof Node) {
            if(((Node) o).getChildren().size() == 1) {
                T data = (T) ((Node) o).getChildren().get(0);
                return data.getValueAt(col);
            }
            else {
                Model data = ((Node) o).getData();
                List<T> children = ((Node) o).getChildren();
                boolean flag = children.stream().map(t-> t.getValueAt(col)).distinct().limit(2).count() <= 1;
                if(flag && !children.isEmpty()) {
                    return children.get(0).getValueAt(col);
                }

                if (data != null) {
                    return data.getValueAt(col);
                }
            }

        }
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof Node) {
            return ((Node) parent).getChildren().get(index);
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if(parent == node) {
            return set.size();
        }
        if (parent instanceof Node) {
            int size = ((Node) parent).getChildren().size();
            return size > 1 ? size : 0;
        }
        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof Node) {
            return ((Node) parent).getChildren().indexOf(child);
        }
        return 0;
    }
}

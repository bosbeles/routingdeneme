package treetable.test.deneme;

public interface Model<T extends Model> extends Comparable<T> {

    Node<T> createNode();

    Object getValueAt(int index);

}

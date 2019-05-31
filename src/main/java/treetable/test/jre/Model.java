package treetable.test.jre;

public interface Model<T extends Model> extends Comparable<T> {

    boolean sameGroup(T data);
    Object getValueAt(int index);
    int getColumnCount();

}
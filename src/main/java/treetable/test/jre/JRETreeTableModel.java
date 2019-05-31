package treetable.test.jre;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import javax.swing.tree.TreePath;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


public class JRETreeTableModel<T extends Model> extends DefaultTreeTableModel {

    private final ModelNode myRoot;

    public JRETreeTableModel(List<String> columnNames) {
        myRoot = new ModelNode(null);
        setRoot(myRoot);
        setColumnIdentifiers(columnNames);
    }

    public void sort() {
        myRoot.sort();

    }

    public void add(T data) {
        Enumeration<? extends MutableTreeTableNode> enumeration = myRoot.children();
        MutableTreeTableNode parent = myRoot;
        while (enumeration.hasMoreElements()) {
            MutableTreeTableNode node = enumeration.nextElement();
            Object userObject = node.getUserObject();
            if (userObject instanceof Model) {
                if (data.sameGroup((Model) userObject)) {
                    if (node.getChildCount() == 0) {
                        parent = node;
                        //node.setUserObject(null);
                        DefaultMutableTreeTableNode newNode = new ModelNode<>((T) userObject);
                        insertNodeInto(newNode, parent, parent.getChildCount());

                    } else {
                        parent = node;
                    }
                    break;
                }
            }
        }


        DefaultMutableTreeTableNode newNode = new ModelNode<>(data);
        insertNodeInto(newNode, parent, parent.getChildCount());
    }
}

package treetable.test;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class MyTreeTableModel extends AbstractTreeTableModel {


    public MyTreeTableModel(EchoMessageCollection root) {
        super(root);
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(Object o, int i) {
        MyMessage m = (MyMessage) o;
        if(i == 0) {
            return m.id;
        }
        else if(i == 1) {
            return m.sender;
        }
         else if (i == 2) {
             if(m instanceof EchoMessage) {
                 return ((EchoMessage) m).receiver;
             }
             else {
                 return "";
             }
        }else if(i == 3) {
             return m.status;
        }
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if(parent instanceof EchoMessageGroup) {
            return ((EchoMessageGroup) parent).children.get(index);
        } else if(parent instanceof EchoMessageCollection) {
            return ((EchoMessageCollection) parent).messages.get(index);
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if(parent instanceof EchoMessageGroup) {
            return ((EchoMessageGroup) parent).children.size();
        } else if(parent instanceof EchoMessageCollection) {
            return ((EchoMessageCollection) parent).messages.size();
        }
        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if(parent instanceof EchoMessageGroup) {
            return ((EchoMessageGroup) parent).children.indexOf(child);

        } else if (parent instanceof EchoMessageCollection) {
            return ((EchoMessageCollection) parent).messages.indexOf(child);
        }
        return -1;

    }
}

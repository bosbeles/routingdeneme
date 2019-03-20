import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class MainClass extends JFrame {

  public MainClass() {
    super("Row Header Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    TableModel tm = new AbstractTableModel() {
      String data[] = { "", "a", "b", "c", "d", "e" };

      String headers[] = { "Row #", "Column 1", "Column 2", "Column 3", "Column 4", "Column 5" };

      public int getColumnCount() {
        return data.length;
      }

      public int getRowCount() {
        return 1000;
      }

      public String getColumnName(int col) {
        return headers[col];
      }

      public Object getValueAt(int row, int col) {
        return data[col] + row;
      }
    };

    TableColumnModel cm = new DefaultTableColumnModel() {
      boolean first = true;

      public void addColumn(TableColumn tc) {
        if (first) {
          first = false;
          return;
        }
        tc.setMinWidth(150);
        super.addColumn(tc);
      }
    };

    TableColumnModel rowHeaderModel = new DefaultTableColumnModel() {
      boolean first = true;

      public void addColumn(TableColumn tc) {
        if (first) {
          tc.setMaxWidth(tc.getPreferredWidth());
          super.addColumn(tc);
          first = false;
        }
      }
    };

    JTable jt = new JTable(tm, cm);
    JTable headerColumn = new JTable(tm, rowHeaderModel);
    jt.createDefaultColumnsFromModel();
    headerColumn.createDefaultColumnsFromModel();

    jt.setSelectionModel(headerColumn.getSelectionModel());

    headerColumn.setBackground(Color.lightGray);
    headerColumn.setColumnSelectionAllowed(false);
    headerColumn.setCellSelectionEnabled(false);

    JViewport jv = new JViewport();
    jv.setView(headerColumn);
    jv.setPreferredSize(headerColumn.getMaximumSize());

    jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    JScrollPane jsp = new JScrollPane(jt);
    jsp.setRowHeader(jv);
    jsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, headerColumn.getTableHeader());
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    new MainClass().setVisible(true);
  }
}
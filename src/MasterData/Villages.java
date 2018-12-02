//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package MasterData;

import DBUtil.DatabaseConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Villages extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel lblVilageName;
    private JTextField txtVilageName;
    private JButton btnAddVilage;
    private JTable tableVilages;
    private JButton btnEditVillage;
    private JButton btnDeleteVillage;
    private DefaultTableModel defaultTableModel;

    public Villages() {
        super("Villages");
        this.$$$setupUI$$$();
        this.add(this.mainPanel);
        String[] column = new String[]{"ID", "Village Name"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.defaultTableModel.setColumnIdentifiers(column);
        this.tableVilages.setModel(this.defaultTableModel);
        this.fillTable();
        this.btnAddVilage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!Villages.this.txtVilageName.getText().equals("")) {
                    int result = DatabaseConnection.addVillage(Villages.this.txtVilageName.getText());
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "Village Added");
                        System.out.println(Villages.this.tableVilages.getRowCount());
                        Villages.this.fillTable();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "Please Enter Village Name", "ERROR", 0);
                }

            }
        });
        this.btnEditVillage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Villages.this.tableVilages.getSelectedRow();

                try {
                    int result = DatabaseConnection.updateVillage(Villages.this.defaultTableModel.getValueAt(rowIndex, 1).toString(), Integer.parseInt(Villages.this.defaultTableModel.getValueAt(rowIndex, 0).toString()));
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "success");
                    } else {
                        JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.btnDeleteVillage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Villages.this.tableVilages.getSelectedRow();

                try {
                    int result = DatabaseConnection.deleteVillage(Integer.parseInt(Villages.this.defaultTableModel.getValueAt(rowIndex, 0).toString()));
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "Deleted");
                        Villages.this.fillTable();
                    } else {
                        JOptionPane.showInternalMessageDialog(Villages.this.getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
    }

    private void fillTable() {
        if (this.defaultTableModel.getRowCount() > 0) {
            for(int i = this.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                this.defaultTableModel.removeRow(i);
            }
        }

        try {
            ResultSet resultSet = DatabaseConnection.getVillages();

            while(resultSet.next()) {
                Object[] data = new Object[]{resultSet.getInt("village_id"), resultSet.getString("village_name")};
                this.defaultTableModel.addRow(data);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void $$$setupUI$$$() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.leftPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Vilage"));
        this.lblVilageName = new JLabel();
        this.lblVilageName.setText("Village Name: ");
        this.leftPanel.add(this.lblVilageName, new GridConstraints(1, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.leftPanel.add(spacer1, new GridConstraints(2, 0, 2, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtVilageName = new JTextField();
        this.leftPanel.add(this.txtVilageName, new GridConstraints(1, 1, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.btnAddVilage = new JButton();
        this.btnAddVilage.setText("Add Vilage");
        this.leftPanel.add(this.btnAddVilage, new GridConstraints(2, 1, 1, 1, 8, 0, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.leftPanel.add(spacer2, new GridConstraints(3, 1, 1, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        this.leftPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer4 = new Spacer();
        this.leftPanel.add(spacer4, new GridConstraints(0, 0, 1, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.rightPanel, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Villages"));
        this.btnEditVillage = new JButton();
        this.btnEditVillage.setText("Edit");
        this.rightPanel.add(this.btnEditVillage, new GridConstraints(1, 0, 1, 1, 4, 0, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnDeleteVillage = new JButton();
        this.btnDeleteVillage.setText("Delete");
        this.rightPanel.add(this.btnDeleteVillage, new GridConstraints(1, 1, 1, 1, 8, 0, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JScrollPane scrollPane1 = new JScrollPane();
        this.rightPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 2, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tableVilages = new JTable();
        this.tableVilages.setAutoResizeMode(3);
        this.tableVilages.setCellSelectionEnabled(false);
        this.tableVilages.setColumnSelectionAllowed(false);
        this.tableVilages.setEditingColumn(1);
        this.tableVilages.setEditingRow(0);
        this.tableVilages.setFillsViewportHeight(true);
        this.tableVilages.setRowSelectionAllowed(true);
        scrollPane1.setViewportView(this.tableVilages);
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customers;

import DBUtil.DatabaseConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Customers extends JInternalFrame {
    private JPanel mainPanel;
    private JLabel lblName;
    private JLabel lblContact;
    private JTextField txtName;
    private JTextField txtContact;
    private JComboBox comboVillage;
    private JTable tableCustomers;
    private JButton addCustomerButton;
    private JLabel lblVillage;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel leftBottomPanel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnView;
    private JTable tablePaymentHistory;
    private JPanel panelPaymentHistory;
    private JLabel lblTotalPaidLabel;
    private JLabel lblTotalPaidText;
    private JLabel lblBalanceText;
    private JLabel lblBalanceLabel;
    private JButton addPaymentButton;
    private JLabel lblCustomerName;
    private JPanel panelLeft;
    private DefaultTableModel defaultTableModel;
    private static DefaultTableModel defaultTableModelPayments;

    public Customers() {
        super("Customers");
        this.$$$setupUI$$$();
        this.add(this.mainPanel);
        final JTextField txtSearchName = (JTextField)this.comboVillage.getEditor().getEditorComponent();
        txtSearchName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Customers.this.comboFilter(txtSearchName.getText(), Customers.this.comboVillage);
                    }
                });
            }
        });
        String[] column = new String[]{"ID", "Name", "Contact", "Village"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.defaultTableModel.setColumnIdentifiers(column);
        this.tableCustomers.setModel(this.defaultTableModel);
        String[] paymentColumns = new String[]{"ID", "Date", "Amount", "Pay. Method", "Pay. Desc."};
        defaultTableModelPayments = new DefaultTableModel(0, 0);
        defaultTableModelPayments.setColumnIdentifiers(paymentColumns);
        this.tablePaymentHistory.setModel(defaultTableModelPayments);
        this.fillTable();
        this.addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String customerName = Customers.this.txtName.getText();
                String customerContact = Customers.this.txtContact.getText();
                String customerVillage = txtSearchName.getText();
                if (customerName.equals("")) {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer name", "Error", 0);
                } else if (!customerContact.equals("") && customerContact.length() != 10) {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer Contact", "Error", 0);
                } else if (customerVillage.equals("")) {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer Village", "Error", 0);
                } else {
                    try {
                        int result = DatabaseConnection.addCustomer(customerName, customerContact, customerVillage);
                        if (result == 1) {
                            JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Customer added successfully");
                            Customers.this.txtName.setText("");
                            Customers.this.txtContact.setText("");
                            Customers.this.fillTable();
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                }

            }
        });
        this.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Customers.this.tableCustomers.getSelectedRow();
                int customerID = Integer.parseInt(Customers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                String customerName = String.valueOf(Customers.this.defaultTableModel.getValueAt(rowIndex, 1));
                String customerContact = String.valueOf(Customers.this.defaultTableModel.getValueAt(rowIndex, 2));
                String customerVillage = String.valueOf(Customers.this.defaultTableModel.getValueAt(rowIndex, 3));
                if (customerID >= 1 && !Customers.this.defaultTableModel.getValueAt(rowIndex, 0).toString().equals("")) {
                    if (customerName.equals("")) {
                        JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer name", "Error", 0);
                    } else if (!customerContact.equals("") && customerContact.length() != 10) {
                        JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer Contact", "Error", 0);
                    } else if (customerVillage.equals("")) {
                        JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please enter valid customer Village", "Error", 0);
                    } else {
                        int result = DatabaseConnection.updateCustomer(customerID, customerName, customerContact, customerVillage);
                        if (result != 0) {
                            JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Customer updated successfully");
                        } else {
                            JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Something went wrong", "Error", 0);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Customer ID cant be blank or less than 1", "Error", 0);
                }

            }
        });
        this.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Customers.this.tableCustomers.getSelectedRow();
                if (rowIndex >= 0) {
                    if (JOptionPane.showConfirmDialog(Customers.this.getContentPane(), "Are you sure?", "Confirm", 2) == 0) {
                        int customerID = Integer.parseInt(Customers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                        int result = DatabaseConnection.deleteCustomer(customerID);
                        if (result != 0) {
                            JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Customer deleted successfully", "Alert", 2);
                            Customers.this.fillTable();
                        } else {
                            JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Something went wrong", "ERROR", 0);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please Select one of customer from right side table", "Warning", 2);
                }

            }
        });
        this.addPaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog ViewSalaryDialog = new Payments("customer");
                Dimension mainPanelSize = Customers.this.mainPanel.getSize();
                ViewSalaryDialog.setSize((int)(mainPanelSize.getWidth() / 2.0D), (int)(mainPanelSize.getHeight() / 2.0D));
                Dimension dialogSize = ViewSalaryDialog.getSize();
                ViewSalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                ViewSalaryDialog.setVisible(true);
            }
        });
        this.btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Customers.this.tableCustomers.getSelectedRow();
                if (rowIndex >= 0) {
                    int customerID = Integer.parseInt(Customers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                    Customers.this.fillPaymentsTable(customerID);
                } else {
                    JOptionPane.showInternalMessageDialog(Customers.this.getContentPane(), "Please Select one of customer from right side table", "Warning", 2);
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
            ResultSet resultSet = DatabaseConnection.getAllCustomers();

            while(resultSet.next()) {
                Object[] data = new Object[]{resultSet.getInt("customer_id"), resultSet.getString("cust_name"), resultSet.getString("cust_contact"), resultSet.getString("cust_village")};
                this.defaultTableModel.addRow(data);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void fillPaymentsTable(int customer_id) {
        if (defaultTableModelPayments.getRowCount() > 0) {
            for(int i = defaultTableModelPayments.getRowCount() - 1; i > -1; --i) {
                defaultTableModelPayments.removeRow(i);
            }
        }

        try {
            ResultSet customerResultSet = DatabaseConnection.getCustomer(customer_id);
            if (customerResultSet.next()) {
                this.lblCustomerName.setText(customerResultSet.getString("cust_name"));

                this.lblBalanceText.setText(String.valueOf(Double.parseDouble(customerResultSet.getString("payable_amount")) - Double.parseDouble(customerResultSet.getString("paid_amount"))));
                ResultSet resultSet = DatabaseConnection.getPayments("customer", customer_id);
                double paid_amount = 0.0;
                while(resultSet.next()) {
                    Object[] data = new Object[]{resultSet.getInt("payment_id"), resultSet.getTimestamp("date"), resultSet.getString("amount"), resultSet.getString("payment_method"), resultSet.getString("payment_desc")};
                    paid_amount += Double.parseDouble(resultSet.getString("amount"));
                    defaultTableModelPayments.addRow(data);
                }

                this.lblTotalPaidText.setText(String.valueOf(paid_amount));
            }
        } catch (SQLException | ParseException var5) {
            var5.printStackTrace();
        }

    }

    public void comboFilter(String enteredText, JComboBox jComboBox) {
        List<String> filterArray = new ArrayList();
        String str1 = "";
        if (!enteredText.equals("")) {
            try {
                ResultSet rs = DatabaseConnection.getVillagesByName(enteredText);

                while(rs.next()) {
                    str1 = rs.getString("village_name");
                    filterArray.add(str1);
                }
            } catch (Exception var6) {
                System.out.println("error" + var6);
            }
        } else {
            filterArray.clear();
        }

        if (filterArray.size() > 0) {
            jComboBox.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            jComboBox.setSelectedItem(enteredText);
            jComboBox.showPopup();
        } else {
            jComboBox.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            jComboBox.hidePopup();
        }

    }

    private void createUIComponents() {
        this.tableCustomers = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3;
            }
        };
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.rightPanel, new GridConstraints(0, 2, 3, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Customers"));
        JScrollPane scrollPane1 = new JScrollPane();
        this.rightPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tableCustomers.setAutoCreateRowSorter(true);
        this.tableCustomers.setAutoResizeMode(4);
        this.tableCustomers.setCellSelectionEnabled(false);
        this.tableCustomers.setEditingColumn(-1);
        this.tableCustomers.setFillsViewportHeight(true);
        this.tableCustomers.setRowSelectionAllowed(true);
        this.tableCustomers.setSelectionBackground(new Color(-16777216));
        this.tableCustomers.setSelectionForeground(new Color(-1));
        this.tableCustomers.setShowHorizontalLines(true);
        this.tableCustomers.setShowVerticalLines(true);
        scrollPane1.setViewportView(this.tableCustomers);
        this.leftBottomPanel = new JPanel();
        this.leftBottomPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        this.rightPanel.add(this.leftBottomPanel, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.leftBottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.btnEdit = new JButton();
        this.btnEdit.setText("Edit");
        this.leftBottomPanel.add(this.btnEdit, new GridConstraints(0, 0, 1, 1, 0, 0, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnDelete = new JButton();
        this.btnDelete.setText("Delete");
        this.btnDelete.setVisible(false);
        this.leftBottomPanel.add(this.btnDelete, new GridConstraints(0, 2, 1, 1, 0, 0, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.addPaymentButton = new JButton();
        this.addPaymentButton.setText("Add Payment");
        this.leftBottomPanel.add(this.addPaymentButton, new GridConstraints(0, 1, 1, 1, 0, 0, 3, 0, (Dimension)null, new Dimension(120, 30), (Dimension)null, 0, false));
        this.panelLeft = new JPanel();
        this.panelLeft.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.panelLeft, new GridConstraints(0, 0, 3, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.panelLeft.add(this.leftPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Customer"));
        this.lblName = new JLabel();
        this.lblName.setText("Name:");
        this.leftPanel.add(this.lblName, new GridConstraints(0, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblContact = new JLabel();
        this.lblContact.setText("Contact No.:");
        this.leftPanel.add(this.lblContact, new GridConstraints(1, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblVillage = new JLabel();
        this.lblVillage.setText("Village:");
        this.leftPanel.add(this.lblVillage, new GridConstraints(2, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtName = new JTextField();
        this.leftPanel.add(this.txtName, new GridConstraints(0, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtContact = new JTextField();
        this.leftPanel.add(this.txtContact, new GridConstraints(1, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.comboVillage = new JComboBox();
        this.comboVillage.setEditable(true);
        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        this.comboVillage.setModel(defaultComboBoxModel1);
        this.leftPanel.add(this.comboVillage, new GridConstraints(2, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.addCustomerButton = new JButton();
        this.addCustomerButton.setText("Add Customer >>");
        this.leftPanel.add(this.addCustomerButton, new GridConstraints(3, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(100, 30), (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.leftPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.leftPanel.add(spacer2, new GridConstraints(1, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelPaymentHistory = new JPanel();
        this.panelPaymentHistory.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.panelLeft.add(this.panelPaymentHistory, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelPaymentHistory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Payment History"));
        JScrollPane scrollPane2 = new JScrollPane();
        this.panelPaymentHistory.add(scrollPane2, new GridConstraints(1, 0, 1, 4, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tablePaymentHistory = new JTable();
        scrollPane2.setViewportView(this.tablePaymentHistory);
        this.lblTotalPaidLabel = new JLabel();
        this.lblTotalPaidLabel.setText("Total Paid:");
        this.panelPaymentHistory.add(this.lblTotalPaidLabel, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblTotalPaidText = new JLabel();
        this.lblTotalPaidText.setText("");
        this.panelPaymentHistory.add(this.lblTotalPaidText, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblBalanceLabel = new JLabel();
        this.lblBalanceLabel.setText("Balance:");
        this.panelPaymentHistory.add(this.lblBalanceLabel, new GridConstraints(2, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblBalanceText = new JLabel();
        this.lblBalanceText.setText("");
        this.panelPaymentHistory.add(this.lblBalanceText, new GridConstraints(2, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblCustomerName = new JLabel();
        this.lblCustomerName.setForeground(new Color(-5817550));
        this.lblCustomerName.setText("");
        this.panelPaymentHistory.add(this.lblCustomerName, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnView = new JButton();
        this.btnView.setText("<<   View");
        this.mainPanel.add(this.btnView, new GridConstraints(0, 1, 1, 1, 0, 0, 3, 3, (Dimension)null, new Dimension(120, 30), (Dimension)null, 0, false));
        this.tableCustomers.setNextFocusableComponent(this.btnEdit);
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}

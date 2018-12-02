//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Drivers;

import DBUtil.DatabaseConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import customers.Payments;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Drivers extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel panelAddDriver;
    private JPanel panelPaymentHistory;
    private JPanel panelDriversInfo;
    private JTable tableDrivers;
    private JTextField txtName;
    private JLabel lblName;
    private JLabel lblContact;
    private JLabel lblAddress;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JButton addDriverButton;
    private JTable tablePaymentHistory;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton viewButton;
    private JPanel panelActions;
    private JLabel lblDriverName;
    private JButton addPaymentButton;
    private JLabel lblTotalPaidText;
    private JLabel lblBalanceLabel;
    private JLabel lblBalanceText;
    private JLabel lblTotalPayments;
    private DefaultTableModel defaultTableModel;
    private DefaultTableModel defaultTableModelPayments;

    public Drivers() {
        super("src/Drivers");
        String[] column = new String[]{"ID", "Name", "Contact", "Address"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.$$$setupUI$$$();
        this.defaultTableModel.setColumnIdentifiers(column);
        this.tableDrivers.setModel(this.defaultTableModel);
        String[] paymentColumns = new String[]{"ID", "Date", "Amount", "Pay. Method", "Pay. Desc."};
        this.defaultTableModelPayments = new DefaultTableModel(0, 0);
        this.defaultTableModelPayments.setColumnIdentifiers(paymentColumns);
        this.tablePaymentHistory.setModel(this.defaultTableModelPayments);
        this.fillTable();
        this.addDriverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String driverName = Drivers.this.txtName.getText();
                String driverContact = Drivers.this.txtContact.getText();
                String driverAddress = Drivers.this.txtAddress.getText();
                if (driverName.equals("")) {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid driver name", "Error", 0);
                } else if (!driverContact.equals("") && driverContact.length() != 10) {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid driver contact", "Error", 0);
                } else if (driverAddress.equals("")) {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid driver address", "Error", 0);
                } else {
                    try {
                        int result = DatabaseConnection.addDriver(driverName, driverContact, driverAddress);
                        if (result == 1) {
                            JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Driver added successfully");
                            Drivers.this.txtName.setText("");
                            Drivers.this.txtContact.setText("");
                            Drivers.this.txtAddress.setText("");
                            Drivers.this.fillTable();
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                }

            }
        });
        this.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Drivers.this.tableDrivers.getSelectedRow();
                int customerID = Integer.parseInt(Drivers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                String customerName = String.valueOf(Drivers.this.defaultTableModel.getValueAt(rowIndex, 1));
                String customerContact = String.valueOf(Drivers.this.defaultTableModel.getValueAt(rowIndex, 2));
                String customerVillage = String.valueOf(Drivers.this.defaultTableModel.getValueAt(rowIndex, 3));
                if (customerID >= 1 && !Drivers.this.defaultTableModel.getValueAt(rowIndex, 0).toString().equals("")) {
                    if (customerName.equals("")) {
                        JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid Driver name", "Error", 0);
                    } else if (!customerContact.equals("") && customerContact.length() != 10) {
                        JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid Driver Contact", "Error", 0);
                    } else if (customerVillage.equals("")) {
                        JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please enter valid Driver Address", "Error", 0);
                    } else {
                        int result = DatabaseConnection.updateDriver(customerID, customerName, customerContact, customerVillage);
                        if (result != 0) {
                            JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Driver updated successfully");
                        } else {
                            JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Something went wrong", "Error", 0);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Driver ID cant be blank or less than 1", "Error", 0);
                }

            }
        });
        this.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Drivers.this.tableDrivers.getSelectedRow();
                int customerID = Integer.parseInt(Drivers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                int result = DatabaseConnection.deleteDriver(customerID);
                if (result != 0) {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Driver deleted successfully", "Alert", 2);
                    Drivers.this.fillTable();
                } else {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Something went wrong", "ERROR", 0);
                }

            }
        });
        this.add(this.$$$getRootComponent$$$());
        this.viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Drivers.this.tableDrivers.getSelectedRow();
                if (rowIndex >= 0) {
                    int customerID = Integer.parseInt(Drivers.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                    Drivers.this.fillPaymentsTable(customerID);
                } else {
                    JOptionPane.showInternalMessageDialog(Drivers.this.getContentPane(), "Please Select one of driver from right side table", "Warning", 2);
                }

            }
        });
        this.addPaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog ViewSalaryDialog = new Payments("driver");
                Dimension mainPanelSize = Drivers.this.mainPanel.getSize();
                ViewSalaryDialog.setSize((int)(mainPanelSize.getWidth() / 2.0D), (int)(mainPanelSize.getHeight() / 2.0D));
                Dimension dialogSize = ViewSalaryDialog.getSize();
                ViewSalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                ViewSalaryDialog.setVisible(true);
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
            ResultSet resultSet = DatabaseConnection.getAllDriver();

            while(resultSet.next()) {
                Object[] data = new Object[]{resultSet.getInt("driver_id"), resultSet.getString("driver_name"), resultSet.getString("driver_contact"), resultSet.getString("driver_address")};
                this.defaultTableModel.addRow(data);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void fillPaymentsTable(int customer_id) {
        if (this.defaultTableModelPayments.getRowCount() > 0) {
            for(int i = this.defaultTableModelPayments.getRowCount() - 1; i > -1; --i) {
                this.defaultTableModelPayments.removeRow(i);
            }
        }

        try {
            ResultSet customerResultSet = DatabaseConnection.getDriver(customer_id);
            if (customerResultSet.next()) {
                this.lblDriverName.setText(customerResultSet.getString("driver_name"));
                this.lblBalanceText.setText(String.valueOf(Double.parseDouble(customerResultSet.getString("payable_amount")) - Double.parseDouble(customerResultSet.getString("paid_amount"))));
                ResultSet resultSet = DatabaseConnection.getPayments("driver", customer_id);
                double paid_amount = 0.0;
                while(resultSet.next()) {
                    Object[] data = new Object[]{resultSet.getInt("payment_id"), resultSet.getTimestamp("date"), resultSet.getString("amount"), resultSet.getString("payment_method"), resultSet.getString("payment_desc")};
                    paid_amount += Double.parseDouble(resultSet.getString("amount"));
                    this.defaultTableModelPayments.addRow(data);
                }

                this.lblTotalPaidText.setText(String.valueOf(paid_amount));
            }
        } catch (SQLException | ParseException var5) {
            var5.printStackTrace();
        }

    }

    private void createUIComponents() {
        this.tableDrivers = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2 || column == 3;
            }
        };
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.leftPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), (String)null));
        this.panelAddDriver = new JPanel();
        this.panelAddDriver.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel.add(this.panelAddDriver, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelAddDriver.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Driver"));
        this.lblName = new JLabel();
        this.lblName.setText("Name");
        this.panelAddDriver.add(this.lblName, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtName = new JTextField();
        this.panelAddDriver.add(this.txtName, new GridConstraints(0, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.lblContact = new JLabel();
        this.lblContact.setText("Contact");
        this.panelAddDriver.add(this.lblContact, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtContact = new JTextField();
        this.txtContact.setText("");
        this.panelAddDriver.add(this.txtContact, new GridConstraints(1, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.lblAddress = new JLabel();
        this.lblAddress.setText("Address");
        this.panelAddDriver.add(this.lblAddress, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtAddress = new JTextField();
        this.panelAddDriver.add(this.txtAddress, new GridConstraints(2, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.addDriverButton = new JButton();
        this.addDriverButton.setText("Add Driver >>");
        this.panelAddDriver.add(this.addDriverButton, new GridConstraints(3, 2, 1, 1, 8, 0, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelAddDriver.add(spacer1, new GridConstraints(1, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelAddDriver.add(spacer2, new GridConstraints(1, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelPaymentHistory = new JPanel();
        this.panelPaymentHistory.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel.add(this.panelPaymentHistory, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelPaymentHistory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Payment History"));
        JScrollPane scrollPane1 = new JScrollPane();
        this.panelPaymentHistory.add(scrollPane1, new GridConstraints(1, 0, 1, 4, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tablePaymentHistory = new JTable();
        this.tablePaymentHistory.setFillsViewportHeight(false);
        scrollPane1.setViewportView(this.tablePaymentHistory);
        this.lblDriverName = new JLabel();
        this.lblDriverName.setForeground(new Color(-5817550));
        this.lblDriverName.setText("");
        this.panelPaymentHistory.add(this.lblDriverName, new GridConstraints(0, 0, 1, 4, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblTotalPayments = new JLabel();
        this.lblTotalPayments.setText("Total Paid:");
        this.panelPaymentHistory.add(this.lblTotalPayments, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblTotalPaidText = new JLabel();
        this.lblTotalPaidText.setText("");
        this.panelPaymentHistory.add(this.lblTotalPaidText, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblBalanceLabel = new JLabel();
        this.lblBalanceLabel.setText("Balance:");
        this.panelPaymentHistory.add(this.lblBalanceLabel, new GridConstraints(2, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblBalanceText = new JLabel();
        this.lblBalanceText.setText("");
        this.panelPaymentHistory.add(this.lblBalanceText, new GridConstraints(2, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.rightPanel, new GridConstraints(0, 2, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), (String)null));
        this.panelDriversInfo = new JPanel();
        this.panelDriversInfo.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.rightPanel.add(this.panelDriversInfo, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelDriversInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Drivers Info."));
        JScrollPane scrollPane2 = new JScrollPane();
        this.panelDriversInfo.add(scrollPane2, new GridConstraints(0, 0, 1, 1, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tableDrivers.setAutoCreateRowSorter(true);
        this.tableDrivers.setFillsViewportHeight(true);
        scrollPane2.setViewportView(this.tableDrivers);
        this.panelActions = new JPanel();
        this.panelActions.setLayout(new FlowLayout(1, 5, 5));
        this.panelDriversInfo.add(this.panelActions, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelActions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.btnEdit = new JButton();
        this.btnEdit.setText("Edit");
        this.panelActions.add(this.btnEdit);
        this.btnDelete = new JButton();
        this.btnDelete.setText("Delete");
        this.btnDelete.setVisible(false);
        this.panelActions.add(this.btnDelete);
        this.addPaymentButton = new JButton();
        this.addPaymentButton.setText("Add Payment");
        this.panelActions.add(this.addPaymentButton);
        this.viewButton = new JButton();
        this.viewButton.setText("<< View");
        this.mainPanel.add(this.viewButton, new GridConstraints(0, 1, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}

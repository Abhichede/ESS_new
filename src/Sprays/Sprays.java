//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Sprays;

import DBUtil.DatabaseConnection;
import Main.DateLabelFormatter;
import Main.Printsupport;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Sprays extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel panelAddSpray;
    private JLabel lblCustomerName;
    private JLabel lblDriverName;
    private JLabel lblVillage;
    private JLabel lblAcres;
    private JLabel lblRate;
    private JLabel lblTotalAmount;
    private JComboBox comboCustomerName;
    private JComboBox comboDriverName;
    private JTextField txtVillageName;
    private JTextField txtAcres;
    private JTextField txtRate;
    private JTextField txtTotalAmount;
    private JButton addSprayButton;
    private JTable tableSprays;
    private JLabel lblCustomerId;
    private JLabel lblDriverId;
    private JPanel panelSprayInfo;
    private JLabel lblFilterBy;
    private JComboBox comboFilterBy;
    private JButton filterButton;
    private JComboBox comboFilterQuery;
    private JLabel lblDate;
    private JDatePickerImpl txtDate;
    private JPanel panelBottomRight;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printButton;
    private JTextField filterQuery;
    UtilDateModel model1 = new UtilDateModel();
    Properties p = new Properties();
    JDatePanelImpl toDatePanel;
    private DefaultTableModel defaultTableModel;
    private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    public Sprays() {
        super("Sprays");
        String[] column = new String[]{"ID", "Date", "Customer", "Driver", "Village", "Acres", "Rate", "Total Amount"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.$$$setupUI$$$();
        this.add(this.$$$getRootComponent$$$());
        this.defaultTableModel.setColumnIdentifiers(column);
        this.tableSprays.setModel(this.defaultTableModel);
        this.p.put("text.today", "Today");
        this.p.put("text.month", "Month");
        this.p.put("text.year", "Year");
        this.fillTable("", "");
        final JTextField txtSearchCustomer = (JTextField)this.comboCustomerName.getEditor().getEditorComponent();
        txtSearchCustomer.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Sprays.comboFilter(txtSearchCustomer.getText(), Sprays.this.comboCustomerName, "customer");
                    }
                });
            }
        });
        final JTextField txtSearchVillage = (JTextField)this.comboFilterQuery.getEditor().getEditorComponent();
        txtSearchVillage.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (Sprays.this.comboFilterBy.getItemAt(Sprays.this.comboFilterBy.getSelectedIndex()).toString().equals("village_name")) {
                            Sprays.comboFilter(txtSearchVillage.getText(), Sprays.this.comboFilterQuery, "village");
                        } else if (Sprays.this.comboFilterBy.getItemAt(Sprays.this.comboFilterBy.getSelectedIndex()).toString().equals("customer")) {
                            Sprays.comboFilter(txtSearchVillage.getText(), Sprays.this.comboFilterQuery, "customer");
                        } else if (Sprays.this.comboFilterBy.getItemAt(Sprays.this.comboFilterBy.getSelectedIndex()).toString().equals("driver")) {
                            Sprays.comboFilter(txtSearchVillage.getText(), Sprays.this.comboFilterQuery, "driver");
                        }

                    }
                });
            }
        });
        final JTextField txtSearchDriver = (JTextField)this.comboDriverName.getEditor().getEditorComponent();
        txtSearchDriver.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Sprays.comboFilter(txtSearchDriver.getText(), Sprays.this.comboDriverName, "driver");
                    }
                });
            }
        });
        txtSearchCustomer.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String customerName = txtSearchCustomer.getText();

                try {
                    ResultSet resultSet = DatabaseConnection.getCustomersByNameSingle(customerName.trim());
                    if (resultSet.next()) {
                        Sprays.this.lblCustomerId.setText(String.valueOf(resultSet.getInt("customer_id")));
                        Sprays.this.txtVillageName.setText(resultSet.getString("cust_village"));
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        txtSearchDriver.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String customerName = txtSearchDriver.getText();

                try {
                    ResultSet resultSet = DatabaseConnection.getDriverByNameSingle(customerName.trim());
                    if (resultSet.next()) {
                        Sprays.this.lblDriverId.setText(String.valueOf(resultSet.getInt("driver_id")));
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
        this.txtAcres.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!Sprays.isNumber(Sprays.this.txtAcres.getText())) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter valid number of acres", "ERROR", 0);
                    Sprays.this.manager.focusPreviousComponent(Sprays.this.txtRate);
                } else {
                    try {
                        ResultSet customerRate = DatabaseConnection.getRate("Customer");
                        if (customerRate.next()) {
                            Double rate = Double.parseDouble(customerRate.getString("rate"));
                            Double acres = Double.parseDouble(Sprays.this.txtAcres.getText());
                            Sprays.this.txtRate.setText(rate + "");
                            Sprays.this.txtTotalAmount.setText(rate * acres + "");
                        }
                    } catch (Exception var5) {
                        var5.printStackTrace();
                    }
                }

            }
        });
        this.addSprayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int customerID = Integer.parseInt(Sprays.this.lblCustomerId.getText());
                int driverID = Integer.parseInt(Sprays.this.lblDriverId.getText());
                String villageName = Sprays.this.txtVillageName.getText();
                String acres = Sprays.this.txtAcres.getText();
                String rate = Sprays.this.txtRate.getText();
                String totalAmount = Sprays.this.txtTotalAmount.getText();
                String date = Sprays.this.txtDate.getJFormattedTextField().getText();
                if (customerID <= 0) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter valid Customer", "ERROR", 0);
                } else if (driverID <= 0) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter valid Driver", "ERROR", 0);
                } else if (villageName.equals("")) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please select valid customer which has village name", "ERROR", 0);
                } else if (acres.equals("")) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter valid number of acres", "ERROR", 0);
                } else if (rate.equals("")) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter valid number of rate", "ERROR", 0);
                } else if (totalAmount.equals("")) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please check for acres & rate to calculate valid total amount", "ERROR", 0);
                } else if (date.equals("")) {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter Date", "ERROR", 0);
                } else {
                    int result = DatabaseConnection.addSpray(customerID, driverID, villageName, acres, rate, totalAmount, date);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Spray added successfully", "Success", 1);
                        Sprays.this.fillTable("", "");
                        txtSearchCustomer.setText("");
                        txtSearchDriver.setText("");
                        Sprays.this.txtVillageName.setText("");
                        Sprays.this.txtAcres.setText("");
                        Sprays.this.txtRate.setText("");
                        Sprays.this.txtTotalAmount.setText("");
                    } else {
                        JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Something went wrong while adding spray", "ERROR", 0);
                    }
                }

            }
        });
        this.filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtSearchVillage.getText().toString().equals("")) {
                    System.out.println("Searched by: "+ comboFilterBy.getItemAt(comboFilterBy.getSelectedIndex()).toString());
                    String filterBy = comboFilterBy.getItemAt(comboFilterBy.getSelectedIndex()).toString();
                    String filterQuery = txtSearchVillage.getText();
                    Sprays.this.fillTable(filterBy, filterQuery);
                } else {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please search something", "Warning", 2);
                }

            }
        });
        this.tableSprays.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                int currentRow = 0;
                if (!Sprays.this.tableSprays.getSelectionModel().isSelectionEmpty()) {
                    int currentRowx = Sprays.this.tableSprays.getSelectedRow();
                    if (String.valueOf(Sprays.this.tableSprays.getValueAt(currentRowx, 5)).equals("")) {
                        JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "This cant be blank...", "Error", 0);
                    } else {
                        Double rate = Double.parseDouble(String.valueOf(Sprays.this.tableSprays.getValueAt(currentRowx, 6)));
                        Double acres = Double.parseDouble(String.valueOf(Sprays.this.tableSprays.getValueAt(currentRowx, 5)));
                        Sprays.this.tableSprays.setValueAt(rate * acres, currentRowx, 6);
                    }
                }

            }
        });
        this.editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Sprays.this.tableSprays.getSelectedRow();
                if (rowIndex >= 0) {
                    try {
                        int sprayID = Integer.parseInt(Sprays.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                        ResultSet customerResultSet = DatabaseConnection.getCustomersByNameSingle(Sprays.this.defaultTableModel.getValueAt(rowIndex, 1).toString());
                        ResultSet driverResultSet = DatabaseConnection.getDriverByNameSingle(Sprays.this.defaultTableModel.getValueAt(rowIndex, 2).toString());
                        int custID = customerResultSet.next() ? customerResultSet.getInt("customer_id") : 0;
                        int driverID = driverResultSet.next() ? driverResultSet.getInt("driver_id") : 0;
                        String acres = Sprays.this.defaultTableModel.getValueAt(rowIndex, 5).toString();
                        String amount = Sprays.this.defaultTableModel.getValueAt(rowIndex, 7).toString();
                        String date = Sprays.this.defaultTableModel.getValueAt(rowIndex, 1).toString();
                        if (custID != 0 && driverID != 0) {
                            int result = DatabaseConnection.updateSpray(sprayID, custID, driverID, acres, amount, date);
                            if (result != 0) {
                                JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Spray updated successfully", "Alert", 2);
                            } else {
                                JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Something went wrong", "ERROR", 0);
                            }
                        } else {
                            JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Something went wrong, \n Customer or driver might be deleted or not found. ", "ERROR", 0);
                        }
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please Select one of spray from right side table", "Warning", 2);
                }

            }
        });
        this.deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Sprays.this.tableSprays.getSelectedRow();
                if (rowIndex >= 0) {
                    if (JOptionPane.showConfirmDialog(Sprays.this.getContentPane(), "Are you sure?", "Warning", 2) == 0) {
                        try {
                            int sprayID = Integer.parseInt(Sprays.this.defaultTableModel.getValueAt(rowIndex, 0).toString());
                            int result = DatabaseConnection.deleteSpray(sprayID);
                            if (result != 0) {
                                JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Spray deleted successfully", "Alert", 2);
                                Sprays.this.fillTable("", "");
                            } else {
                                JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Something went wrong", "ERROR", 0);
                            }
                        } catch (Exception var5) {
                            var5.printStackTrace();
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please Select one of spray from right side table", "Warning", 2);
                    }
                }

            }
        });
        this.printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Sprays.this.comboFilterQuery.getItemCount() > 0) {
                    SprayPrintJob sprayPrintJob = new SprayPrintJob(Sprays.this.comboFilterBy.getItemAt(Sprays.this.comboFilterBy.getSelectedIndex()).toString(), txtSearchVillage.getText());
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(sprayPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var6) {
                        var6.printStackTrace();
                        JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), var6, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Sprays.this.getContentPane(), "Please enter village / customer name in search box!!!", "ERROR", 0);
                }
            }
        });
    }

    public static void comboFilter(String enteredText, JComboBox jComboBox, String searchTable) {
        List<String> filterArray = new ArrayList();
        String str1 = "";
        System.out.println(searchTable);
        ResultSet rs;
        if (searchTable.equals("customer")) {
            if (!enteredText.equals("")) {
                try {
                    rs = DatabaseConnection.getCustomersByName(enteredText);

                    while(rs.next()) {
                        str1 = rs.getString("cust_name");
                        filterArray.add(str1);
                    }
                } catch (Exception var8) {
                    System.out.println("error" + var8);
                }
            } else {
                filterArray.clear();
            }
        } else if (searchTable.equals("driver")) {
            if (!enteredText.equals("")) {
                try {
                    rs = DatabaseConnection.getDriversByName(enteredText);

                    while(rs.next()) {
                        str1 = rs.getString("driver_name");
                        filterArray.add(str1);
                    }
                } catch (Exception var7) {
                    System.out.println("error" + var7);
                }
            } else {
                filterArray.clear();
            }
        } else if (searchTable.equals("village")) {
            if (!enteredText.equals("")) {
                try {
                    rs = DatabaseConnection.getVillagesByName(enteredText);

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

    public static boolean isNumber(String num) {
        boolean flag = false;

        try {
            Double.parseDouble(num);
            flag = true;
        } catch (NumberFormatException var3) {
            var3.printStackTrace();
            flag = false;
        }

        return flag;
    }

    private void fillTable(String filterBy, String query) {
        if (this.defaultTableModel.getRowCount() > 0) {
            for(int i = this.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                this.defaultTableModel.removeRow(i);
            }
        }

        try {
            ResultSet resultSet = !filterBy.equals("") && !query.equals("") ? DatabaseConnection.getFilteredSprays(filterBy, query) : DatabaseConnection.getAllSprays();

            while(resultSet.next()) {
                ResultSet customerResultSet = DatabaseConnection.getCustomer(resultSet.getInt("customer_id"));
                ResultSet driverResultSet = DatabaseConnection.getDriver(resultSet.getInt("driver_id"));
                if (customerResultSet.next() && driverResultSet.next()) {
                    Object[] data = new Object[]{resultSet.getInt("spray_id"), resultSet.getString("date"), customerResultSet.getString("cust_name"), driverResultSet.getString("driver_name"), resultSet.getString("village_name"), resultSet.getString("acres"), resultSet.getString("rate_per_acre"), resultSet.getString("total_amount")};
                    this.defaultTableModel.addRow(data);
                }
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

    }

    private void createUIComponents() {
        this.toDatePanel = new JDatePanelImpl(this.model1, this.p);
        this.txtDate = new JDatePickerImpl(this.toDatePanel, new DateLabelFormatter());
        this.tableSprays = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 1;
            }
        };
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.leftPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelAddSpray = new JPanel();
        this.panelAddSpray.setLayout(new GridLayoutManager(9, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.leftPanel.add(this.panelAddSpray, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelAddSpray.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Spray"));
        this.lblCustomerName = new JLabel();
        this.lblCustomerName.setText("Customer Name");
        this.panelAddSpray.add(this.lblCustomerName, new GridConstraints(0, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDriverName = new JLabel();
        this.lblDriverName.setText("Driver Name");
        this.panelAddSpray.add(this.lblDriverName, new GridConstraints(1, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblVillage = new JLabel();
        this.lblVillage.setText("Village");
        this.panelAddSpray.add(this.lblVillage, new GridConstraints(2, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAcres = new JLabel();
        this.lblAcres.setText("Acres");
        this.panelAddSpray.add(this.lblAcres, new GridConstraints(3, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblRate = new JLabel();
        this.lblRate.setText("Rate / acre");
        this.panelAddSpray.add(this.lblRate, new GridConstraints(4, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblTotalAmount = new JLabel();
        this.lblTotalAmount.setText("Total Amount");
        this.panelAddSpray.add(this.lblTotalAmount, new GridConstraints(5, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.comboCustomerName = new JComboBox();
        this.comboCustomerName.setEditable(true);
        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        this.comboCustomerName.setModel(defaultComboBoxModel1);
        this.panelAddSpray.add(this.comboCustomerName, new GridConstraints(0, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.comboDriverName = new JComboBox();
        this.comboDriverName.setEditable(true);
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        this.comboDriverName.setModel(defaultComboBoxModel2);
        this.panelAddSpray.add(this.comboDriverName, new GridConstraints(1, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtVillageName = new JTextField();
        this.txtVillageName.setEditable(false);
        this.panelAddSpray.add(this.txtVillageName, new GridConstraints(2, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtAcres = new JTextField();
        this.panelAddSpray.add(this.txtAcres, new GridConstraints(3, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtRate = new JTextField();
        this.txtRate.setEditable(false);
        this.panelAddSpray.add(this.txtRate, new GridConstraints(4, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtTotalAmount = new JTextField();
        this.txtTotalAmount.setEditable(false);
        this.panelAddSpray.add(this.txtTotalAmount, new GridConstraints(5, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.addSprayButton = new JButton();
        this.addSprayButton.setText("Add Spray >>");
        this.panelAddSpray.add(this.addSprayButton, new GridConstraints(7, 2, 1, 1, 8, 0, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelAddSpray.add(spacer1, new GridConstraints(8, 2, 1, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelAddSpray.add(spacer2, new GridConstraints(3, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        this.panelAddSpray.add(spacer3, new GridConstraints(3, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblCustomerId = new JLabel();
        this.lblCustomerId.setEnabled(true);
        this.lblCustomerId.setFocusable(false);
        this.lblCustomerId.setText("");
        this.lblCustomerId.setVisible(false);
        this.panelAddSpray.add(this.lblCustomerId, new GridConstraints(0, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDriverId = new JLabel();
        this.lblDriverId.setEnabled(true);
        this.lblDriverId.setFocusable(false);
        this.lblDriverId.setText("");
        this.lblDriverId.setVisible(false);
        this.panelAddSpray.add(this.lblDriverId, new GridConstraints(1, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDate = new JLabel();
        this.lblDate.setText("Date");
        this.panelAddSpray.add(this.lblDate, new GridConstraints(6, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelAddSpray.add(this.txtDate, new GridConstraints(6, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.rightPanel, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelSprayInfo = new JPanel();
        this.panelSprayInfo.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        this.rightPanel.add(this.panelSprayInfo, new GridConstraints(0, 0, 2, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelSprayInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Spray Info."));
        JScrollPane scrollPane1 = new JScrollPane();
        this.panelSprayInfo.add(scrollPane1, new GridConstraints(1, 0, 1, 5, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.tableSprays.setAutoCreateRowSorter(true);
        this.tableSprays.setFillsViewportHeight(true);
        scrollPane1.setViewportView(this.tableSprays);
        this.lblFilterBy = new JLabel();
        this.lblFilterBy.setText("Filter By");
        this.panelSprayInfo.add(this.lblFilterBy, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.comboFilterBy = new JComboBox();
        this.comboFilterBy.setEnabled(true);
        DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("village_name");
        defaultComboBoxModel3.addElement("customer");
        defaultComboBoxModel3.addElement("driver");
        this.comboFilterBy.setModel(defaultComboBoxModel3);
        this.panelSprayInfo.add(this.comboFilterBy, new GridConstraints(0, 1, 1, 1, 8, 0, 2, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.filterButton = new JButton();
        this.filterButton.setText("Filter");
        this.panelSprayInfo.add(this.filterButton, new GridConstraints(0, 3, 1, 2, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.comboFilterQuery = new JComboBox();
        this.comboFilterQuery.setEditable(true);
        DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        this.comboFilterQuery.setModel(defaultComboBoxModel4);
        this.panelSprayInfo.add(this.comboFilterQuery, new GridConstraints(0, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottomRight = new JPanel();
        this.panelBottomRight.setLayout(new FlowLayout(1, 5, 5));
        this.rightPanel.add(this.panelBottomRight, new GridConstraints(2, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottomRight.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.editButton = new JButton();
        this.editButton.setText("Edit");
        this.panelBottomRight.add(this.editButton);
        this.deleteButton = new JButton();
        this.deleteButton.setText("Delete");
        this.panelBottomRight.add(this.deleteButton);
        this.printButton = new JButton();
        this.printButton.setText("Print");
        this.panelBottomRight.add(this.printButton);
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}

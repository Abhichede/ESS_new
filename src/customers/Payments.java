//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customers;

import DBUtil.DatabaseConnection;
import Sprays.Sprays;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Payments extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblCustomerName;
    private JComboBox comboCustomer;
    private JLabel lblAmount;
    private JLabel lblPaymentMethod;
    private JLabel lblPaymentDesc;
    private JTextField txtAmount;
    private JTextField txtPaymentDesc;
    private JLabel lblCustomerId;
    private JComboBox comboPaymentMethod;
    private String payment_for = "";
    private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    public Payments(final String payment_for) {
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        String toFrom = "";
        this.payment_for = payment_for;
        if (payment_for.equals("customer")) {
            toFrom = "From";
        } else if (payment_for.equals("driver")) {
            toFrom = "To";
        }

        this.setTitle("Payment " + toFrom + " " + payment_for);
        this.lblCustomerName.setText(payment_for);
        final JTextField txtSearchName = (JTextField)this.comboCustomer.getEditor().getEditorComponent();
        txtSearchName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Sprays.comboFilter(txtSearchName.getText(), Payments.this.comboCustomer, payment_for);
                    }
                });
            }
        });
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Payments.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Payments.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Payments.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Payments.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onOK() {
        if (this.comboCustomer.getItemAt(this.comboCustomer.getSelectedIndex()) != null) {
            String customerName = this.comboCustomer.getItemAt(this.comboCustomer.getSelectedIndex()).toString();
            String amount = this.txtAmount.getText();
            String paymentMethod = this.comboPaymentMethod.getItemAt(this.comboPaymentMethod.getSelectedIndex()).toString();
            String paymentDesc = this.txtPaymentDesc.getText();
            if (!amount.equals("") && Sprays.isNumber(amount)) {
                if (paymentDesc.equals("")) {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter Payment description", "WARNING", 2);
                } else {
                    try {
                        ResultSet customerResultSet = this.payment_for.equals("customer") ? DatabaseConnection.getCustomersByNameSingle(customerName) : DatabaseConnection.getDriverByNameSingle(customerName);
                        if (customerResultSet.next()) {
                            Double balanceAmount = Double.parseDouble(customerResultSet.getString("payable_amount")) - Double.parseDouble(customerResultSet.getString("paid_amount"));
                            int ok;
                            if (Double.parseDouble(amount) > balanceAmount) {
                                ok = JOptionPane.showConfirmDialog(this.getContentPane(), "Amount is greater than payable amount \n Want to Continue??", "Alert", 2);
                                if (ok == 0) {
                                    this.lblCustomerId.setText("Balance Amount: " + balanceAmount);
                                    int result = DatabaseConnection.addPayments(customerResultSet.getInt(this.payment_for + "_id"), amount, paymentMethod, paymentDesc, this.payment_for);
                                    if (result != 0) {
                                        JOptionPane.showInternalMessageDialog(this.getContentPane(), "Payment Successfully added for " + this.payment_for);
                                        this.dispose();
                                    } else {
                                        JOptionPane.showInternalMessageDialog(this.getContentPane(), "Something went wrong!!!", "ERROR", 0);
                                    }
                                }
                            } else {
                                ok = DatabaseConnection.addPayments(customerResultSet.getInt(this.payment_for + "_id"), amount, paymentMethod, paymentDesc, this.payment_for);
                                if (ok != 0) {
                                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Payment Successfully added for " + this.payment_for);
                                    this.dispose();
                                } else {
                                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Something went wrong!!!", "ERROR", 0);
                                }
                            }
                        }
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid amount", "WARNING", 2);
            }
        } else {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please select one of " + this.payment_for, "WARNING", 2);
        }

    }

    private void onCancel() {
        this.dispose();
    }

    private void $$$setupUI$$$() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonOK = new JButton();
        this.buttonOK.setText("OK");
        panel2.add(this.buttonOK, new GridConstraints(0, 0, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonCancel = new JButton();
        this.buttonCancel.setText("Cancel");
        panel2.add(this.buttonCancel, new GridConstraints(0, 1, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblCustomerName = new JLabel();
        this.lblCustomerName.setText("");
        panel3.add(this.lblCustomerName, new GridConstraints(0, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.comboCustomer = new JComboBox();
        this.comboCustomer.setEditable(true);
        panel3.add(this.comboCustomer, new GridConstraints(0, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.lblAmount = new JLabel();
        this.lblAmount.setText("Amount");
        panel3.add(this.lblAmount, new GridConstraints(1, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblPaymentMethod = new JLabel();
        this.lblPaymentMethod.setText("Payment Method");
        panel3.add(this.lblPaymentMethod, new GridConstraints(2, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblPaymentDesc = new JLabel();
        this.lblPaymentDesc.setText("Payment Desc.");
        panel3.add(this.lblPaymentDesc, new GridConstraints(3, 1, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.comboPaymentMethod = new JComboBox();
        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("CASH");
        defaultComboBoxModel1.addElement("CHEQUE");
        defaultComboBoxModel1.addElement("RTGS/IMPS/NEFT");
        this.comboPaymentMethod.setModel(defaultComboBoxModel1);
        panel3.add(this.comboPaymentMethod, new GridConstraints(2, 2, 1, 1, 8, 0, 2, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtAmount = new JTextField();
        panel3.add(this.txtAmount, new GridConstraints(1, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.txtPaymentDesc = new JTextField();
        panel3.add(this.txtPaymentDesc, new GridConstraints(3, 2, 1, 1, 8, 0, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(2, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(2, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblCustomerId = new JLabel();
        this.lblCustomerId.setText("");
        this.lblCustomerId.setVisible(true);
        panel3.add(this.lblCustomerId, new GridConstraints(0, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return this.contentPane;
    }
}

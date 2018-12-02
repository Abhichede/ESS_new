//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package MasterData;

import DBUtil.DatabaseConnection;
import Sprays.Sprays;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Rates extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel customerRatePanel;
    private JPanel driverratePanel;
    private JTextField txtCustomerRate;
    private JLabel lblCustomerRate;
    private JButton addRateButton;
    private JLabel lblDriverRate;
    private JTextField txtDriverRate;
    private JButton addRateButton1;
    private JLabel lblCustomerPreviousRate;
    private JLabel lblDriverPreviousRate;

    public Rates() {
        super("Rates");
        this.$$$setupUI$$$();
        this.add(this.$$$getRootComponent$$$());
        this.setRates();
        this.addRateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String customerRate = Rates.this.txtCustomerRate.getText();
                if (Sprays.isNumber(customerRate)) {
                    int result = DatabaseConnection.updateRate("Customer", customerRate);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Rate updated for customer", "Success", 1);
                        Rates.this.setRates();
                    } else {
                        JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Something went wrong", "ERROR", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Please enter valid Rate", "ERROR", 0);
                }

            }
        });
        this.addRateButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String customerRate = Rates.this.txtDriverRate.getText();
                if (Sprays.isNumber(customerRate)) {
                    int result = DatabaseConnection.updateRate("Driver", customerRate);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Rate updated for driver", "Success", 1);
                        Rates.this.setRates();
                    } else {
                        JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Something went wrong", "ERROR", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Rates.this.getContentPane(), "Please enter valid Rate", "ERROR", 0);
                }

            }
        });
    }

    public void setRates() {
        try {
            ResultSet customerResultSet = DatabaseConnection.getRate("Customer");
            ResultSet driverResultSet = DatabaseConnection.getRate("Driver");
            if (customerResultSet.next()) {
                this.lblCustomerPreviousRate.setText("Previous Rate: " + customerResultSet.getString("rate"));
            }

            if (driverResultSet.next()) {
                this.lblDriverPreviousRate.setText("Previous Rate: " + driverResultSet.getString("rate"));
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void $$$setupUI$$$() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.customerRatePanel = new JPanel();
        this.customerRatePanel.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.customerRatePanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.customerRatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rate for Customer"));
        this.txtCustomerRate = new JTextField();
        this.customerRatePanel.add(this.txtCustomerRate, new GridConstraints(0, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.lblCustomerRate = new JLabel();
        this.lblCustomerRate.setText("Rate For Customer");
        this.customerRatePanel.add(this.lblCustomerRate, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.addRateButton = new JButton();
        this.addRateButton.setText("Add Rate");
        this.customerRatePanel.add(this.addRateButton, new GridConstraints(0, 3, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.customerRatePanel.add(spacer1, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.customerRatePanel.add(spacer2, new GridConstraints(0, 4, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblCustomerPreviousRate = new JLabel();
        this.lblCustomerPreviousRate.setText("");
        this.customerRatePanel.add(this.lblCustomerPreviousRate, new GridConstraints(1, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.driverratePanel = new JPanel();
        this.driverratePanel.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.driverratePanel, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.driverratePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Rate for Driver"));
        this.lblDriverRate = new JLabel();
        this.lblDriverRate.setText("Rate For Driver");
        this.driverratePanel.add(this.lblDriverRate, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtDriverRate = new JTextField();
        this.driverratePanel.add(this.txtDriverRate, new GridConstraints(0, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 30), (Dimension)null, 0, false));
        this.addRateButton1 = new JButton();
        this.addRateButton1.setText("Add Rate");
        this.driverratePanel.add(this.addRateButton1, new GridConstraints(0, 3, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        this.driverratePanel.add(spacer3, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer4 = new Spacer();
        this.driverratePanel.add(spacer4, new GridConstraints(0, 4, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDriverPreviousRate = new JLabel();
        this.lblDriverPreviousRate.setText("");
        this.driverratePanel.add(this.lblDriverPreviousRate, new GridConstraints(1, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}

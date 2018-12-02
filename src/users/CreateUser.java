//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package users;

import DBUtil.DatabaseConnection;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class CreateUser extends JInternalFrame {
    private static DatabaseConnection dbConnection = new DatabaseConnection();

    public CreateUser() {
        super("Create User", false, true, false, true);
        JPanel container = new JPanel((LayoutManager)null);
        JLabel lblFrameText = new JLabel("Create User");
        lblFrameText.setBounds(270, 10, 500, 20);
        Font frameTextFont = new Font("Serif", 1, 12);
        lblFrameText.setFont(frameTextFont);
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(200, 50, 100, 30);
        final JTextField txtUsername = new JTextField(15);
        txtUsername.setBounds(325, 50, 150, 30);
        lblUsername.setLabelFor(txtUsername);
        txtUsername.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(200, 100, 100, 30);
        final JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBounds(325, 100, 150, 30);
        lblPassword.setLabelFor(txtPassword);
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JButton btnAddUser = new JButton("Add User");
        btnAddUser.setBounds(250, 250, 100, 30);
        InputMap im1 = btnAddUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strUsername = txtUsername.getText().toString();
                String strPassword = txtPassword.getText().toString();
                if (!strUsername.equals("") && !strPassword.equals("")) {
                    int result = CreateUser.dbConnection.addUser(strUsername, strPassword, CreateUser.this.getContentPane());
                    KeyboardFocusManager managerx;
                    if (result == 1) {
                        txtUsername.setText("");
                        txtPassword.setText("");
                        managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        managerx.focusPreviousComponent(txtPassword);
                    } else {
                        managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        managerx.focusPreviousComponent(txtPassword);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CreateUser.this.getContentPane(), "Please fill all the fields.", "Blank Error", 0);
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(txtPassword);
                }

            }
        });
        container.add(lblFrameText);
        container.add(lblUsername);
        container.add(txtUsername);
        container.add(lblPassword);
        container.add(txtPassword);
        container.add(btnAddUser);
        this.add(container);
    }
}

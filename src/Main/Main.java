//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Main;

import DBUtil.DatabaseConnection;
import Drivers.Drivers;
import MasterData.BackupData;
import MasterData.Rates;
import MasterData.Season;
import MasterData.Villages;
import Sprays.Sprays;
import customers.Customers;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import users.CreateUser;
import users.DeleteUser;
import users.UpdateUser;
import users.ViewUsers;

public class Main extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JDesktopPane desktopPane;
    public static JInternalFrame internalFrame;
    static Font frameTextFont;
    static boolean userLoggedInStatus = false;
    static JMenuBar mainMenuBar;
    static JFrame mainFrame;
    static DatabaseConnection dbConnectionObject;

    public Main() {
    }

    public Main(String appName) {
        super(appName);
        this.setSize(screenSize);
        this.setExtendedState(6);
        this.setIconImage((new ImageIcon(this.getClass().getResource("/images/appIcon.png"))).getImage());
        final URL imgURL = this.getClass().getResource("/images/ess.png");
        desktopPane = new JDesktopPane() {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image;
            Image newImage;

            {
                this.image = this.icon.getImage();
                this.newImage = this.image.getScaledInstance(Main.screenSize.width, Main.screenSize.height, 4);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.newImage, 0, 0, this);
            }
        };
        mainMenuBar = this.mainMenuBar();
        this.setContentPane(desktopPane);
    }

    public static void main(String[] a) {
        databaseInit();
        frameTextFont = new Font("Serif", 1, 16);
        mainFrame = new Main("ESS");
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);
        mainMenuBar.setEnabled(false);
        userLogin();
    }

    public static void databaseInit() {
        dbConnectionObject = new DatabaseConnection();

        try {
            Connection con = dbConnectionObject.getDBConnection("", "root", "root");
            int dbStatus = dbConnectionObject.createDatabase("ess_management", con);
            if (dbStatus != 0) {
                System.out.println("Database Created Successfully");
                con = dbConnectionObject.getDBConnection("ess_management", "root", "root");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        dbConnectionObject.createRequiredTables();
    }

    public JMenuBar mainMenuBar() {
        JMenuBar jMainMenuBar = new JMenuBar();
        JMenu menuHome = new JMenu("Home");
        menuHome.setFont(frameTextFont);
        JMenu menuUsers = new JMenu("Users");
        menuUsers.setFont(frameTextFont);
        JMenu menuMasterData = new JMenu("Master Data");
        menuMasterData.setFont(frameTextFont);
        JMenu menuCustomers = new JMenu("Customers");
        menuCustomers.setFont(frameTextFont);
        JMenu menuDrivers = new JMenu("Drivers");
        menuDrivers.setFont(frameTextFont);
        JMenu menuSprays = new JMenu("Sprays");
        menuSprays.setFont(frameTextFont);
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.setFont(frameTextFont);
        itemExit.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        itemExit.setAccelerator(KeyStroke.getKeyStroke(88, 8));
        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.setFont(frameTextFont);
        itemLogout.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        itemLogout.setAccelerator(KeyStroke.getKeyStroke(76, 8));
        JMenuItem itemAddUser = new JMenuItem("Add");
        itemAddUser.setFont(frameTextFont);
        itemAddUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemEditUser = new JMenuItem("Edit");
        itemEditUser.setFont(frameTextFont);
        itemEditUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemViewUser = new JMenuItem("View");
        itemViewUser.setFont(frameTextFont);
        itemViewUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemDeleteUser = new JMenuItem("Delete");
        itemDeleteUser.setFont(frameTextFont);
        itemDeleteUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemVillages = new JMenuItem("Villages");
        itemVillages.setFont(frameTextFont);
        itemVillages.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemSeasons = new JMenuItem("Seasons");
        itemSeasons.setFont(frameTextFont);
        itemSeasons.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemRates = new JMenuItem("Rates");
        itemRates.setFont(frameTextFont);
        itemRates.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemBackupData = new JMenuItem("Backup");
        itemBackupData.setFont(frameTextFont);
        itemBackupData.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemCustomers = new JMenuItem("Customers Actions");
        itemCustomers.setFont(frameTextFont);
        itemCustomers.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemDrivers = new JMenuItem("Drivers Actions");
        itemDrivers.setFont(frameTextFont);
        itemDrivers.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemSprays = new JMenuItem("Sprays Actions");
        itemSprays.setFont(frameTextFont);
        itemSprays.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        menuHome.add(itemLogout);
        menuHome.add(itemExit);
        menuUsers.add(itemAddUser);
        menuUsers.add(itemEditUser);
        menuUsers.add(itemViewUser);
        menuUsers.add(itemDeleteUser);
        menuCustomers.add(itemCustomers);
        menuDrivers.add(itemDrivers);
        menuSprays.add(itemSprays);
        menuMasterData.add(itemVillages);
        menuMasterData.add(itemSeasons);
        menuMasterData.add(itemRates);
        menuMasterData.add(itemBackupData);
        itemLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResultSet currentUser = Main.dbConnectionObject.getCurrentUser();
                int uid = 0;

                try {
                    if (currentUser.next()) {
                        uid = currentUser.getInt("uid");
                    }
                } catch (Exception var8) {
                    var8.printStackTrace();
                }

                Main.dbConnectionObject.updateLoginStatus(false, uid);
                Main.mainFrame.setJMenuBar((JMenuBar)null);
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new Login();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResultSet currentUser = Main.dbConnectionObject.getCurrentUser();
                int uid = 0;

                try {
                    if (currentUser.next()) {
                        uid = currentUser.getInt("uid");
                    }
                } catch (Exception var5) {
                    var5.printStackTrace();
                }

                Main.dbConnectionObject.updateLoginStatus(false, uid);
                System.exit(0);
            }
        });
        itemAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new CreateUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemEditUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new UpdateUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new DeleteUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemViewUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new ViewUsers();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemVillages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Villages();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemSeasons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Season();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemBackupData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new BackupData();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Customers();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemDrivers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Drivers();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemSprays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Sprays();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemRates.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new Rates();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuHome);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuUsers);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuDrivers);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuCustomers);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuSprays);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuMasterData);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        return jMainMenuBar;
    }

    public static void userLogin() {
        System.out.println("User login....");
        internalFrame = new Login();
        internalFrame.setSize(desktopPane.getWidth() / 2, desktopPane.getHeight() / 2);
        Dimension desktopSize = desktopPane.getSize();
        Dimension jInternalFrameSize = internalFrame.getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        internalFrame.setLocation(width, height);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
    }

    public static void changeUserLoginStatus(boolean userLoggedInStatus1) {
        userLoggedInStatus = userLoggedInStatus1;
        if (userLoggedInStatus) {
            mainFrame.setJMenuBar(mainMenuBar);
            internalFrame.dispose();
        }

    }
}

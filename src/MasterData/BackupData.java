package MasterData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.CodeSource;
import java.util.Date;

public class BackupData extends JInternalFrame{
    private JButton button1;
    private JPanel mainPanel;
    private JButton restoreBackupButton;

    public BackupData() {
        super("Backup", true, true, true);
        add(mainPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
                File jarFile = new File(codeSource.getLocation().toURI().getPath());

                String jarDir = jarFile.getParentFile().getPath();

                    String folderPath = jarDir + "/backup";

                    /*NOTE: Creating Folder if it does not exist*/
                    File f1 = new File(folderPath);
                    f1.mkdir();

                    /*NOTE: Creating Path Constraints for backup saving*/
                    /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
                    String savePath = jarDir + "/backup/" + "backup_"+new Date().getTime()+".sql";

                    Backup b = new Backup();

                    byte[] data = b.getData("localhost", "3306", "root",   "root", "ess_management").getBytes();
                    File filedst = new File(savePath);
                    System.out.println("File Path: "+ savePath);

                    if (!filedst.getParentFile().exists())
                        filedst.getParentFile().mkdirs();
                    if (!filedst.exists())
                        filedst.createNewFile();
//                  FileOutputStream dest = new FileOutputStream(filedst);

                    FileOutputStream out = new FileOutputStream(filedst);
                    out.write(data);
                    out.close();
//
//
//                    ZipOutputStream zip = new ZipOutputStream(
//                            new BufferedOutputStream(dest));
//                    zip.setMethod(ZipOutputStream.DEFLATED);
//                    zip.setLevel(Deflater.BEST_COMPRESSION);
//                    zip.putNextEntry(new ZipEntry("data.sql"));
//                    zip.write(data);
//                    zip.close();
//                    dest.close();
                    JOptionPane.showMessageDialog(null, "Back Up Successfully."+"\n"+"For Database: On Dated: ","Database BackUp Wizard",JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Back Up Failed."+"\n"+"For Database: "+"On     Dated: ","Database BackUp Wizard",JOptionPane.INFORMATION_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        restoreBackupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

            /*NOTE: Creating Database Constraints*/
                    String dbName = "ess_management";
                    String dbUser = "root";
                    String dbPass = "root";

            /*NOTE: Creating Path Constraints for restoring*/
                    String restorePath = Backup.getBackUpPath();

            /*NOTE: Used to create a cmd command*/
            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
                    String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
                    Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                    int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
                    if (processComplete == 0) {
                        JOptionPane.showMessageDialog(null, "Successfully restored from SQL : \n" + restorePath);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error at restoring");
                    }


                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}

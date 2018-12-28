package MasterData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Backup
{

    private static ResultSet res;
    private static Connection con;
    private Statement st;

    public String getData(String host, String port, String user, String password, String db) {
        String Mysqlpath = getMysqlBinPath(user, password, db);

        System.out.println(Mysqlpath);
        Process run = null;
        try {
            System.out.println(Mysqlpath + "mysqldump --host=" + host + " --port=" + port + " --user=" + user + " --password=" + password + " --compact --complete-insert --extended-insert " + "--skip-comments --skip-triggers " + db);
            run = Runtime.getRuntime().exec(Mysqlpath + "mysqldump --host=" + host + " --port=" + port + " --user=" + user + " --password=" + password + "  " + "--skip-comments --skip-triggers " + db);
        } catch (IOException ex) {
            // Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
        }


        InputStream in = run.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer temp = new StringBuffer();


        int count;
        int BUFFER = 99999;
        char[] cbuf = new char[BUFFER];
        try {
            while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                temp.append(cbuf, 0, count);
            }
        } catch (IOException ex) {
            Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp.toString();
    }

    // Mysql path is required to locate the bin folder inside it because it contains the Mysqldump which performs a //main role while taking backup.
/*Function to find MySql Path*/
    public  String getMysqlBinPath(String user, String password, String db) {

        createConnection(db, user, password);

        String a = "";

        try {
            res = st.executeQuery("select @@basedir");
            while (res.next()) {
                a = res.getString(1);
            }
        } catch (Exception eee) {
            eee.printStackTrace();
        }
        a = a + "/bin/";
        System.err.println("Mysql path is :" + a);
        return a;
    }

    public static String getBackUpPath() {

        String backUpPath = "";
        JFileChooser fc = null;
        if (fc == null) {
            fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("Mysql Dump File *.sql", "sql", "Mysql"));
        }
        int returnVal = fc.showDialog(null, "Open");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            backUpPath = file.getAbsolutePath();
        }
        return backUpPath;
    }
    public void createConnection(String db, String user, String password){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.print("I am here yaaar");
            e.printStackTrace();
        }
    }

}

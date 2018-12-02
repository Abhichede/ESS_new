//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package DBUtil;

import java.awt.Component;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    static Connection con = null;

    public DatabaseConnection() {
    }

    public Connection getDBConnection(String databaseName, String dbUname, String dbPass) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, dbUname, dbPass);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return con;
    }

    public void closeDBConnection(Connection con) {
        try {
            con.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public int createDatabase(String dbName, Connection con) {
        int creatingStatus = 0;

        try {
            String createStatement = "CREATE DATABASE IF NOT EXISTS " + dbName;
            Statement stmt = con.createStatement();
            creatingStatus = stmt.executeUpdate(createStatement);
            System.out.println(creatingStatus);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return creatingStatus;
    }

    public void createTable(String tableName, String columnNamesWithDatatype) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + "( " + columnNamesWithDatatype + " )";

        try {
            Statement stmt = con.createStatement();
            System.out.println("Creating Table " + tableName);
            System.out.println("Executing : " + createTableStatement);
            stmt.executeUpdate(createTableStatement);
            System.out.println("Table has been created...!!!");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static Connection getCon() {
        return con;
    }

    public void createRequiredTables() {
        String createUser = "uid INT(10) NOT NULL AUTO_INCREMENT, username VARCHAR(20), password VARCHAR(20), login_status boolean DEFAULT false,PRIMARY KEY(uid)";
        String createVillages = "village_id INT(10) NOT NULL AUTO_INCREMENT, village_name VARCHAR(20), PRIMARY KEY (village_id)";
        String createSeasons = "season_id INT(10) NOT NULL AUTO_INCREMENT, start_date VARCHAR(10), end_date VARCHAR(10), is_active VARCHAR(10), PRIMARY KEY (season_id)";
        String createCustomer = "customer_id INT(10) NOT NULL AUTO_INCREMENT, cust_name VARCHAR(50), cust_contact VARCHAR(10), cust_village VARCHAR(50), payable_amount VARCHAR(10) DEFAULT 0, paid_amount VARCHAR(10) DEFAULT 0, PRIMARY KEY (customer_id)";
        String createDriver = "driver_id INT(10) NOT NULL AUTO_INCREMENT, driver_name VARCHAR(50), driver_contact VARCHAR(10), driver_address VARCHAR(50), payable_amount VARCHAR(10) DEFAULT 0, paid_amount VARCHAR(10) DEFAULT 0, PRIMARY KEY (driver_id)";
        String createSprays = "spray_id INT(10) NOT NULL AUTO_INCREMENT, customer_id INT(10), driver_id INT(10), village_name VARCHAR(50), acres VARCHAR(10), rate_per_acre VARCHAR(10), total_amount VARCHAR(10),date VARCHAR(50), created_at timestamp, PRIMARY KEY (spray_id)";
        String createRates = "rate_id INT(10) NOT NULL AUTO_INCREMENT, rate_for VARCHAR(20), rate VARCHAR(20), PRIMARY KEY (rate_id)";
        String createCustomerPayments = "payment_id INT(10) NOT NULL AUTO_INCREMENT, payment_for_id INT(20), amount VARCHAR(20), payment_method VARCHAR(20), payment_desc VARCHAR(20), date timestamp, PRIMARY KEY (payment_id)";
        String createDriverPayments = "payment_id INT(10) NOT NULL AUTO_INCREMENT, payment_for_id INT(20), amount VARCHAR(20), payment_method VARCHAR(20), payment_desc VARCHAR(20), date timestamp, PRIMARY KEY (payment_id)";
        this.createTable("users", createUser);
        this.createTable("villages", createVillages);
        this.createTable("seasons", createSeasons);
        this.createTable("customers", createCustomer);
        this.createTable("drivers", createDriver);
        this.createTable("sprays", createSprays);
        this.createTable("rates", createRates);
        this.createTable("customer_payments", createCustomerPayments);
        this.createTable("driver_payments", createDriverPayments);
    }

    public int addUser(String username, String password, Component component) {
        String insertUser = "INSERT INTO users( username, password) values(?, ?)";
        int result = 0;

        try {
            System.out.println("Adding user to database");
            PreparedStatement insertUserStatement = con.prepareStatement(insertUser);
            insertUserStatement.setString(1, username);
            insertUserStatement.setString(2, password);
            result = insertUserStatement.executeUpdate();
            if (result == 1) {
                System.out.println("User " + username + " added.");
                JOptionPane.showMessageDialog(component, "User has been added successfully!!!", "Success", 1);
            }
        } catch (Exception var7) {
            JOptionPane.showInternalMessageDialog(component, var7, "Something went wrong", 0);
        }

        return result;
    }

    public int updateUser(int uid, String username, String password, Component component) {
        String insertUser = "UPDATE users SET username = ?, password = ? WHERE uid = ?";
        int result = 0;

        try {
            System.out.println("Updating user...");
            PreparedStatement updateUserStatement = con.prepareStatement(insertUser);
            updateUserStatement.setString(1, username);
            updateUserStatement.setString(2, password);
            updateUserStatement.setInt(3, uid);
            result = updateUserStatement.executeUpdate();
            if (result == 1) {
                System.out.println("User " + username + " updated.");
                JOptionPane.showMessageDialog(component, "User has been updated successfully!!!", "Success", 1);
            }
        } catch (Exception var8) {
            JOptionPane.showInternalMessageDialog(component, var8, "Something went wrong", 0);
        }

        return result;
    }

    public int deleteUser(int uid) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM users WHERE uid = " + uid;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public ResultSet getUser(int uid) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users WHERE uid = " + uid + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getUserByUnamePass(String username, String password) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(strGetUser);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultSet;
    }

    public void updateLoginStatus(boolean status, int uid) {
        try {
            String sqlQuery = "UPDATE users SET login_status = ? WHERE uid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, uid);
            preparedStatement.executeUpdate();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public ResultSet getCurrentUser() {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM users WHERE login_status = true";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAllUsers() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users";
            System.out.println("Showing all users...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static int addVillage(String villageName) {
        int result = 0;
        String insertCommand = "INSERT INTO villages(village_name) values(?)";

        try {
            PreparedStatement preparedInsertStatement = con.prepareStatement(insertCommand);
            preparedInsertStatement.setString(1, villageName);
            result = preparedInsertStatement.executeUpdate();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static ResultSet getVillages() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM villages";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getVillagesByName(String string) {
        string = "%" + string + "%";
        String strQuery = "SELECT * FROM villages WHERE village_name LIKE ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static int updateVillage(String villageName, int id) {
        String insertQuery = "UPDATE villages SET village_name = ? WHERE village_id = ?";
        int result = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, villageName);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public static int deleteVillage(int uid) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM villages WHERE village_id = " + uid;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static int addSeason(String start_date, String end_date, String is_active) {
        int result = 0;
        String insertCommand = "INSERT INTO seasons(start_date, end_date, is_active) values(?, ?, ?)";

        try {
            PreparedStatement preparedInsertStatement = con.prepareStatement(insertCommand);
            preparedInsertStatement.setString(1, start_date);
            preparedInsertStatement.setString(2, end_date);
            preparedInsertStatement.setString(3, is_active);
            if(is_active.equals("YES")) {
                updateExistingSeasonActiveStatus();
            }
            result = preparedInsertStatement.executeUpdate();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static int updateSeason(int season_id, String start_date, String end_date, String is_active) {
        String insertCustomer = "UPDATE seasons SET start_date = ?, end_date = ?, is_active = ? WHERE season_id = ?";
        int result = 0;

        try {
            System.out.println("Updating season...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, start_date);
            updateCustomerStatement.setString(2, end_date);
            updateCustomerStatement.setString(3, is_active);
            updateCustomerStatement.setInt(4, season_id);
            if(is_active.equals("YES")) {
                updateExistingSeasonActiveStatus();
            }
            result = updateCustomerStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static ResultSet getActiveSeason() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM seasons WHERE is_active = 'YES' LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getSeasons() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM seasons";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultSet;
    }

    public static int deleteSeason(int season_id) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM seasons WHERE season_id = " + season_id;
            result = statement.executeUpdate(strDeleteUser);


            ResultSet currentSeason = getSeasons();
            int last_id = 1;
            boolean need_change = false;
            while(currentSeason.next()){
                if(currentSeason.getString("is_active").equals("YES")){
                    need_change = false;
                    break;
                }else{
                    last_id = currentSeason.getInt("season_id");
                    need_change = true;
                }
            }

            if(need_change) {
                String insertCustomer = "UPDATE seasons SET is_active = ? WHERE season_id = ?";

                try {
                    System.out.println("Making existing seasons inactive...");
                    PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
                    updateCustomerStatement.setString(1, "YES");
                    updateCustomerStatement.setInt(2, last_id);
                    updateCustomerStatement.executeUpdate();
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    private static void updateExistingSeasonActiveStatus(){
        String insertCustomer = "UPDATE seasons SET is_active = ? WHERE is_active = ?";

        try {
            System.out.println("Making existing seasons inactive...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, "NO");
            updateCustomerStatement.setString(2, "YES");
            updateCustomerStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    public static int addCustomer(String customer_name, String customer_contact, String customer_address) {
        String insertCustomer = "INSERT INTO customers( cust_name, cust_contact, cust_village) values(?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding customer to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer);
            insertCustomerStatement.setString(1, customer_name);
            insertCustomerStatement.setString(2, customer_contact);
            insertCustomerStatement.setString(3, customer_address);
            result = insertCustomerStatement.executeUpdate();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public static int updateCustomer(int cust_id, String customer_name, String customer_contact, String customer_village) {
        String insertCustomer = "UPDATE customers SET cust_name = ?, cust_contact = ?, cust_village = ? WHERE customer_id = ?";
        int result = 0;

        try {
            System.out.println("Updating customer...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, customer_name);
            updateCustomerStatement.setString(2, customer_contact);
            updateCustomerStatement.setString(3, customer_village);
            updateCustomerStatement.setInt(4, cust_id);
            result = updateCustomerStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static void updateTotalPayments(int cust_id, String payable_amount, String payment_for, String amount_type) {
        String insertCustomer = "UPDATE " + payment_for + "s SET " + amount_type + "_amount = ? WHERE " + payment_for + "_id = ?";

        try {
            System.out.println("Updating payments...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, payable_amount);
            updateCustomerStatement.setInt(2, cust_id);
            updateCustomerStatement.executeUpdate();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static int deleteCustomer(int cust_id) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM customer_payments WHERE payment_for_id = " + cust_id);
            String strDeleteUser = "DELETE FROM customers WHERE customer_id = " + cust_id;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static ResultSet getCustomer(int cust_id) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM customers WHERE customer_id = " + cust_id + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getAllCustomers() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM customers";
            System.out.println("Showing all customers...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getCustomersByName(String string) {
        string = "%" + string + "%";
        String strQuery = "SELECT * FROM customers WHERE cust_name LIKE ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getCustomersByNameSingle(String string) {
        String strQuery = "SELECT * FROM customers WHERE cust_name = ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static int addDriver(String driver_name, String driver_contact, String driver_address) {
        String insertCustomer = "INSERT INTO drivers( driver_name, driver_contact, driver_address) values(?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding driver to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer);
            insertCustomerStatement.setString(1, driver_name);
            insertCustomerStatement.setString(2, driver_contact);
            insertCustomerStatement.setString(3, driver_address);
            result = insertCustomerStatement.executeUpdate();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public static int updateDriver(int driver_id, String driver_name, String driver_contact, String driver_village) {
        String insertCustomer = "UPDATE drivers SET driver_name = ?, driver_contact = ?, driver_address = ? WHERE driver_id = ?";
        int result = 0;

        try {
            System.out.println("Updating driver...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, driver_name);
            updateCustomerStatement.setString(2, driver_contact);
            updateCustomerStatement.setString(3, driver_village);
            updateCustomerStatement.setInt(4, driver_id);
            result = updateCustomerStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static int deleteDriver(int cust_id) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM drivers WHERE driver_id = " + cust_id;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static ResultSet getDriver(int cust_id) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM drivers WHERE driver_id = " + cust_id + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getAllDriver() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM drivers";
            System.out.println("Showing all customers...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getDriversByName(String string) {
        string = "%" + string + "%";
        String strQuery = "SELECT * FROM drivers WHERE driver_name LIKE ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getDriverByNameSingle(String string) {
        String strQuery = "SELECT * FROM drivers WHERE driver_name = ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static int addSpray(int cust_id, int driver_id, String village_name, String acres, String rate_per_acre, String total_amount, String date) {
        String insertCustomer = "INSERT INTO sprays( customer_id, driver_id, village_name, acres, rate_per_acre, total_amount, date ) values(?, ?, ?, ?, ?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding spray to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer);
            insertCustomerStatement.setInt(1, cust_id);
            insertCustomerStatement.setInt(2, driver_id);
            insertCustomerStatement.setString(3, village_name);
            insertCustomerStatement.setString(4, acres);
            insertCustomerStatement.setString(5, rate_per_acre);
            insertCustomerStatement.setString(6, total_amount);
            insertCustomerStatement.setString(7, date);
            result = insertCustomerStatement.executeUpdate();
            if (result != 0) {
                ResultSet customer = getCustomer(cust_id);
                if (customer.next()) {
                    Double previousAmount = Double.parseDouble(customer.getString("payable_amount"));
                    updateTotalPayments(cust_id, previousAmount + Double.parseDouble(total_amount) + "", "customer", "payable");
                }

                ResultSet driver = getDriver(driver_id);
                if (driver.next()) {
                    Double previousAmount = Double.parseDouble(driver.getString("payable_amount"));
                    ResultSet rateForDriver = getRate("Driver");
                    if (rateForDriver.next()) {
                        Double rate_for_driver = Double.parseDouble(rateForDriver.getString("rate"));
                        Double totalAmount = Double.parseDouble(acres) * rate_for_driver;
                        updateTotalPayments(driver_id, previousAmount + totalAmount + "", "driver", "payable");
                    }
                }
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        }

        return result;
    }

    public static int updateSpray(int spray_id, int cust_id, int driver_id, String acres, String total_amount, String date) {
        Double previousAcres = 0.0D;
        Double previousRate = 0.0D;
        int result = 0;

        try {
            ResultSet previousSpray = getSpray(spray_id);
            if (previousSpray.next()) {
                previousAcres = Double.parseDouble(previousSpray.getString("acres"));
                previousRate = Double.parseDouble(previousSpray.getString("rate_per_acre"));
            }

            String insertCustomer = "UPDATE sprays SET customer_id = ?, driver_id = ?, acres = ?, total_amount = ?, date = ? WHERE spray_id = ?";
            System.out.println("Updating spray...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setInt(1, cust_id);
            updateCustomerStatement.setInt(2, driver_id);
            updateCustomerStatement.setString(3, acres);
            updateCustomerStatement.setString(4, total_amount);
            updateCustomerStatement.setString(5, date);
            updateCustomerStatement.setInt(6, spray_id);
            System.out.println(updateCustomerStatement.getParameterMetaData());
            result = updateCustomerStatement.executeUpdate();
            if (result != 0) {
                ResultSet customer = getCustomer(cust_id);
                if (customer.next()) {
                    Double previousAmount = Double.parseDouble(customer.getString("payable_amount")) - previousAcres * previousRate;
                    updateTotalPayments(cust_id, previousAmount + Double.parseDouble(total_amount) + "", "customer", "payable");
                }

                ResultSet driver = getDriver(driver_id);
                if (driver.next()) {
                    Double previousAmount = Double.parseDouble(driver.getString("payable_amount"));
                    ResultSet rateForDriver = getRate("Driver");
                    if (rateForDriver.next()) {
                        Double rate_for_driver = Double.parseDouble(rateForDriver.getString("rate"));
                        previousAmount = previousAmount - previousAcres * rate_for_driver;
                        Double totalAmount = Double.parseDouble(acres) * rate_for_driver;
                        updateTotalPayments(driver_id, previousAmount + totalAmount + "", "driver", "payable");
                    }
                }
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

        return result;
    }

    public static int deleteSpray(int spray_id) {
        int result = 0;

        try {
            int customer_id = 0;
            int driver_id = 0;
            Double acresPrevious = 0.0D;
            Double ratePreviousCustomer = 0.0D;
            Double ratePreviousDriver = 0.0D;
            ResultSet sprayResultSet = getSpray(spray_id);
            if (sprayResultSet.next()) {
                customer_id = sprayResultSet.getInt("customer_id");
                driver_id = sprayResultSet.getInt("driver_id");
                acresPrevious = Double.parseDouble(sprayResultSet.getString("acres"));
                ratePreviousCustomer = Double.parseDouble(sprayResultSet.getString("rate_per_acre"));
            }

            ResultSet rateForDriver = getRate("Driver");
            if (rateForDriver.next()) {
                ratePreviousDriver = Double.parseDouble(rateForDriver.getString("rate"));
            }

            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM sprays WHERE spray_id = " + spray_id;
            result = statement.executeUpdate(strDeleteUser);
            if (result != 0) {
                ResultSet customer = getCustomer(customer_id);
                if (customer.next()) {
                    Double previousAmount = Double.parseDouble(customer.getString("payable_amount"));
                    updateTotalPayments(customer_id, previousAmount - ratePreviousCustomer * acresPrevious + "", "customer", "payable");
                }

                ResultSet driver = getDriver(driver_id);
                if (driver.next()) {
                    Double previousAmount = Double.parseDouble(driver.getString("payable_amount"));
                    Double totalAmount = acresPrevious * ratePreviousDriver;
                    updateTotalPayments(driver_id, previousAmount - totalAmount + "", "driver", "payable");
                }
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        return result;
    }

    public static ResultSet getSpray(int cust_id) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM sprays WHERE spray_id = " + cust_id + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getAllSprays() throws SQLException {
        ResultSet resultSet = null;
        ResultSet seasonResultSet = getActiveSeason();
        String start_date = "", end_date = "";
        if(seasonResultSet.next()){
            start_date = seasonResultSet.getString("start_date");
            end_date = seasonResultSet.getString("end_date");
        }
        try {
            String strGetUser = "SELECT * FROM sprays where date BETWEEN '"+ start_date +"' AND '"+ end_date +"'";
            System.out.println("Get All Sprays: "+ strGetUser);
            System.out.println("Showing all sprays...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getFilteredSprays(String filterBy, String query) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM sprays WHERE " + filterBy + " = '" + query + "'";
            ResultSet driver;
            if (filterBy.equals("customer")) {
                driver = getCustomersByNameSingle(query);
                if (driver.next()) {
                    strGetUser = "SELECT * FROM sprays WHERE " + filterBy + "_id = " + driver.getInt("customer_id") + "";
                }
            } else if (filterBy.equals("driver")) {
                driver = getDriverByNameSingle(query);
                if (driver.next()) {
                    strGetUser = "SELECT * FROM sprays WHERE " + filterBy + "_id = " + driver.getInt("driver_id") + "";
                }
            }

            System.out.println("Showing filtered sprays...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet getRate(String rate_for) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM rates WHERE rate_for = '" + rate_for + "' LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public static int updateRate(String rate_for, String rate) {
        String insertCustomer = "UPDATE rates SET rate = ? WHERE rate_for = ?";
        int result = 0;

        try {
            System.out.println("Adding rate to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer);
            insertCustomerStatement.setString(1, rate);
            insertCustomerStatement.setString(2, rate_for);
            result = insertCustomerStatement.executeUpdate();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public static int addPayments(int cust_id, String amount, String paymentMethod, String paymentDesc, String payment_for) {
        String insertCustomer = "INSERT INTO " + payment_for + "_payments( payment_for_id, amount, payment_method, payment_desc) values(?, ?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding payments to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer);
            insertCustomerStatement.setInt(1, cust_id);
            insertCustomerStatement.setString(2, amount);
            insertCustomerStatement.setString(3, paymentMethod);
            insertCustomerStatement.setString(4, paymentDesc);
            result = insertCustomerStatement.executeUpdate();
            if (result != 0) {
                ResultSet driver;
                Double previousAmount;
                if (payment_for.equals("customer")) {
                    driver = getCustomer(cust_id);
                    if (driver.next()) {
                        previousAmount = Double.parseDouble(driver.getString("paid_amount"));
                        updateTotalPayments(cust_id, previousAmount + Double.parseDouble(amount) + "", "customer", "paid");
                    }
                } else if (payment_for.equals("driver")) {
                    driver = getDriver(cust_id);
                    if (driver.next()) {
                        previousAmount = Double.parseDouble(driver.getString("paid_amount"));
                        updateTotalPayments(cust_id, previousAmount + Double.parseDouble(amount) + "", "driver", "paid");
                    }
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return result;
    }

    public static ResultSet getPayments(String payment_for, int customer_id) throws SQLException, ParseException {
        ResultSet resultSet = null;

        ResultSet seasonResultSet = getActiveSeason();
        String start_date = null;
        String end_date = null;
        if(seasonResultSet.next()){
            start_date = seasonResultSet.getString("start_date") + " 00:00:00";
            end_date = seasonResultSet.getString("end_date") + " 00:00:00";
        }

        try {
            String strGetUser = "SELECT * FROM " + payment_for + "_payments WHERE payment_for_id = " + customer_id +" AND date BETWEEN '"+ start_date +"' AND '"+ end_date +"'";
            System.out.println("Get Payments: "+ strGetUser);
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }
}

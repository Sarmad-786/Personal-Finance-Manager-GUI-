package PersonalFinanceManagerWeek2;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conn {
    
    Connection c;
    Statement s;

    public Conn () {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Modern JDBC often doesn't need this line
            
            // Database URL, Username, Password
            String url = "jdbc:mysql://localhost:3306/financial_manager_db";
            String user = "root";   
            String password = "sarmad123"; // *** YAHAN APNA PASSWORD DALEIN ***
            
            c = DriverManager.getConnection(url, user, password);
            s = c.createStatement();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed! Check MySQL server and JDBC Connector JAR.");
            e.printStackTrace();
        }
    }
}
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.sql.*;

public class TestClass {
    static Thread1 thread1 = new Thread1();
    static String insertData = "INSERT INTO [users].[dbo].[users]" +
            "VALUES('userName','userLastName','233323456', 'sfdsgfddd','someStreet','RandomMail@gmail.com','Georgia', 'Tbilisi', 'Tbilisi', '23533')";
    static String sql = "SELECT  * FROM [users].[dbo].[users] ";
    static volatile Connection conn;

    static {
        try {
            conn = SqlConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static volatile Statement stmt;

    static {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static volatile ResultSet rs;

    static {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    static class Thread1 extends Thread {
        public synchronized void run() {
            try {
                conn.setAutoCommit(true);
                stmt.executeUpdate(insertData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static synchronized void main(String[] args) throws SQLException {
        int n = 3; // Number of threads
        for (int i = 0; i < n; i++) {
            System.out.println("Thread " + i + "started: ");
            Thread1 object = new Thread1();
            object.start();
        }
        conn.close();
        stmt.close();
        rs.close();
    }
}



import java.sql.*;

public class Homework8 {
    static Thread1 thread1 = new Thread1();
    static Thread2 thread2 = new Thread2();
    static Thread3 thread3 = new Thread3();
    static String insertData = "INSERT INTO [users].[dbo].[users]" +
            "VALUES('userName','userLastName','233323456', 'sfdsgfddd','someStreet','RandomMail@gmail.com','Georgia', 'Tbilisi', 'Tbilisi', '23533')";
    static String sql = "SELECT  * FROM [users].[dbo].[users] ";
    static String deleteData = "delete from [users].[dbo].[users] where id >0";
    static Connection conn;

    static {
        try {
            conn = SqlConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Statement stmt;

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

    static class Thread2 extends Thread {
        public synchronized void run() {
            System.out.println("Thread 2 started: ");
            try {
                conn.setAutoCommit(false);
                stmt.executeUpdate(deleteData);
                conn.commit();
                System.out.println("Thread2 deleted rows");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    static class Thread1 extends Thread {

        public synchronized void run() {
            System.out.println("Thread 1 started: ");
            try {
                conn.setAutoCommit(false);
                stmt.executeUpdate(insertData);
                conn.commit();
                System.out.println("Thread1 added a row");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class Thread3 extends Thread {

        public synchronized void run() {
            System.out.println("Thread 3 started: ");

            try {
                conn.setAutoCommit(false);
                stmt.executeUpdate(insertData);
                conn.commit();
                System.out.println("Thread3 added a row");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    public static synchronized void main(String[] args) throws SQLException {
        thread1.start();
        thread2.start();
        thread3.start();
        conn.close();
        stmt.close();
        rs.close();

    }
}

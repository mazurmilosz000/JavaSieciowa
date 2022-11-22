package DB;

import java.sql.*;

public class DBConnection {

    private Connection conn;

    public DBConnection() { conn = null; }

    public Connection connectToSqlite() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            System.out.println("Nawiazlem polaczenie");
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // If it doesn't connect to the database return null
        return null;
    }

    public Connection connectionToMySql(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/test",
                    "root",
                    "usbw"
            );
            System.out.println("Połączono z bazą danych");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS Person" +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT," +
                "LastName TEXT," +
                "Age INTEGER)";
        try {
            PreparedStatement prepr = conn.prepareStatement(query);
            prepr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Savepoint getPoint() {
        try {
            Savepoint s = conn.setSavepoint();
            conn.setAutoCommit(false);
            System.out.println("Utworzono punkt przywracania");
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getRollback(Savepoint point) {
        try {
            conn.rollback(point);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            if (!conn.isClosed()) {
                System.out.println("Zakonczono polaczenie");
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

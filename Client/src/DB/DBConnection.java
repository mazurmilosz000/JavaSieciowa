package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

package DB;

import java.sql.*;

public class DBOperations {
    private final Connection conn;

    public DBOperations(Connection c){
        conn = c;
    }

    public void insertPerson(String name, String lastName, int age) {
        PreparedStatement preper;
        try {
            preper = conn.prepareStatement("INSERT INTO Person (Name, LastName, Age) VALUES (?,?,?)");
            preper.setString(1, name);
            preper.setString(2, lastName);
            preper.setInt(3, age);

            int result = preper.executeUpdate();
            System.out.println("Wstawiono " + result);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectPeople() throws SQLException {
        Statement selectStmt = conn.createStatement();
        try {
            ResultSet rs = selectStmt.executeQuery("SELECT * FROM Person");
            System.out.println("ID" + "\t" + "Name" + "\t" + "LastName" + "\t" + "Age" + "\t");

            while (rs.next()) {
                System.out.print(rs.getInt("ID")+ "\t");
                System.out.print(rs.getString("Name")+ "\t" + "\t");
                System.out.print(rs.getString("LastName")+ "\t"+ "\t");
                System.out.print(rs.getInt("Age")+ "\t");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void descendingOrder() throws SQLException {
        Statement selectStmt = conn.createStatement();
        try {
            ResultSet rs = selectStmt.executeQuery("SELECT * FROM Person ORDER BY ID DESC");
            System.out.println("ID" + "\t" + "Name" + "\t" + "LastName" + "\t" + "Age" + "\t");

            while (rs.next()) {
                System.out.print(rs.getInt("ID")+ "\t");
                System.out.print(rs.getString("Name")+ "\t" + "\t");
                System.out.print(rs.getString("LastName")+ "\t"+ "\t");
                System.out.print(rs.getInt("Age")+ "\t");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteFromDB(int id) throws SQLException {
        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM Person WHERE ID=" + id);
            int result = prep.executeUpdate();
            System.out.println("Usunieto " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setName(int id, String newName) {
        try {
            PreparedStatement prep = conn.prepareStatement(String.format("UPDATE Person SET Name = '%s' WHERE ID = %d",
                    newName, id));
            int result = prep.executeUpdate();
            System.out.println("Zmodyfikowano " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
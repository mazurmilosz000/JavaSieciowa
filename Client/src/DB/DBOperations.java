package DB;

import com.mysql.cj.MysqlType;
import com.mysql.cj.xdevapi.Result;

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

    public void getCountPerson(){
        // dla procedury
        try {
            CallableStatement callStm = conn.prepareCall(
                    "{call GetCountPerson(?) }");
            // rejestrowanie wyjsciowego parametru
            callStm.registerOutParameter(1, MysqlType.INT);
            callStm.executeUpdate();
            int number = callStm.getInt(1);
            System.out.println("Liczba rekordów w bazie: " + number);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getPerson(int id) throws SQLException {
        CallableStatement callStm = null;
        try {
            callStm = conn.prepareCall(
                    "{call GetPersons(?,?,?,?)}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        callStm.setInt(1, id);
        callStm.registerOutParameter(2, MysqlType.FIELD_TYPE_VARCHAR);
        callStm.registerOutParameter(3, MysqlType.FIELD_TYPE_VARCHAR);
        callStm.registerOutParameter(4, MysqlType.INT);
        callStm.executeUpdate();

        String name = callStm.getString(2);
        String lastName = callStm.getString(3);
        int age = callStm.getInt(4);
        System.out.printf("Rekord z bazy danych %s %s %d %n", name, lastName, age);

    }

    public void getAllPersons() throws SQLException {

        // wyswietlenie wszystkich rekordow od tylu
        CallableStatement callStm = conn.prepareCall(
                "{call GetAllPersons()}", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet result =callStm.executeQuery();

        result.afterLast();
        while(result.previous()) {
            System.out.printf("%d) %s %s %d %n", result.getInt(1), result.getString(2),
                    result.getString(3), result.getInt(4));
        }
        result.close();

    }
}
import DB.DBConnection;
import DB.DBOperations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.nio.file.Paths;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
//        Client c = new Client("localhost", 5501);
//        c.connection();
//        c.sendMessage();
//        c.disconnect();

//        FileClient fc = new FileClient("localhost", 5501, "pliki\\");
//        fc.connection();
//        fc.sendFileToServer();
//        fc.disconnect();

//        List<String> results = new ArrayList<>();
//        File[] files = new File("C:\\Users\\7280\\Desktop\\pulpit").listFiles();
//
//        for (File file : files) {
//            if (file.isFile()) {
//                results.add(file.getName());
//                System.out.println(file.getName());
//            }
//        }
//        System.out.println();
//        //Creating a File object for directory
//        File directoryPath = new File("C:\\");
//        //List of all files and directories
//        String[] contents = directoryPath.list();
//        System.out.println("List of files and directories in the specified directory:");
//        for(int i = 0; i< Objects.requireNonNull(contents).length; i++) {
//            System.out.println(contents[i]);
//        }
//        System.out.println();
//
//        Path location = Path.of("C:\\");
//        File[] tempFiles = location.toFile().listFiles();
//        for (File f : tempFiles)
//            System.out.println(f.getName() + (f.isFile() ? " plik" : " katalog"));

        DBConnection conn = new DBConnection();
  //      Connection c = conn.connectToSqlite();
        Connection c = conn.connectionToMySql();
        DBOperations operation = new DBOperations(c);
        Savepoint s = conn.getPoint();
//        operation.insertPerson("Maciej", "Nowak", 22);
//        operation.insertPerson("Piotr", "Rubik", 54);
//        operation.insertPerson("Jan", "Kowalski", 16);
//        operation.selectPeople();
        operation.insertPerson("Janusz", "Kowal", 77);
        operation.setName(2, "Jonasz");
        operation.getAllPersons();

        // TODO: fix Exception
        conn.getRollback(s);
        operation.getAllPersons();
//        operation.getCountPerson();
//        operation.getPerson(1);
//        operation.getAllPersons();

   //     conn.createTable();
        conn.disconnect();



    }
}
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FileServer {

    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    String defaultLocation;
    private ServerSocket serverSocket;
    private Socket socket;

    public FileServer(int port, String location) {
        try {
            defaultLocation = location;
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void serverConnect() {
        while (true) {
            System.out.println("Oczekuje na klienta ...");
            try {
                socket = serverSocket.accept();
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Nawiazalem polaczenie");
                System.out.println("Port urzadzenia to: " + socket.getPort());
                System.out.println("IP urzadzenia to: " + socket.getRemoteSocketAddress().toString().replace("/", ""));
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileToClient () {

    }

    public void getFileFromClient () {
        try {
            int fileNameLength = inputStream.readInt();
            if (fileNameLength > 0) {
                System.out.println("Odbieram plik");
                byte[] fileNameBytes = new byte[fileNameLength];
                inputStream.readFully(fileNameBytes);
                String fileName = new String(fileNameBytes, StandardCharsets.UTF_8);
                long fileContentLength = inputStream.readLong();
                if (fileContentLength > 0) {
                    byte[] fileContentBytes = new byte[(int) fileContentLength];
                    inputStream.readFully(fileContentBytes);
                    FileOutputStream fileOut = new FileOutputStream(defaultLocation + fileName);
                    fileOut.write(fileContentBytes);
                    fileOut.flush();
                    fileOut.close();
                } else {
                    System.err.println("plik pusty, nie zapisuje go");
                }
            } else {
                System.out.println("Nie przekazano pliku");
            }
        } catch (IOException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }

    public void disconnectClient() {
        if (!socket.isClosed()) {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();
                System.out.println("Zakonczylem polaczenie z klientem");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

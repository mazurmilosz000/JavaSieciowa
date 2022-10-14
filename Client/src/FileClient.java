import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class FileClient {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    String defaultLocation;
    private Socket socket;
    private String host;
    private int port;

    public FileClient(String h, int p, String l) {
        this.host = h;
        this.port = p;
        defaultLocation = l;
    }

    public void connection() throws IOException {
        try {
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address,port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (socket.isConnected()) {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Nawiazano polaczenie");
        }
    }

    public void sendFileToServer() throws IOException {
        System.out.println("Wysylanie pikow");

        Path location = Path.of(defaultLocation);
        File[] tempFiles = location.toFile().listFiles();
        System.out.println("\nLista plikow mozliwych do przeslania: ");
        for (File f : tempFiles)
            if (f.isFile()) {
                System.out.println(f.getName());
            }

        System.out.println("\nJaki plik chcesz przeslac?: ");
        Scanner scan = new Scanner(System.in);
        String plik = scan.next();
        scan.close();

        try {
            File file = new File(defaultLocation + plik);
            int fileNameLength = file.getName().length();
            byte[] fileNameBytes = file.getName().getBytes(StandardCharsets.UTF_8);

            long fileContentLength = file.length();
            byte[] fileContentByte = new byte[(int) fileContentLength];

            try {
                FileInputStream filein = new FileInputStream(file);
                filein.read(fileContentByte);
                filein.close();

                //przeslanie nazwy pliku
                dataOutputStream.writeInt(fileNameLength);
                dataOutputStream.write(fileNameBytes);

                //przeslanie zawartosci pliku
                dataOutputStream.writeLong(fileContentLength);
                dataOutputStream.write(fileContentByte);
                dataOutputStream.flush();
                System.out.println("Wyslano plik " + plik);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            System.out.println("Podano zla nazwe pliku!");
            e.printStackTrace();
        }

    }

    public void getFileFromServer() {

    }

    public void disconnect () {
        if (!socket.isClosed()) {
            try {
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

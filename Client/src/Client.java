import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private String host;
    private int port;

    public Client(String h, int p) {
        this.host = h;
        this.port = p;
    }

    public void connection() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            socket = new Socket(address,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket.isConnected()) {
            System.out.println("Nawiazano polaczenie");
        }
    }

    public void sendMessage () {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
            String text2 = new String();
            while (!text2.equals("exit")) {
                writer.print("Witaj, podaj imie: ");
                String text = reader.readLine();
                System.out.println(text);
                Scanner scan = new Scanner(System.in);
                text2 = scan.next();
                writer.println(text2);
            }
            disconnect();
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect () {
        if(!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

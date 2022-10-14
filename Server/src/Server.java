import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    public Server(int port) {
        try {
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
                System.out.println("Nawiazalem polaczenie");
                System.out.println("Port urzadzenia to: " + socket.getPort());
                System.out.println("IP urzadzenia to: " + socket.getRemoteSocketAddress().toString().replace("/", ""));
                sendMessageToClient();
                disconnectClient();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendMessageToClient() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
            while (!socket.isClosed()) {
                writer.println("Witaj, podaj imie: ");
                String text = reader.readLine();
                System.out.println(text);
                if (text.contains("exit")) {
                    disconnectClient();
                    writer.close();
                    reader.close();
                }
            }
            disconnectClient();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void disconnectClient() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.out.println("Zakonczylem polaczenie z klientem");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

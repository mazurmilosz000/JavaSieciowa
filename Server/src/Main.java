public class Main {
    public static void main(String[] args) {
//        Server s = new Server(5501);
//        s.serverConnect();
        FileServer fs = new FileServer(5501, "pliki_server\\");
        fs.serverConnect();
        fs.getFileFromClient();
        fs.disconnectClient();

    }
}
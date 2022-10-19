import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

//        FileClient fc = new FileClient("localhost", 5501, "pliki\\");
//        fc.connection();
//        fc.sendFileToServer();
//        fc.disconnect();
        PackageZip pz = new PackageZip();
        File[] files = Paths.get("Client/pliki").toFile().listFiles();
//        pz.packingArchive(files, "Archiwum.zip");
        pz.unpackageArchive(Path.of("Client/pliki_out"), "Archiwum.zip");

    }
}
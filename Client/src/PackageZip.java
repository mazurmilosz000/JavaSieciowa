import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class PackageZip {
    public void packingArchive(File[] files, String zipName) {
        if (files.length == 0)
            System.out.println("Brak plikow");

        Scanner scan = new Scanner(System.in);
        List<Integer> listElements = new ArrayList<>();

        System.out.println("Lista plikow mozliwych do spakowania: ");
        for (int i=0; i< files.length; i++)
            System.out.println(i+1 + ": " + files[i]);

        System.out.println("Podaj pliki, ktore chcesz spakowac: ");
        while(true) {
            try {
                int nextEl = scan.nextInt();
                if (nextEl >= 1 && nextEl <= files.length)
                    listElements.add(nextEl);
                else
                    break;
            } catch (Exception e) {
                break;
            }
        }
        File[] newFiles = new File[listElements.size()];
        for(int i=0; i<listElements.size(); i++)
            newFiles[i] = files[listElements.get(i) -1];

        try {
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName));
            zipOut.setLevel(Deflater.NO_COMPRESSION);
            for (File f : newFiles) {
                ZipEntry zipE = new ZipEntry(f.getName());
                zipOut.putNextEntry(zipE);
                FileInputStream fileIn = new FileInputStream(f);
                zipOut.write(fileIn.readAllBytes());
                fileIn.close();
                zipOut.closeEntry();
            }
            zipOut.flush();
            zipOut.close();
            System.out.println("Zapisano plik do archiwum ZIP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unpackageArchive(Path dlocation, String zipName) {
        try {
            try {
                ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipName));
                ZipEntry zipE;
                while ((zipE = zipIn.getNextEntry()) != null) {
                    FileOutputStream fOut =
                            new FileOutputStream(
                                    new File(dlocation.toString(), zipE.getName()));
                    fOut.write(zipIn.readAllBytes());
                    fOut.flush();
                    fOut.close();
                    zipIn.closeEntry();
                }
                zipIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TestDownloadManager {

    public static void main(String[] args) throws IOException {
        Console console = System.console();
        if (Objects.isNull(console)) {
            return;
        }
        String src = console.readLine("Enter source file path");
        String dest = console.readLine("Enter destination file path");
        String speedInChars = console.readLine("Enter speed of copying, in chars (approximately value of 8 or so is appropriate for my file). If you do not want to input speed, type 0");
        if (Integer.valueOf(speedInChars) == 0) {
            speedInChars = "8";
        }
        String duration = console.readLine("Enter time in milliseconds as an upper boundary of this program operation (recommended value for my file is 2-3)");
        try {
            download(src,dest,Integer.valueOf(speedInChars),Integer.valueOf(duration));
        } catch (NoSuchFileException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void download (String source, String destination, Integer speedInChars, Integer duration) throws IOException {
        Path path = Paths.get(destination);
        if (!path.toFile().exists() || !path.toFile().isFile()) {
            Files.createFile(path);
        }
        if (!Paths.get(source).toFile().exists()) {
            throw new NoSuchFileException("No source file found!");
        }
        try (FileReader readerSource = new FileReader(source); FileReader readerDestination = new FileReader(destination);
        FileWriter writer = new FileWriter(destination, true)) {
            int dataSrc;
            char [] cbuffer = new char[speedInChars];
            int dataDest = 0;
            while ((dataSrc = readerDestination.read(cbuffer)) != -1) {
                dataDest += dataSrc;
            }
            readerSource.skip(dataDest);
            long start = System.currentTimeMillis();
            while (((dataSrc = readerSource.read(cbuffer)) != -1 && (System.currentTimeMillis() - start) <= duration) || readerDestination.read() == -1) {
                writer.write(cbuffer, 0, dataSrc );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


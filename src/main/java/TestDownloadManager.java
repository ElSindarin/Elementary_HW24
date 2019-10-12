import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class TestDownloadManager {

    public static void main(String[] args) {
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
        download(src,dest,Integer.valueOf(speedInChars),Integer.valueOf(duration));
    }

    public static void download (String source, String destination, Integer speedInChars, Integer duration) {
        try (FileReader readerSource = new FileReader(source); FileReader readerDestination = new FileReader(destination);
        FileWriter writer = new FileWriter(destination, true)) {
            int dataSrc;
            char [] cbuffer = new char[speedInChars];
            int dataDest = 0;
            while ((dataSrc = readerDestination.read(cbuffer)) != -1) {
                dataDest += dataSrc;
            }
            readerSource.read(new char[dataDest]);
            long start = System.currentTimeMillis();
            while ((dataSrc = readerSource.read(cbuffer)) != -1 && (System.currentTimeMillis() - start) <= duration) {
                writer.write(cbuffer, 0, dataSrc );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


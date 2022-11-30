import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class QuickMain {
    private static final String dirName = "inputs/";
    private static final String realName = "outputs/";
    private static final String outName = "mouts/";

    public static void main(String[] args) throws IOException {
        File fileName = new File(dirName);
        File[] fileList = fileName.listFiles();

        assert fileList != null;
        for (File file : fileList) {
            Main.main(new String[]{String.valueOf(file), outName + (file.getPath().substring(7)) });
        }

        compare();
    }

    private static void compare() throws IOException {
        File fileName = new File(realName);
        File[] files = fileName.listFiles();

        assert files != null;
        for (File file : files) {
            long line = filesCompareByLine(Path.of(outName + (file.getPath().substring(8))),
                    Path.of(file.toURI()));
            if (line == -1L){
                System.out.println("Input " + file + ": True" );
            }else {
                System.out.println("Input " + file + ": False. Wrong line is " + line);
            }
        }
    }

    private static long filesCompareByLine(Path path1, Path path2) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (!line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            }
            else {
                return lineNumber;
            }
        }
    }
}


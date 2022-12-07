import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Reader {

    private File file;
    private List<String> lines;

    public Reader(final File file) {
        this.file = file;
        this.lines = cleanLines(read());
    }

    private List<String> read() {
        List<String> lines = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) lines.add(input.nextLine());
            input.close();
        } catch (FileNotFoundException e) {
            System.out.printf("Não foi possível abrir o arquivo '%s'!\n", file.getName());
            System.exit(0);
        }

        return lines;
    }

    private List<String> cleanLines(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace("<", "").replace(">", ""))
                .collect(Collectors.toList());
    }

    public List<String> getLines() {
        return lines;
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    private File file;

    public Reader(final File file) {
        this.file = file;
    }

    public List<String> read() {
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

}

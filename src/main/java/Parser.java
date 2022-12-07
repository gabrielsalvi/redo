import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private final JSONParser jsonParser;
    private final List<Integer> firstColumnValues;
    private final List<Integer> secondColumnValues;

    public Parser(String fileName) {
        this.jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(fileName)) {
            JSONObject parsedJson = (JSONObject) jsonParser.parse(fileReader); //Read JSON file
            JSONObject initialObject = (JSONObject) parsedJson.get("INITIAL");

            this.firstColumnValues = parseColumnValues(initialObject.get("A"));
            this.secondColumnValues = parseColumnValues(initialObject.get("B"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> parseColumnValues(Object column) {
        String columnData = column.toString();
        String[] columnValues = columnData.replace("[", "").replace("]", "").split(",");
        return Arrays.stream(columnValues).map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<Integer> getFirstColumnValues() {
        return firstColumnValues;
    }

    public List<Integer> getSecondColumnValues() {
        return secondColumnValues;
    }
}

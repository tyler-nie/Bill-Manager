package persistence;

import model.Group;
import model.Bill;
import model.Person;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads group from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads group from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Group read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGroup(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses group from JSON object and returns it
    private Group parseGroup(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Group g = new Group(name);
        addPersons(g, jsonObject);
        addBills(g, jsonObject);
        return g;
    }

    // MODIFIES: Group
    // EFFECTS: parses people from JSON object and adds them to the group
    private void addPersons(Group g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("people");
        for (Object json : jsonArray) {
            JSONObject nextPerson = (JSONObject) json;
            addPerson(g, nextPerson);
        }
    }

    // MODIFIES: Group
    // EFFECTS: parses person from JSON object and adds it to the group
    private void addPerson(Group g, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        g.addPerson(name);
    }

    // MODIFIES: Group
    // EFFECTS: parses bills from JSON object and adds them to the group
    private void addBills(Group g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("people");
        for (Object json : jsonArray) {
            JSONObject nextBill = (JSONObject) json;
            addBill(g, nextBill);
        }
    }

    // MODIFIES: Group
    // EFFECTS: parses bill from JSON object and adds it to the group
    private void addBill(Group g, JSONObject jsonObject) {
        int payeeId = jsonObject.getInt("payee id");
        int numOfPeople = jsonObject.getInt("number of people");
        int cost = jsonObject.getInt("cost");
        g.addBill(g.getPersons().get(payeeId), cost, numOfPeople);
    }
}

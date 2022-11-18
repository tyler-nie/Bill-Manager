package ui;

import model.Group;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;

public class GroupManagerWindow extends JFrame {
    private static final String JSON_STORE = "./data/group.json";
    private Group group;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame window;

    //EFFECTS: Starts
    public GroupManagerWindow() {
        runGroup();
    }


    private void runGroup() {
        init();
    }

    //EFFECTS: Initializes
    private void init() {
        group = new Group();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Frame initialization
        window = new JFrame();
        window.setTitle("Group Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
        window.getContentPane().setBackground(new Color(0x9098dd));

        // ImageIcon image = nweImageIcon("file name");
        // frame.setIconImage(image.getImage());
    }


}

package ui;

import exceptions.NegativeAmountException;
import model.EventLog;
import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GroupManagerWindow extends JFrame {
    private static final String JSON_SOURCE = "./data/group.json";
    private Group group;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame window;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private final int buttonWidth = 300;
    private final int buttonHeight = 50;
    private final int alignX = 0;
    private JPanel main;
    private JScrollPane personPane;
    private JScrollPane billPane;

    private DefaultTableModel peopleModel;
    private DefaultTableModel billModel;
    private JTable peopleTable;
    private JTable billsTable;

    /// Effects: initializes the GUI
    public GroupManagerWindow() {
        startUp();
        mainPage();
    }

    // Modifies: this, window, group
    // Effects: creates and initializes the application window
    //         also initializes the GUI and data for start up
    public void mainPage() {
        window = new JFrame("Group Manager");

        initialize();

        window.setResizable(false);
        window.pack();
        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveGroupDialog();
            }
        });
    }

    // Modifies: this
    // Effects: initializes main page and adds buttons to main page for group app
    public void initialize() {
        main = new JPanel();
        main.setLayout(null);
        main.setBackground(new Color(0x807F80));
        window.setContentPane(main);
        personPane = new JScrollPane();
        billPane = new JScrollPane();

        billModel = new DefaultTableModel();
        billModel.addColumn("Bill ID");
        billModel.addColumn("Payee ID");
        billModel.addColumn("# of People for Bill");
        billModel.addColumn("Cost");

        peopleModel = new DefaultTableModel();
        peopleModel.addColumn("Person ID");
        peopleModel.addColumn("Name");
        display(peopleModel, billModel);

        try {
            BufferedImage img = ImageIO.read(new File("data/groupImage.png"));
            JLabel imgLoc = new JLabel(new ImageIcon(img));
            imgLoc.setBounds(alignX,-350, 300, HEIGHT);
            window.add(imgLoc);
        } catch (IOException e) {
            // do nothing -- when unable to get image
        }
        addButtons();
    }

    // Modifies: this
    // Effects: adds buttons
    public void addButtons() {
        addPersonButton();
        addBillButton();
        addSaveButton();
        addLoadButton();

        JButton quitButton = new JButton("Quit Program");
        quitButton.addActionListener(e -> saveGroupDialog());
        quitButton.setBounds(alignX, 550, buttonWidth, buttonHeight);
        main.add(quitButton);
    }

    // Modifies: this
    // Effects: displays persons and groups
    public void display(DefaultTableModel modelPeople, DefaultTableModel modelBill) {
        displayBills(modelBill);
        displayPeople(modelPeople);
    }

    // Modifies: this
    // Effects: displays People and the details of each person
    public void displayPeople(DefaultTableModel model) {
        for (Person p : group.getPersons()) {
            model.addRow(new Object[]{Integer.toString(p.getID()), p.getName()});
        }
        peopleTable = new JTable(model);
        personPane.add(peopleTable);
        personPane.setViewportView(peopleTable);
        window.add(personPane);
    }

    // Modifies: this
    // Effects: shows dialog and creates new Person with given name
    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
    public void addPersonButton() {
        JButton addNewPerson = new JButton("Add New Person");
        addNewPerson.addActionListener(e -> {
            JPanel addPersonPanel = new JPanel();
            JTextField nameField = new JTextField(20);

            addPersonPanel.add(new JLabel("What is the name of the person?"));
            addPersonPanel.add(nameField);
            int dialog = JOptionPane.showConfirmDialog(null, addPersonPanel,
                    "Person Name", JOptionPane.OK_CANCEL_OPTION);

            if (dialog == JOptionPane.OK_OPTION) {
                group.addPerson(nameField.getText());
            }
        });
        addNewPerson.setBounds(alignX, 350, buttonWidth, buttonHeight);
        window.add(addNewPerson);
    }

    // Modifies: this
    // Effects: displays bills and the details of the bill
    public void displayBills(DefaultTableModel model) {
        for (Bill b : group.getBills()) {
            model.addRow(new Object[]{Integer.toString(b.getID()), Integer.toString(b.getPersonID()),
                    Integer.toString(b.getNumberOfPeople()), Double.toString(b.getCost())});
        }
        billsTable = new JTable(model);
        billPane.add(billsTable);
        window.add(billPane);
    }

    // Modifies: this
    // Effects: shows dialog and creates new Bill with given name
    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
    public void addBillButton() {
        JButton addNewBill = new JButton("Add New Bill");
        addNewBill.addActionListener(e -> {
            JPanel addBillPanel = new JPanel();
            JTextField payeeField = new JTextField(20);
            JTextField costField = new JTextField(20);
            JTextField numField = new JTextField(20);

            addBillPanel.add(new JLabel("What is the id of the person who payed?"));
            addBillPanel.add(payeeField);
            addBillPanel.add(Box.createHorizontalStrut(15)); // a spacer

            addBillPanel.add(new JLabel("What is the cost of the bill?"));
            addBillPanel.add(costField);
            addBillPanel.add(Box.createHorizontalStrut(15)); // a spacer

            addBillPanel.add(new JLabel("How many people was the bill for?"));
            addBillPanel.add(numField);
            addBillPanel.add(Box.createHorizontalStrut(15)); // a spacer

            int dialog = JOptionPane.showConfirmDialog(null, addBillPanel,
                    "Add Bill Details", JOptionPane.OK_CANCEL_OPTION);

            if (dialog == JOptionPane.OK_OPTION) {
                if (group.isPersonInGroup(Integer.parseInt(payeeField.getText()))) {
                    Person payee = group.getPersons().get(Integer.parseInt(payeeField.getText()));
                    try {
                        group.addBill(payee,
                                Double.parseDouble(costField.getText()),
                                Integer.parseInt(numField.getText()));
                    } catch (NegativeAmountException nae) {
                        //Do nothing
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Person with this ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addNewBill.setBounds(alignX, 400, buttonWidth, buttonHeight);
        window.add(addNewBill);
    }

    // Modifies: this, main
    // Effects: adds save button to main page
    private void addSaveButton() {
        JButton b3 = new JButton("Save Program");
        b3.setBounds(alignX, 450, buttonWidth, buttonHeight);
        b3.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, new JLabel(saveGroup()));
        });
        main.add(b3);
    }

    // Modifies: this, main
    // Effects: adds load button to main page
    private void addLoadButton() {
        JButton b4 = new JButton("Load Program");
        b4.setBounds(alignX, 500, buttonWidth, buttonHeight);
        b4.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, new JLabel(loadGroup()));
        });
        main.add(b4);
    }

    // Modifies: this
    // Effects: when closing program, gives the option to save the program
    private void saveGroupDialog() {
        JDialog saveQuitDialog = new JDialog();
        saveQuitDialog.setLocationRelativeTo(null);
        saveQuitDialog.setLayout(new FlowLayout());
        saveQuitDialog.add(new JLabel("Would you like to save before quitting?"));
        JButton yesButton = new JButton("Yes");
        JButton quitButton = new JButton("No");
        quitButton.addActionListener(e -> {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            System.exit(0);
        });
        yesButton.addActionListener(e1 -> {
            saveAndQuitResult(quitButton);
        });
        saveQuitDialog.add(yesButton);
        saveQuitDialog.add(quitButton);
        saveQuitDialog.setLocationRelativeTo(null);
        saveQuitDialog.pack();
        saveQuitDialog.setVisible(true);
    }
    
    // Modifies: this, quit
    // Effects: tries to save group in the current state and prints result aas a string
    private void saveAndQuitResult(JButton quit) {
        JDialog jd = new JDialog();
        jd.setLayout(new FlowLayout());
        jd.add(new JLabel(saveGroup()));
        quit.setText("Quit");
        jd.add(quit);
        jd.pack();
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
    }
    

    // Modifies: this
    // Effects: initializes empty group for manipulation with name "My Group"
    public void startUp() {
        group = new Group();
        group.rename("My Group");
    }

    // Modifies: this
    // Effects: loads group from JSON file, replaces current garden with it, returns a successful string
    //         else, return error string
    public String loadGroup() {
        try {
            jsonReader = new JsonReader(JSON_SOURCE);
            group = jsonReader.read();
            return "Loaded Group from " + JSON_SOURCE + ",";
        } catch (Exception e) {
            return "Unable to read from the file " + JSON_SOURCE;
        }
    }

    // Effects: saves group to JSON file JSON_SOURCE, returns a successful string
    //         else, return error string
    public String saveGroup() {
        try {
            jsonWriter = new JsonWriter(JSON_SOURCE);
            jsonWriter.open();
            jsonWriter.write(group);
            jsonWriter.close();
            return "Saved Group to " + JSON_SOURCE + ".";
        } catch (Exception e) {
            return "Unable to save to file " + JSON_SOURCE;
        }
    }
}

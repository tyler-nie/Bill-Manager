package ui;

import exceptions.InvalidIdException;
import exceptions.NegativeAmountException;
import model.Group;
import model.Bill;
import model.Person;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GroupAppUI {

    private static final String JSON_STORE = "./data/group.json";
    private Group group;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public GroupAppUI() {
        runGroup();
    }

    private void runGroup() {

        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings({"MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommand(String command) {
        if (command.equals("ng")) {
            doNameGroup();
        } else if (command.equals("gn")) {
            groupName();
        } else if (command.equals("ap")) {
            doAddPerson();
        } else if (command.equals("ab")) {
            doAddBill();
        } else if (command.equals("p")) {
            doGetPeoples();
        } else if (command.equals("b")) {
            doGetBills();
        } else if (command.equals("gsb")) {
            doGetSpecificBill();
        } else if (command.equals("gs")) {
            doGetSizeOfGroup();
        } else if (command.equals("gb")) {
            doGetNumOfBills();
        } else if (command.equals("bs")) {
            doBillSplit();
        } else if (command.equals("s")) {
            saveGroup();
        } else if (command.equals("l")) {
            loadGroup();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        group = new Group();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tng -> name the group");
        System.out.println("\tgn -> get group name");
        System.out.println("\tap -> add person");
        System.out.println("\tab -> add bill");
        System.out.println("\tp -> peoples");
        System.out.println("\tb -> bills");
        System.out.println("\tgsb -> get specific bill");
        System.out.println("\tgs -> size of group");
        System.out.println("\tgb -> size of bills");
        System.out.println("\tbs -> bill split");
        System.out.println("\ts -> save group");
        System.out.println("\tl -> load group");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: group
    //EFFECTS: renames the group
    private void doNameGroup() {
        System.out.println("Enter name of the group: ");
        String name = input.next();

        if (name.equals("") || name.equals(" ")) {
            System.out.println("No name inputted");
        } else {
            group.rename(name);
            System.out.println("The group name is now " + group.getName());
        }
    }

    //EFFECTS: returns the name of the group
    private void groupName() {
        System.out.println("The group name is " + group.getName());
    }

    //MODIFIES: group
    //EFFECTS: adds a person to the group
    private void doAddPerson() {
        System.out.println("Enter name of person to add: ");
        String name = input.next();

        if (name.equals("") || name.equals(" ")) {
            System.out.println("No name inputted");
        } else {
            group.addPerson(name);
            System.out.println(name + " has been added to the group");
        }
    }

    //MODIFIES: group
    //EFFECTS: adds a bill to the groups bills
    private void doAddBill() {
        System.out.println("Enter id of person who payed: ");
        int id = input.nextInt();

        if  (group.isPersonInGroup(id)) {
            System.out.println("Enter cost of the bill: $");
            double cost = input.nextDouble();
            System.out.println("Enter number of people for bill: ");
            int numOfPeople = input.nextInt();

            ArrayList<Person>  persons = group.getPersons();
            try {
                group.addBill(persons.get(id), cost, numOfPeople);
            } catch (NegativeAmountException e) {
                System.out.println("Negative Amount Inputted");
            }
            int billID = group.numberOfBills();
            System.out.println("The Bill has been added to the group of bills with id: " + (billID - 1));
        } else {
            System.out.println("No person with this id exists");
        }
    }

    //EFFECTS: returns the names of everyone in the group
    private void doGetPeoples() {
        if (group.numberOfPeople() == 0) {
            System.out.println("No one is in the group");
        } else {
            ArrayList<Person> persons = group.getPersons();
            String names = persons.get(0).getName();

            for (int i = 1; i < group.numberOfPeople(); i++) {
                names = names + ", " + persons.get(i).getName();
            }
            System.out.println("The people in this group are (in order of ID's) " + names);
        }
    }

    //EFFECTS: returns the ids of all the bills in the group
    private void doGetBills() {
        if (group.numberOfBills() == 0) {
            System.out.println("There are no bills in the group.");
        } else {
            ArrayList<Bill> bills = group.getBills();
            String billIDs = Integer.toString(bills.get(0).getID());

            for (int i = 1; i < group.numberOfBills(); i++) {
                billIDs = billIDs + ", " + bills.get(i).getID() + " ";
            }
            System.out.println("The bills in this group are (in order of ID's) " + billIDs);
        }
    }

    //EFFECTS: returns a specific bill with id in group
    private void doGetSpecificBill() {
        if (group.numberOfBills() == 0) {
            System.out.println("There are no bills in the group.");
        } else {
            System.out.println("Enter id of bill: ");
            int id = input.nextInt();

            ArrayList<Bill> bills = group.getBills();
            ArrayList<Person> person = group.getPersons();


            try {
                String name = person.get(group.getBill(id).getPersonID()).getName();

                System.out.println("The bill with id was payed by " + name + ", includes "
                        + group.getBill(id).getNumberOfPeople() + " people and costed $" + group.getBill(id).getCost());
            } catch (InvalidIdException e) {
                System.out.println("Invalid Id inputted");
            }

        }
    }

    //EFFECTS: returns the size of the group
    private void doGetSizeOfGroup() {
        int size = group.numberOfPeople();
        System.out.println("There are " + size + " people in this group");
    }

    //EFFECTS: returns the number of bills in the group
    private void doGetNumOfBills() {
        int size = group.numberOfBills();
        System.out.println("There are " + size + " bills in this group");
    }

    //EFFECTS: provides the share of a bill that you owe the person who payed the bill
    private void doBillSplit() {
        ArrayList<Bill> bills = group.getBills();
        ArrayList<Person> persons = group.getPersons();

        System.out.println("Please provide the billID you were a part of: ");
        int id = input.nextInt();

        try {
            boolean validId = group.isBillInGroup(id);
            double split = group.billSplit(id);
            System.out.println("Your share of this bill is " + split);
            int personId = bills.get(id).getPersonID();
            String name = persons.get(personId).getName();

            System.out.println("Therefore you owe " + name + " " + split + " dollars.");
        } catch (InvalidIdException e) {
            System.out.println("That is not a valid bill ID.");
        }
    }

    // EFFECTS: saves the group to file
    private void saveGroup() {
        try {
            jsonWriter.open();
            jsonWriter.write(group);
            jsonWriter.close();
            System.out.println("Saved " + group.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads group from file
    private void loadGroup() {
        try {
            group = jsonReader.read();
            System.out.println("Loaded " + group.getName() + " from " + JSON_STORE);
        } catch (NegativeAmountException e) {
            System.out.println("Negative Value inputted");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}

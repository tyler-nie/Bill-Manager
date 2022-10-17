package ui;

import model.Group;
import model.Bill;
import model.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class GroupApp {

    private Group group;
    private Scanner input;

    public GroupApp() {
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
    private void processCommand(String command) {
        if (command.equals("ap")) {
            doAddPerson();
        } else if (command.equals("ab")) {
            doAddBill();
        } else if (command.equals("p")) {
            doGetPeoples();
        } else if (command.equals("b")) {
            doGetBills();
        } else if (command.equals("gs")) {
            doGetSizeOfGroup();
        } else if (command.equals("gb")) {
            doGetNumOfBills();
        } else if (command.equals("bs")) {
            doBillSplit();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        group = new Group();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tap -> add person");
        System.out.println("\tab -> add bill");
        System.out.println("\tp -> peoples");
        System.out.println("\tb -> bills");
        System.out.println("\tgs -> size of group");
        System.out.println("\tgb -> size of bills");
        System.out.println("\tbs -> bill split");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: group
    //EFFECTS: adds a person to the group
    private void doAddPerson() {
        System.out.println("Enter name of person to add: ");
        String name = input.next();

        if (name.equals("") || name.equals(" ")) {
            System.out.println("No name inputed");
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
            group.addBill(persons.get(id), cost, numOfPeople);
            int billID = group.numberOfBills();
            System.out.println("The Bill has been added to the group of bills with id: " + (billID - 1));
        } else {
            System.out.println("This person id does not exists.");
        }
    }

    //EFFECTS: returns the names of everyone in the group
    private void doGetPeoples() {
        if (group.numberOfPeople() == 0) {
            System.out.println("There is no one in the group.");
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
            System.out.println("There is no bills in the group.");
        } else {
            ArrayList<Bill> bills = group.getBills();
            String billIDs = Integer.toString(bills.get(0).getID());

            for (int i = 1; i < group.numberOfBills(); i++) {
                billIDs = billIDs + ", " + bills.get(i).getID() + " ";
            }
            System.out.println("The bills in this group are (in order of ID's) " + billIDs);
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

        if (group.isBillInGroup(id)) {

            double split = group.billSplit(id);
            System.out.println("Your share of this bill is " + split);
            int personId = bills.get(id).getPersonID();
            String name = persons.get(personId).getName();

            System.out.println("Therefore you owe " + name + " " + split + " dollars.");

        } else {
            System.out.println("That is not a valid bill ID.");
        }
    }

}

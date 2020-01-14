package sk.itsovy.dolinsky.scannerdatabase;

import java.util.Scanner;

public class Main {
    private final static String ANSI_RESET = "\u001B[0m";
    private final static String ANSI_CYAN = "\u001B[36m";
    private final static String ANSI_BLUE = "\u001B[34m";
    private static final String RED_BOLD = "\033[1;31m";
    private static final String GREEN_BRIGHT = "\033[0;92m";
    private static Scanner sc = new Scanner(System.in);
    private static String option;

    public static void main(String[] args) {
        Database database = new Database();

        try {
            System.out.println(GREEN_BRIGHT + "Welcome to my Application! " + ANSI_RESET);
            printMenu();

            while (true) {
                if (option.equals("5")) {
                    System.out.println(RED_BOLD + "Exiting..." + ANSI_RESET);
                    break;
                }
                switch (option) {
                    case "1":
                        database.selectSlovakCities();
                        printMenu();
                        break;
                    case "2":
                        database.insertSlovakCity();
                        printMenu();
                        break;
                    case "3":
                        database.updateSlovakCity();
                        printMenu();
                        break;
                    case "4":
                        database.deleteSlovakCity();
                        printMenu();
                        break;
                    default:
                        System.out.println();
                        System.out.println(RED_BOLD + "Incorrect option. Please type it correctly." + ANSI_RESET);
                        System.out.println();
                        printMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMenu() {
        System.out.println("\n" + ANSI_CYAN + "|----------------------------------------------|");
        System.out.println("|   Select action to do with the table city:   |");
        System.out.println("|     1. Return list of the Slovak cities.     |");
        System.out.println("|     2. Insert city into slovak cities.       |");
        System.out.println("|     3. Update Slovak city.                   |");
        System.out.println("|     4. Delete Slovak city.                   |");
        System.out.println("|     5. Exit application.                     |");
        System.out.println("|----------------------------------------------|" + ANSI_RESET);
        System.out.print("\n" + ANSI_BLUE + "Select your action: " + ANSI_RESET);
        option = sc.nextLine();
    }
}

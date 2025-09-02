package ch.bzz;

import java.util.Scanner;

public class LibraryAppMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Enter Command >");

            input = scanner.nextLine();
            switch (input) {
                case "help" -> printHelp();
                case "quit" ->  System.out.println("Quitting Application...");
                default -> System.out.println("Invalid Command: " + input);
            }
        } while (!"quit".equalsIgnoreCase(input));
    }

    public static void printHelp() {
        System.out.println("help - lists all available commands");
        System.out.println("quit - quits the application");
    }
}

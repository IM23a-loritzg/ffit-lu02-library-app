package ch.bzz;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.sql.*;
import ch.bzz.PropertyLoader;

public class LibraryAppMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Enter Command >");

            input = scanner.nextLine();
            String[] commandParts = input.split("\\s", 2);
            String command = commandParts[0];
            String arg = commandParts[1];
            switch (input) {
                case "help" -> printHelp();
                case "quit" -> System.out.println("Quitting Application...");
                case "listBooks" -> listBooks();
                case "importBooks" -> importBooks(arg);
                default -> System.out.println("Invalid Command: " + input);
            }
        } while (!"quit".equalsIgnoreCase(input));
    }



    static Properties config = PropertyLoader.getProperties();

    private static final String DB_URL = config.getProperty("DB_URL");
    private static final String DB_USER = config.getProperty("DB_USER");
    private static final String DB_PASSWORD = config.getProperty("DB_PASSWORD");


    public static void printHelp() {
        System.out.println("help - lists all available commands");
        System.out.println("quit - quits the application");
    }

    public static void listBooks() {
        List<Book> books = new ArrayList<>();
        String query = "select * from books";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publication_year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        books.forEach(System.out::println);
    }

    public static void importBooks(String filePath) {
        filePath = filePath.replace("\\", "/");
        if (filePath.isEmpty()) {
            System.out.println("File path is empty");
            return;
        }
        if (!filePath.endsWith(".tsv")) {
            System.out.println("File path does not end with .tsv");
        } else {
            loadBookFromTSV(filePath);
        }
    }

    public static void loadBookFromTSV(String filePath) {
        List<Book> books = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            boolean bool = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (bool) {
                    bool = false;
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }
                String[] lineParts = line.split("\t");
                if (lineParts.length < 5) {
                    System.out.println("Line is too short");
                    continue;
                }
                int id = Integer.parseInt(lineParts[0]);
                String isbn = lineParts[1];
                String title = lineParts[2];
                String author = lineParts[3];
                int publicationYear = Integer.parseInt(lineParts[4]);
                Book book = new Book(id, isbn, title, author, publicationYear);
                books.add(book);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

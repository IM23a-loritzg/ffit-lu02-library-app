package ch.bzz;

public class Book {
    private int id ;
    private String isbn;
    private String title;
    private String author;
    private int year;

    public Book(int id, String isbn, String title, String author, int year) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s, Titel: %s, Author: %s, Year: %d", isbn, title, author, year);
    }
}

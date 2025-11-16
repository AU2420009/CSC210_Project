package datastruc;
import java.util.*;
public class BookHashTable {

    private final int SIZE = 50;
    private LinkedList<Entry>[] table;

    public static class Book {
        String title;
        String author;
        String genre;
        String isbn;
        int year;

        Book(String title, String author, String genre, String isbn, int year) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.isbn = isbn;
            this.year = year;
        }

        public String toString() {
            return "[" + title + " | " + author + " | " + genre + " | " + isbn + " | " + year + "]";
        }
    }

    static class Entry {
        String key;
        Book book;
        Entry(String key, Book book) {
            this.key = key;
            this.book = book;
        }
    }

    @SuppressWarnings("unchecked")
    public BookHashTable() {
        table = new LinkedList[SIZE];
        for (int i = 0; i < SIZE; i++)
            table[i] = new LinkedList<>();
    }

    private int hash(String key) {
        return Math.abs(key.toLowerCase().hashCode()) % SIZE;
    }

    public void insert(Book book) {
        int index = hash(book.title);
        table[index].add(new Entry(book.title, book));
    }

    // Simple title search (used for borrow, case-insensitive)
    public Book searchByTitle(String title) {
        if (title == null) return null;
        String t = title.toLowerCase();

        for (LinkedList<Entry> bucket : table) {
            for (Entry e : bucket) {
                if (e.book.title.toLowerCase().equals(t)) {
                    return e.book;
                }
            }
        }
        return null;
    }

    // Unified metadata search:
    public Book fullSearch(String title, String genre, String isbn, Integer year) {
        String t = (title == null) ? null : title.toLowerCase();
        String g = (genre == null) ? null : genre.toLowerCase();
        String i = (isbn == null) ? null : isbn.toLowerCase();

        for (LinkedList<Entry> bucket : table) {
            for (Entry e : bucket) {
                Book b = e.book;

                boolean matchTitle = (t == null || b.title.toLowerCase().equals(t));
                boolean matchGenre = (g == null || b.genre.toLowerCase().equals(g));
                boolean matchIsbn  = (i == null || b.isbn.toLowerCase().equals(i));
                boolean matchYear  = (year == null || b.year == year);

                if (matchTitle && matchGenre && matchIsbn && matchYear) {
                    return b; // return first exact match
                }
            }
        }
        return null;
    }

// bit ugly code: method to initialize it with the default data.
public void initBHT() {
        // FICTION
        this.insert(new BookHashTable.Book("Pride and Prejudice", "Jane Austen", "Fiction", "9780141439518", 1813));
        this.insert(new BookHashTable.Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "9780061120084", 1960));
        this.insert(new BookHashTable.Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "9780743273565", 1925));
        this.insert(new BookHashTable.Book("Jane Eyre", "Charlotte Brontë", "Fiction", "9780141441146", 1847));
        this.insert(new BookHashTable.Book("Wuthering Heights", "Emily Brontë", "Fiction", "9780141439556", 1847));
        this.insert(new BookHashTable.Book("1984", "George Orwell", "Fiction", "9780451524935", 1949));

        // MYSTERY
        this.insert(new BookHashTable.Book("The Hound of the Baskervilles", "Arthur Conan Doyle", "Mystery", "9780141034324", 1902));
        this.insert(new BookHashTable.Book("Gone Girl", "Gillian Flynn", "Mystery", "9780307588371", 2012));
        this.insert(new BookHashTable.Book("The Girl with the Dragon Tattoo", "Stieg Larsson", "Mystery", "9780307454546", 2005));
        this.insert(new BookHashTable.Book("Murder on the Orient Express", "Agatha Christie", "Mystery", "9780062073501", 1934));
        this.insert(new BookHashTable.Book("The Da Vinci Code", "Dan Brown", "Mystery", "9780307474278", 2003));
        this.insert(new BookHashTable.Book("In the Woods", "Tana French", "Mystery", "9780143113492", 2007));

        // FANTASY
        this.insert(new BookHashTable.Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", "9780590353427", 1997));
        this.insert(new BookHashTable.Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", "9780547928227", 1937));
        this.insert(new BookHashTable.Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", "9780544003415", 1954));
        this.insert(new BookHashTable.Book("A Game of Thrones", "George R.R. Martin", "Fantasy", "9780553593716", 1996));
        this.insert(new BookHashTable.Book("The Name of the Wind", "Patrick Rothfuss", "Fantasy", "9780756404741", 2007));
        this.insert(new BookHashTable.Book("Percy Jackson: The Lightning Thief", "Rick Riordan", "Fantasy", "9780786838653", 2005));

        // SCIENCE
        this.insert(new BookHashTable.Book("A Brief History of Time", "Stephen Hawking", "Science", "9780553380163", 1988));
        this.insert(new BookHashTable.Book("The Selfish Gene", "Richard Dawkins", "Science", "9780199291151", 1976));
        this.insert(new BookHashTable.Book("Cosmos", "Carl Sagan", "Science", "9780345539434", 1980));
        this.insert(new BookHashTable.Book("The Origin of Species", "Charles Darwin", "Science", "9781509827695", 1859));
        this.insert(new BookHashTable.Book("Sapiens", "Yuval Noah Harari", "Science", "9780062316110", 2011));
        this.insert(new BookHashTable.Book("The Gene: An Intimate History", "Siddhartha Mukherjee", "Science", "9781476733524", 2016));

        // HISTORY
        this.insert(new BookHashTable.Book("Guns, Germs, and Steel", "Jared Diamond", "History", "9780393317558", 1997));
        this.insert(new BookHashTable.Book("The Rise and Fall of the Third Reich", "William L. Shirer", "History", "9781451651683", 1960));
        this.insert(new BookHashTable.Book("Team of Rivals", "Doris Kearns Goodwin", "History", "9780684824901", 2005));
        this.insert(new BookHashTable.Book("1776", "David McCullough", "History", "9780743226721", 2005));
        this.insert(new BookHashTable.Book("The Wright Brothers", "David McCullough", "History", "9781476728759", 2015));
        this.insert(new BookHashTable.Book("Alexander Hamilton", "Ron Chernow", "History", "9780143034759", 2004));

        // NON-FICTION
        this.insert(new BookHashTable.Book("Educated", "Tara Westover", "Non-Fiction", "9780399590504", 2018));
        this.insert(new BookHashTable.Book("Becoming", "Michelle Obama", "Non-Fiction", "9781524763138", 2018));
        this.insert(new BookHashTable.Book("The Power of Habit", "Charles Duhigg", "Non-Fiction", "9780812981605", 2012));
        this.insert(new BookHashTable.Book("Thinking, Fast and Slow", "Daniel Kahneman", "Non-Fiction", "9780374533557", 2011));
        this.insert(new BookHashTable.Book("Atomic Habits", "James Clear", "Non-Fiction", "9780735211292", 2018));
        this.insert(new BookHashTable.Book("The Subtle Art of Not Giving a F*ck", "Mark Manson", "Non-Fiction", "9780062457714", 2016));


}


}




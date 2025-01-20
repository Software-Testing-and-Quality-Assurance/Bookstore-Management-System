/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * Unit  Testing * * * * * * * * * *
 * * * * * * Test done by: Klaudia Tamburi * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */


package test;

import controller.BookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookReadWriteFileTest {

    @TempDir
    File TempDir;

    private File source;
    private ObservableList<Book> bookStock;
    private BookController bc;

    @BeforeEach
    void setUp() {
        source = new File(TempDir, "books.dat");
        bookStock = FXCollections.observableArrayList();
        bc = new BookController();
    }

    @Test
    @DisplayName("Test create function - unit testing")
    void test1() {
        // Check temporary directory
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(TempDir.exists());

        // Create a book
        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);

        // Check function
        assertTrue(bc.create(b,source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());
    }

    @Test
    @DisplayName("Test load books from file - unit testing")
    void test2() throws IOException  {
        // Check temporary directory
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(TempDir.exists());
        Book b;

        // Write one book to the temporary file
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(source))) {
            b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
            outputStream.writeObject(b);
        }

        // Check function
        assertTrue(bc.loadBooksFromFile(source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());
    }

    @Test
    @DisplayName("Test both functions together")
    void test3() throws IOException {
        // Check temporary directory
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(TempDir.exists());

        // Create a book
        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);

        // Check function create
        assertTrue(bc.create(b,source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());

        // Clear bookStock from previous function
        bookStock.clear();

        // Check function load from file
        assertTrue(bc.loadBooksFromFile(source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());
    }
}
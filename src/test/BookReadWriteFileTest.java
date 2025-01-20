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

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

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
        Book b1 = new Book("123456789", "And then there were none", "Klaudia", "Mystery", 16.0, 10.0, new Author("Agatha", "Christie"), 100);

        // Check function
        assertAll(
                ()->assertTrue(bc.create(b,source,bookStock)),
                ()->assertTrue(bc.create(b1,source,bookStock)),
                ()->assertEquals(2,bookStock.size()),
                ()->assertTrue(source.exists()),
                ()->assertTrue(Files.exists(source.toPath())),
                ()-> {
                    ObservableList<Book> written = FXCollections.observableArrayList();
                    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(source))) {
                        while (true) {
                            try {
                                written.add((Book) inputStream.readObject());
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    }
                    assertEquals(bookStock.size(),written.size());
                    assertEquals(bookStock.getFirst(), written.getFirst());
                    assertEquals(bookStock.getLast(), written.getLast());
                }
        );
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
        assertAll(
                ()-> assertTrue(source.exists()),
                ()->assertTrue(Files.exists(source.toPath())),
                ()->assertTrue(bc.loadBooksFromFile(source,bookStock)),
                ()->assertEquals(1,bookStock.size()),
                ()->assertEquals(b,bookStock.getFirst())
        );

    }

    @Test
    @DisplayName("Test both functions together")
    void test3() {
        // Check temporary directory
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(TempDir.exists());

        // Create a book
        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);

        // Check function create
        assertTrue(bc.create(b,source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());
        assertTrue(source.exists());
        assertTrue(Files.exists(source.toPath()));

        // Clear bookStock from previous function
        bookStock.clear();

        // Check function load from file
        assertTrue(bc.loadBooksFromFile(source,bookStock));
        assertEquals(1,bookStock.size());
        assertEquals(b,bookStock.getFirst());
    }
}
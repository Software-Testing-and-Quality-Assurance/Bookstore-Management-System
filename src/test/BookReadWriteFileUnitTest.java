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
import controller.ObjectOutputStreamWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Author;
import model.Book;
import controller.ObjectInputStreamWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookReadWriteFileUnitTest {

    /*
    * Unit testing to see if the functions
    * to read and write from file the books
    * work correctly
    * */
    @TempDir
    File TempDir;

    private File source;
    private File mockFile;
    private ObservableList<Book> bookStock;
    private BookController bc;

    @BeforeEach
    void setUp() {
        source = new File(TempDir, "books.dat");
        bookStock = FXCollections.observableArrayList();
        bc = new BookController();
        mockFile = mock(File.class);
    }

    @Test
    @DisplayName("Test create function (writes only 1 book to file by appending) - unit testing")
    void test1() {
        // Check temporary directory
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(TempDir.exists());

        // Create a book
        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
        Book b1 = new Book("123456780", "And then there were none", "Klaudia", "Mystery", 16.0, 10.0, new Author("Agatha", "Christie"), 100);

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
    @DisplayName("Test create function (mocked using wrapper class - writes to empty file) - unit testing")
    void test1_mock() throws IOException {
        // Prepare book object
        Book book = new Book("123456789", "Book Title", "Author Name", "Genre", 15.0, 10.0, new Author("Author", "Last"), 100);

        // Mock FileOutputStream
        FileOutputStream mockFileOutputStream = mock(FileOutputStream.class);

        // Mock ObjectOutputStreamWrapper
        ObjectOutputStreamWrapper mockObjectOutputStreamWrapper = mock(ObjectOutputStreamWrapper.class);

        // Mock File
        File mockFile = mock(File.class);
        when(mockFile.length()).thenReturn(0L);  // Simulate an empty file

        // Mock behavior of the writeObject method in the wrapper
        doNothing().when(mockObjectOutputStreamWrapper).writeObject(any(Book.class));

        // Mock behavior of the createObjectOutputStream method to return our mock
        BookController bookController = spy(new BookController());

        // Mock createFileOutputStream and createObjectOutputStream
        doReturn(mockFileOutputStream).when(bookController).createFileOutputStream(mockFile);
        doReturn(mockObjectOutputStreamWrapper).when(bookController).createObjectOutputStream(mockFileOutputStream);

        // Execute the method
        boolean result = bookController.create(book, mockFile, bookStock);

        // Assertions
        assertTrue(result);
        assertEquals(1, bookStock.size());
        assertEquals("123456789", bookStock.getFirst().getIsbn());

        // Verify interactions with the mocks
        verify(mockObjectOutputStreamWrapper, times(1)).writeObject(book);  // Verify writeObject was called once
        verify(mockObjectOutputStreamWrapper, times(1)).close();  // Ensure the wrapper was closed
        verify(mockFileOutputStream, times(1)).close();
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
    @DisplayName("Test load books from file(mocked using wrapper class) - unit testing")
    void test2_mock() throws Exception {
        // Mock FileInputStream
        FileInputStream mockFileInputStream = mock(FileInputStream.class);

        // Mock ObjectInputStreamWrapper
        ObjectInputStreamWrapper mockInputStream = mock(ObjectInputStreamWrapper.class);

        // Mock behavior: return books, then throw EOFException
        Book b = new Book("123456789", "If We Were Villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
        Book b1 = new Book("123456780", "And Then There Were None", "Klaudia", "Mystery", 16.0, 10.0, new Author("Agatha", "Christie"), 100);

        when(mockInputStream.readObject())
                .thenReturn(b)
                .thenReturn(b1)
                .thenThrow(new EOFException()); // Simulate end of file

        doNothing().when(mockInputStream).close();

        // Spy on BookController and mock `createObjectInputStreamWrapper`
        BookController bookLoader = Mockito.spy(new BookController());

        // Mock createObjectInputStreamWrapper to return the mocked ObjectInputStreamWrapper
        Mockito.doReturn(mockInputStream).when(bookLoader).createObjectInputStreamWrapper(any(FileInputStream.class));
        Mockito.doReturn(mockFileInputStream).when(bookLoader).createFileInputStream(any(File.class));

        // Execute the method
        boolean result = bookLoader.loadBooksFromFile(mockFile, bookStock);

        // Assertions
        assertTrue(result);
        assertEquals(2, bookStock.size());
        assertEquals("123456789", bookStock.get(0).getIsbn());
        assertEquals("123456780", bookStock.get(1).getIsbn());

        // Verify interactions
        verify(mockInputStream, times(3)).readObject(); // 2 books + EOFException
        verify(mockInputStream, times(1)).close(); // Ensure stream was closed
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

    @Test
    @DisplayName("Test update function (writes all books to file) - unit testing")
    void test4() throws IOException, ClassNotFoundException {
        // Assign the temporary file to the static array in main
        Main.BOOK_FILE = source;

        // Create a book
        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
        Book b1 = new Book("123456789", "And then there were none", "Klaudia", "Mystery", 16.0, 10.0, new Author("Agatha", "Christie"), 100);

        // Add to bookStock
        Main.bookStock.add(b);
        Main.bookStock.add(b1);

        // Call update function to write all books to file
        boolean check = bc.updateAll();

        // Make assertions
        assertTrue(Main.BOOK_FILE.exists());
        assertTrue(Files.exists(Main.BOOK_FILE.toPath()));
        assertTrue(check);

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
        assertEquals(Main.bookStock.size(), written.size());
        assertEquals(Main.bookStock.getFirst(), written.getFirst());
        assertEquals(Main.bookStock.getLast(), written.getLast());
    }
}
/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * Integration Testing * * * * * * * * *
 * * * * * Test done by: Klaudia Tamburi * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import view.ManagerView;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerAddBookIntegrationTest extends ApplicationTest {
    /*
    * Testing the part where in the Manager view (after supposedly
    * signing in) you select to add a book.
    *
    * 2 scenarios:
    *   - Add a new book
    *   - Add stock to existing book
    * */
    @TempDir
    static File TempDir;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Welcome to BookStore");
        ManagerView mv = new ManagerView();
        stage.setScene(mv.showView(stage));
        stage.show();
    }

    @BeforeAll
    public static void setUp(){
        Main.currentUser = new Employee("manager", "p455w0r8", "Man", "Ager", "man@gmail.com", Role.MANAGER, "355691234567", 80000,
                Access.NO, Access.YES, Access.YES, Access.YES, new Date(0));
        Main.BOOK_FILE = new File(TempDir, "books.dat");
        Main.bookStock.clear();
    }

    Button addView;
    Button add;

    @Test
    @DisplayName("Manager adds new book")
    public void test1(){
        addView = lookup("#add_book").query();
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(addView);
        WaitForAsyncUtils.waitForFxEvents();

        add =  lookup("#new_book").query();
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        TextField isbn = lookup("#isbn").query();
        TextField category = lookup("#category").query();
        TextField supplier = lookup("#supplier").query();
        TextField title = lookup("#title").query();
        TextField selling = lookup("#selling").query();
        TextField original = lookup("#original").query();
        TextField author_n = lookup("#author_n").query();
        TextField author_s = lookup("#author_s").query();
        TextField stock = lookup("#stock").query();
        WaitForAsyncUtils.waitForFxEvents();

        String expectedIsbn = "123456789";
        String expectedCategory = "Mystery";
        String expectedSupplier = "Klaudia";
        String expectedTitle = "If We Were Villains";
        String expectedSelling = "16.0";
        String expectedOriginal = "10.0";
        String expectedAuthorN = "M.L.";
        String expectedAuthorS = "RIO";
        String expectedStock = "100";

        Platform.runLater(() -> {
            isbn.setText(expectedIsbn);
            category.setText(expectedCategory);
            supplier.setText(expectedSupplier);
            title.setText(expectedTitle);
            selling.setText(expectedSelling);
            original.setText(expectedOriginal);
            author_n.setText(expectedAuthorN);
            author_s.setText(expectedAuthorS);
            stock.setText(expectedStock);
        });
        WaitForAsyncUtils.waitForFxEvents();

        Button create = lookup("#create").query();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(create);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(expectedIsbn, Main.bookStock.getFirst().getIsbn());
        assertEquals(expectedTitle, Main.bookStock.getFirst().getTitle());
        assertEquals(expectedSupplier, Main.bookStock.getFirst().getSupplier());
        assertEquals(expectedCategory, Main.bookStock.getFirst().getCategory());
        assertEquals(Double.parseDouble(expectedSelling), Main.bookStock.getFirst().getSellingPrice());
        assertEquals(Double.parseDouble(expectedOriginal), Main.bookStock.getFirst().getPurchasePrice());
        assertEquals(expectedAuthorN, Main.bookStock.getFirst().getAuthor().getAuthorName());
        assertEquals(expectedAuthorS, Main.bookStock.getFirst().getAuthor().getAuthorSurname());
        assertEquals(Integer.parseInt(expectedStock), Main.bookStock.getFirst().getStock());

        WaitForAsyncUtils.waitForFxEvents();
        Button skipAlert = skipAlertButton();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(skipAlert);
        WaitForAsyncUtils.waitForFxEvents();
        sleep(500);
        Main.bookStock.clear();
    }

    @Test
    @DisplayName("Manager adds book stock")
    public void test2(){
        Book book = new Book("123456789", "If We Were Villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
        Main.bookStock.add(book);

        addView = lookup("#add_book").query();
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(addView);
        WaitForAsyncUtils.waitForFxEvents();

        add =  lookup("#add_stock").query();
        WaitForAsyncUtils.waitForFxEvents();

        TextField isbn = lookup("#isbn").query();
        TextField stock = lookup("#stock").query();
        WaitForAsyncUtils.waitForFxEvents();

        Platform.runLater(() -> {
            isbn.setText(book.getIsbn());
            stock.setText("20");
        });
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(120.0,Main.bookStock.getFirst().getStock());

        WaitForAsyncUtils.waitForFxEvents();
        Button skip = skipAlertButton();
        clickOn(skip);
    }

    private Button skipAlertButton() {
        Button okButton;
        okButton = lookup(".button").queryAllAs(Button.class)
                .stream()
                .filter(button -> "OK".equals(button.getText()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("OK button not found!"));
        return okButton;
    }
}

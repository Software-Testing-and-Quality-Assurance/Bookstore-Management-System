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

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import view.ChooseDates;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookBoughtViewIntegrationTest extends ApplicationTest {
    /*
     * Testing the part where you choose dates, so ChooseDates in view
     * and then passing the dates in the BookBought in view and showing
     * the table with the correct data. Main purpose of test is to see
     * if the elements show up correctly in UI
     */
    @Override
    public void start(Stage stage) {
        ChooseDates cdv = new ChooseDates();
        String action = "Book Bought";
        cdv.showView(stage, action);
    }

    @BeforeAll
    public static void setUp(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 5);
        Date bought = calendar.getTime();

        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 15.0, 10.0, new Author("M.L.", "RIO"), 10, bought);
        Book b1 = new Book("123456780", "And then there were none", "Klaudia", "Mystery", 10.0, 5.0, new Author("Agatha", "Christie"), 10, bought);
        b.getBoughtPerDate().put(bought, 10);
        b1.getBoughtPerDate().put(bought, 10);

        //To make sure we have only these 2 books in stock
        Main.bookStock.clear();
        Main.bookStock.add(b);
        Main.bookStock.add(b1);
    }

    Button pickDate;
    DatePicker dp1;
    DatePicker dp2;


    TableView<Book> tv;
    TableColumn<Book, String> isbn;
    TableColumn<Book, String> title;
    TableColumn<Book, Author> author;
    TableColumn<Book, Integer> stock;
    TableColumn<Book, Double> selling;
    TableColumn<Book, Double> purchase;
    TableColumn<Book, Date> date;
    Button goBack;

    @Test
    @DisplayName("Pick Date and Show Book Data")
    void test1(){

        dp1 = lookup("#date_picker_1").query();
        dp2 = lookup("#date_picker_2").query();
        pickDate = lookup("#pick_date").query();

        interact(() -> dp1.setValue(LocalDate.of(2024, 12, 1)));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> dp2.setValue(LocalDate.of(2024, 12, 31)));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(pickDate);
        WaitForAsyncUtils.waitForFxEvents();

        tv = lookup("#table").query();
        assertFalse(tv.getItems().isEmpty());
        //noinspection unchecked
        isbn = (TableColumn<Book, String>) tv.getColumns().get(0);
        assertEquals("ISBN", isbn.getText());
        //noinspection unchecked
        title = (TableColumn<Book, String>) tv.getColumns().get(1);
        assertEquals("Title", title.getText());
        //noinspection unchecked
        author = (TableColumn<Book, Author>) tv.getColumns().get(2);
        assertEquals("Author", author.getText());
        //noinspection unchecked
        stock = (TableColumn<Book, Integer>) tv.getColumns().get(3);
        assertEquals("Stock", stock.getText());
        //noinspection unchecked
        selling = (TableColumn<Book, Double>) tv.getColumns().get(4);
        assertEquals("Selling Price", selling.getText());
        //noinspection unchecked
        purchase = (TableColumn<Book, Double>) tv.getColumns().get(5);
        assertEquals("Purchase Price", purchase.getText());
        //noinspection unchecked
        date = (TableColumn<Book, Date>) tv.getColumns().get(6);
        assertEquals("Purchase Date",date.getText());

        Book firstRow = tv.getItems().getFirst();
        assertEquals(Main.bookStock.getFirst().getIsbn(), firstRow.getIsbn());
        assertEquals(Main.bookStock.getFirst().getTitle(), firstRow.getTitle());
        assertEquals(Main.bookStock.getFirst().getCategory(), firstRow.getCategory());
        assertEquals(Main.bookStock.getFirst().getStock(), firstRow.getStock());
        assertEquals(Main.bookStock.getFirst().getSellingPrice(), firstRow.getSellingPrice());
        assertEquals(Main.bookStock.getFirst().getPurchasePrice(), firstRow.getPurchasePrice());
        assertEquals(Main.bookStock.getFirst().getAuthor(), firstRow.getAuthor());
        assertEquals(Main.bookStock.getFirst().getFirstPurchaseDate(), firstRow.getFirstPurchaseDate());

        Book secondRow = tv.getItems().getLast();
        assertEquals(Main.bookStock.getLast().getIsbn(), secondRow.getIsbn());
        assertEquals(Main.bookStock.getLast().getTitle(), secondRow.getTitle());
        assertEquals(Main.bookStock.getLast().getCategory(), secondRow.getCategory());
        assertEquals(Main.bookStock.getLast().getStock(), secondRow.getStock());
        assertEquals(Main.bookStock.getLast().getSellingPrice(), secondRow.getSellingPrice());
        assertEquals(Main.bookStock.getLast().getPurchasePrice(), secondRow.getPurchasePrice());
        assertEquals(Main.bookStock.getLast().getAuthor(), secondRow.getAuthor());
        assertEquals(Main.bookStock.getLast().getFirstPurchaseDate(), secondRow.getFirstPurchaseDate());
        WaitForAsyncUtils.waitForFxEvents();

        goBack = lookup("#previous").query();

        // Mock button click behaviour since we only care to see if button is clicked not to show next scene
        goBack.setOnAction(event -> goBack.setText("Clicked"));
        clickOn(goBack);
        sleep(500);
        assertEquals("Clicked", goBack.getText());
    }


}
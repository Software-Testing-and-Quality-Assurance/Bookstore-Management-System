/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * System  Testing * * * * * * * * * *
 * * * * * Test done by: Klaudia Tamburi * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import controller.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import view.LoginView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LibrarianSystemTest extends ApplicationTest {
    /*
     * System testing for app state as a Librarian
     * Log in - Sell Books/Create Bill - Print Bill in .txt file - Log out 
     */
    @TempDir
    static File TempDir;

    static EmployeeController ec;
    static AdminController ac;
    static BookController bc;
    static TotalBillController tbc;

    static LoginView lv;

    static TextField usernameField;
    static TextField passwordField;
    static Button loginButton;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Welcome to BookStore");
        stage.setScene(lv.showView(stage));
        stage.show();
    }

    @BeforeAll
    public static void beforeAll(){
        Main.DATA_FILE = new File(TempDir, "employees.dat");
        Main.BOOK_FILE = new File(TempDir, "books.dat");
        Main.id  = new File(TempDir,"last_assigned_id.txt");
        Main.ALL_BILLS_FILE = new File(TempDir, "bills.dat");
        Main.PRINT_BILL_PATH = TempDir.toString() + File.separator + "printBill";
        Main.billsPerLibrarian = new HashMap<>();

        ec = new EmployeeController();
        ac = new AdminController(ec);
        bc = new BookController();
        tbc = new TotalBillController();

        lv = new LoginView();

        try (FileWriter writer = new FileWriter(Main.id)) {
            writer.write("0");  // Write the number 0 to the file
            System.out.println("Number 0 written to file: " + Main.id.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        Book b1 = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 2);
        Book b2 = new Book("234567890", "And then there were none", "Klaudia", "Mystery", 16.0, 10.0, new Author("Agatha", "Christie"), 100);

        bc.create(b1, Main.BOOK_FILE, Main.bookStock);
        bc.create(b2, Main.BOOK_FILE, Main.bookStock);

        Main.seedData();
        Main.readLastAssignedId();
        ac.ec.loadUsersFromFile(Main.DATA_FILE, Main.employeesAll);
    }

    @BeforeEach
    public void beforeEach() {
        usernameField = lookup("#username_field").query();
        passwordField = lookup("#password_field").query();
        loginButton = lookup("#login_button").query();
    }

    @Test
    public void createBill() {
        assertTrue(TempDir.exists());
        assertTrue(TempDir.isDirectory());
        assertTrue(Main.DATA_FILE.exists());
        assertTrue(Main.BOOK_FILE.exists());
        assertTrue(Main.id.exists());

        usernameField.setText("librarian");
        WaitForAsyncUtils.waitForFxEvents();

        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();

        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();

        Button createBill = lookup("#create_bill").query();
        clickOn(createBill);
        WaitForAsyncUtils.waitForFxEvents();

        // No book found
        TextField isbn = lookup("#isbn_field").query();
        TextField quantity = lookup("#quantity_field").query();
        Button add = lookup("#add").query();

        isbn.setText("123456780");
        WaitForAsyncUtils.waitForFxEvents();
        quantity.setText("2");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        Button alert = skipAlertButton();
        Label alertHeader = alertMessageHeader();
        Label alertContent = alertMessageContent();

        assertEquals("Try again", alertHeader.getText());
        assertEquals("Book not found", alertContent.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);
        WaitForAsyncUtils.waitForFxEvents();

        Button next = lookup("#next").query();

        clickOn(next);
        WaitForAsyncUtils.waitForFxEvents();

        // Empty quantity
        isbn = lookup("#isbn_field").query();
        isbn.setText("123456789");
        WaitForAsyncUtils.waitForFxEvents();

        add = lookup("#add").query();
        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        alertContent = alertMessageContent();

        assertEquals("Invalid input", alertHeader.getText());
        assertEquals("Please enter valid input.", alertContent.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);
        WaitForAsyncUtils.waitForFxEvents();

        next = lookup("#next").query();
        clickOn(next);
        WaitForAsyncUtils.waitForFxEvents();

        // Add a book with quantity > stock to bill
        isbn = lookup("#isbn_field").query();
        isbn.setText("123456789");
        WaitForAsyncUtils.waitForFxEvents();
        quantity = lookup("#quantity_field").query();
        quantity.setText("3");
        WaitForAsyncUtils.waitForFxEvents();

        add = lookup("#add").query();
        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        alertContent = alertMessageContent();

        assertEquals("Not enough books!", alertHeader.getText());
        assertEquals("There aren't enough books for this bill!", alertContent.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);
        WaitForAsyncUtils.waitForFxEvents();

        next = lookup("#next").query();
        clickOn(next);
        WaitForAsyncUtils.waitForFxEvents();

        // Create bill with 2 books
        isbn = lookup("#isbn_field").query();
        isbn.setText("123456789");
        WaitForAsyncUtils.waitForFxEvents();
        quantity = lookup("#quantity_field").query();
        quantity.setText("1");
        WaitForAsyncUtils.waitForFxEvents();

        add = lookup("#add").query();
        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        next = lookup("#next").query();
        clickOn(next);
        WaitForAsyncUtils.waitForFxEvents();

        isbn = lookup("#isbn_field").query();
        isbn.setText("234567890");
        WaitForAsyncUtils.waitForFxEvents();
        quantity = lookup("#quantity_field").query();
        quantity.setText("1");
        WaitForAsyncUtils.waitForFxEvents();

        add = lookup("#add").query();
        clickOn(add);
        WaitForAsyncUtils.waitForFxEvents();

        Button done1 = lookup("#done").query();
        clickOn(done1);
        WaitForAsyncUtils.waitForFxEvents();

        // Validate files and content
        System.out.println(TempDir.getAbsolutePath());
        assertTrue(Main.ALL_BILLS_FILE.exists());
        assertEquals(1,Main.allBills.size());
        assertEquals("librarian",Main.allBills.getFirst().getLibrarianUser());
        assertTrue(Main.PRINT_BILL_FILE.exists());
        System.out.println(Main.PRINT_BILL_FILE.getAbsolutePath());
        String content = "";
        try {
            content = Files.readString(Main.PRINT_BILL_FILE.toPath());
        } catch (IOException e) {
            //log
        }

        // Since got error that the strings had differences (in line separator)
        String expected = Main.allBills.getFirst().getBillContent().replaceAll("\\r\\n|\\r|\\n", "\n");
        content = content.replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals(expected,content);
    }

    @Test
    public void logInLogOut(){
        sleep(500);
        usernameField.setText("librarian");
        sleep(500);
        passwordField.setText("p455w0r8");
        sleep(500);
        this.clickOn(loginButton);
        sleep(500);

        Button logout = lookup("#sign_out").query();
        clickOn(logout);
        sleep(500);

        Button found = (lookup("#login_button").query());

        assertEquals(loginButton.getText(), found.getText());
    }

    @Test
    public void failLogIn(){
        //wrong password
        sleep(500);
        usernameField.setText("librarian");
        sleep(500);
        passwordField.setText("p455w0r811111");
        sleep(500);
        this.clickOn(loginButton);
        sleep(500);

        Button alert = skipAlertButton();
        Label alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        sleep(500);
        clickOn(alert);

        // wrong username
        sleep(500);
        usernameField.setText("librarian1");
        sleep(500);
        passwordField.setText("p455w0r8");
        sleep(500);
        this.clickOn(loginButton);
        sleep(500);

        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        sleep(500);
        clickOn(alert);

        //wrong both
        sleep(500);
        usernameField.setText("librarian1");
        sleep(500);
        passwordField.setText("p455w0r81111111");
        sleep(500);
        this.clickOn(loginButton);
        sleep(500);

        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        sleep(500);
        clickOn(alert);

        Button found = lookup("#login_button").query();

        assertEquals(loginButton, found);
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

    private Label alertMessageHeader(){
        Label alertHeader;
        alertHeader = lookup(".header-panel .label").queryAllAs(Label.class)
                .stream()
                .filter(Label::isVisible)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Alert header not found!"));
        return alertHeader;
    }

    private Label alertMessageContent() {
        Node contentNode = lookup("#custom-alert-pane .content").query();
        return (Label) contentNode;
    }
}

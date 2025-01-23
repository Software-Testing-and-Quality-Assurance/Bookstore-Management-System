package test;
import controller.AdminController;
import controller.EmployeeController;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import view.LoginView;
import view.RegisterView;
import java.io.File;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AdminSystemTest extends ApplicationTest {

    @TempDir
    static File tempDir;
    static AdminController ac;
    static EmployeeController ec;
    static RegisterView rv;
    static LoginView lv;
    static TextField usernameField;
    static TextField passwordField;
    static  TextField firstNameField;
    static  TextField firstNameF;
    static  TextField surnameField;
    static  TextField surnameF;
    static  TextField emailField;
    static TextField userField;
    static TextField userF;
    static PasswordField passF;
    static PasswordField vpassF;
    static RadioButton lib;
    static TextField phoneF;
    static DatePicker birthdayDatePicker;
    static Button button;
    static Button delete;
    static Button goBack;
    static Button loginButton;
    static Button signOut;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Welcome to BookStore");
        stage.setScene(lv.showView(stage));
        stage.show();
    }

    @BeforeAll
    public static void beforeAll(){
        Main.DATA_FILE = new File(tempDir, "employees.dat");
        rv = new RegisterView();
        ec = new EmployeeController();
        ac = new AdminController(ec);
        lv = new LoginView();
        Main.seedData();
        ac.ec.loadUsersFromFile(Main.DATA_FILE, Main.employeesAll);
    }
    @BeforeEach
    public void beforeEach() {
        usernameField = lookup("#username_field").query();
        passwordField = lookup("#password_field").query();
        loginButton = lookup("#login_button").query();
    }

    @Test
    void logInLogOut(){
        sleep(500);
        usernameField.setText("admin");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();

        Button logout = lookup("#signout").query();
        clickOn(logout);
        WaitForAsyncUtils.waitForFxEvents();

        Button found = (lookup("#login_button").query());
        assertEquals(loginButton.getText(), found.getText());
    }
    @Test
    void logInWrongCredentials(){
        sleep(500);
        usernameField.setText("admin");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("12345678"); //wrong password
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();
        Button alert = skipAlertButton();
        Label alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);


        WaitForAsyncUtils.waitForFxEvents();
        usernameField.setText("admin1");// wrong username
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();
        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);

        //both username and password are wrong
        WaitForAsyncUtils.waitForFxEvents();
        usernameField.setText("admin1");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("12345678");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();
        alert = skipAlertButton();
        alertHeader = alertMessageHeader();
        assertEquals("Enter the correct username and passw", alertHeader.getText());
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(alert);
        Button found = lookup("#login_button").query();
        assertEquals(loginButton, found);
    }

    @Test
    @DisplayName("Add Employee ")
    void test1() {
        sleep(500);
        usernameField.setText("admin");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();

        Button addEmployee = lookup("#add").query();
        clickOn(addEmployee);
        WaitForAsyncUtils.waitForFxEvents();

        firstNameField = lookup("#firstname").query();
        firstNameField.setText("Keit");
        WaitForAsyncUtils.waitForFxEvents();

        surnameField = lookup("#surname").query();
        surnameField.setText("Nika");
        WaitForAsyncUtils.waitForFxEvents();

        emailField = lookup("#email").query();
        emailField.setText("knika@gmail");
        WaitForAsyncUtils.waitForFxEvents();

        userField = lookup("#user").query();
        userField.setText("keit");
        WaitForAsyncUtils.waitForFxEvents();

        passF = lookup("#pass").query();
        passF.setText("password123");
        WaitForAsyncUtils.waitForFxEvents();

        vpassF = lookup("#vpass").query();
        vpassF.setText("password122");
        WaitForAsyncUtils.waitForFxEvents();

        lib = lookup("#lib").query();
        WaitForAsyncUtils.waitForFxEvents();

        phoneF = lookup("#phone").query();
        phoneF.setText("355698263541");
        WaitForAsyncUtils.waitForFxEvents();

        birthdayDatePicker = lookup("#birth").query();
        birthdayDatePicker.setValue(LocalDate.of(2004, 10, 5));
        WaitForAsyncUtils.waitForFxEvents();

        //try to enter employee with invalid inputs
        button = lookup("#signup").query();
        clickOn(button);
        WaitForAsyncUtils.waitForFxEvents();
        skipAlert();

        sleep(500);
        emailField.setText("knika@gmail.com");//entering valid email address
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(button);
        WaitForAsyncUtils.waitForFxEvents();

        skipAlert();
        WaitForAsyncUtils.waitForFxEvents();

        vpassF.setText("password123");//correct password
        clickOn(button);
        WaitForAsyncUtils.waitForFxEvents();
        skipAlert();
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(lib);//choose a role
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(button);
        WaitForAsyncUtils.waitForFxEvents();
        skipAlert();
        goBack = lookup("#previous").query();
        clickOn(goBack);
        WaitForAsyncUtils.waitForFxEvents();
        signOut = lookup("#signout").query();
        clickOn(signOut);
        assertEquals(4, Main.employeesAll.size());
    }

    @Test
    @DisplayName("Remove Employee")
    void test2() {
        sleep(500);
        usernameField.setText("admin");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();

        Button removeEmployee = lookup("#remove").query();
        clickOn(removeEmployee);
        WaitForAsyncUtils.waitForFxEvents();

        //incorrect credentials firstly
        firstNameF = lookup("#nameF").query();
        firstNameF.setText("Keiti");
        WaitForAsyncUtils.waitForFxEvents();

        surnameF = lookup("#surnameF").query();
        surnameF.setText("Nika");
        WaitForAsyncUtils.waitForFxEvents();

        userF = lookup("#usernameF").query();
        userF.setText("keit");
        WaitForAsyncUtils.waitForFxEvents();

        // Click the delete button
        delete = lookup("#delete_button").query();
        clickOn(delete);
        WaitForAsyncUtils.waitForFxEvents();

        skipAlert();
        WaitForAsyncUtils.waitForFxEvents();
        //enter correct name
        firstNameF.setText("Keit");
        sleep(500);
        clickOn(delete);
        skipAlert();
        WaitForAsyncUtils.waitForFxEvents();


        firstNameF.setText("Keit");
        WaitForAsyncUtils.waitForFxEvents();

        surnameF.setText("Nika");
        WaitForAsyncUtils.waitForFxEvents();

        userF.setText("keit");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(delete);
        skipAlert();
        WaitForAsyncUtils.waitForFxEvents();

        Employee fired = ec.searchEmployee("keit");
        assertEquals("Fired",fired.getStatus());

        goBack = lookup("#goBack").query();
        clickOn(goBack);
        WaitForAsyncUtils.waitForFxEvents();
        signOut = lookup("#signout").query();
        clickOn(signOut);

    }

    @Test
    @DisplayName("Test Employee Statistics View")
    void testEmployeeStatView() {
        sleep(500);
        usernameField.setText("admin");
        WaitForAsyncUtils.waitForFxEvents();
        passwordField.setText("p455w0r8");
        WaitForAsyncUtils.waitForFxEvents();
        this.clickOn(loginButton);
        WaitForAsyncUtils.waitForFxEvents();

        Button viewEmployeeStats = lookup("#manage").query();
        clickOn(viewEmployeeStats);
        WaitForAsyncUtils.waitForFxEvents();

        TableView<Employee> employeeTable = lookup("#table").query();
        assertFalse(employeeTable.getItems().isEmpty());

        Employee e = employeeTable.getItems().getFirst();
        assertEquals("admin", e.getUsername());

        goBack = lookup("#previous").query();
        clickOn(goBack);

    }


    private void skipAlert() {
        Button okButton;
        okButton = lookup(".button").queryAllAs(Button.class)
                .stream()
                .filter(button -> "OK".equals(button.getText()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("OK button not found!"));
        clickOn(okButton);
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

}
package test;
import controller.AdminController;
import controller.EmployeeController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;
import model.Access;
import model.Employee;
import model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import view.EditEmployee;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class EditEmployeeIntegrationTest extends ApplicationTest {

    Button goBackButton, editRoleButton, editPermissionButton, saveButton,goBackButtonR ,sP, pP;
    RadioButton managerRadioButton;
    CheckBox addBooksBox;
    GridPane editEmployeeGridPane;
    EditEmployee ee;
    AdminController ac;
    TextField firstNameField, lastNameField, userField, salaryField,userFieldR;

    @Start
    public void start(Stage stage){
        ee = new EditEmployee();
        ac = new AdminController();
        Scene sc = ee.showView(stage,ac);
        stage.setScene(sc);
        stage.show();
    }
    @BeforeEach
    void setUp(){
        editEmployeeGridPane = new GridPane();
        goBackButton = lookup("#goBack").query();
        goBackButton.setText("Previous");

        editRoleButton = lookup("#editRole").query();
        editRoleButton.setText("Edit Role");

        editPermissionButton = lookup("#editPermission").query();
        editPermissionButton.setText("Edit Permissions");
    }
    @Test
    void testButtons(){
        assertThat(goBackButton).hasText("Previous");
        goBackButton.setOnAction(e->goBackButton.setText("clicked"));
        clickOn(goBackButton);
        sleep(500);
        assertEquals("clicked",goBackButton.getText());

        sleep(500);
        assertThat(editRoleButton).hasText("Edit Role");
        editRoleButton.setOnAction(e->editRoleButton.setText("edited Role"));
        clickOn(editRoleButton);
        sleep(500);
        assertEquals("edited Role",editRoleButton.getText());

        sleep(500);
        assertThat(editPermissionButton).hasText("Edit Permissions");
        editPermissionButton.setOnAction(e->editPermissionButton.setText("edited permission"));
        clickOn(editPermissionButton);
        sleep(500);
        assertEquals("edited permission",editPermissionButton.getText());

    }
    @Test
    void testTextFieldsAndButtons() {
        firstNameField = lookup("#name").query();
        lastNameField = lookup("#last").query();
        userField = lookup("#u").query();
        firstNameField.setText("Man");
        lastNameField.setText("Ager");
        userField.setText("manager");

        editRoleButton.setOnAction(e->editRoleButton.setText("edited Role"));
        clickOn(editRoleButton);
        sleep(500);
        assertEquals("edited Role",editRoleButton.getText());

        editPermissionButton.setOnAction(e->editPermissionButton.setText("edited permission"));
        clickOn(editPermissionButton);
        sleep(500);
        assertEquals("edited permission",editPermissionButton.getText());

        goBackButton.setOnAction(e->goBackButton.setText("clicked"));
        clickOn(goBackButton);
        sleep(500);
        assertEquals("clicked",goBackButton.getText());

    }
    @Test
    void testEmptyTextField() {
        firstNameField = lookup("#name").query();
        lastNameField = lookup("#last").query();
        userField = lookup("#u").query();
        firstNameField.clear();
        lastNameField.clear();
        userField.clear();
        clickOn(editRoleButton);
        skipAlert();
        assertThat(editRoleButton).hasText("Edit Role");
        clickOn(editPermissionButton);
        skipAlert();
        assertThat(editPermissionButton).hasText("Edit Permissions");
        goBackButton.setOnAction(e->goBackButton.setText("clicked"));
        clickOn(goBackButton);
        sleep(500);
        assertEquals("clicked",goBackButton.getText());
    }

    @Test
    @DisplayName("Edit Role View")
    void test() throws IOException {
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        // Create a temporary file for testing
        File tempFile = File.createTempFile("employeeTest", ".dat");
        tempFile.deleteOnExit();

        EmployeeController ec = new EmployeeController();

        // Create the employee in the temporary file
        boolean isCreated = ec.create(e, tempFile,Main.employeesAll);
        System.out.println("Employee created: " + isCreated);
        assertTrue(isCreated, "Employee creation failed in temporary file");
        // Ensure the employee was added by searching it in the temporary file
        Employee createdEmployee = ec.searchEmployee("librarianKeit");
        assertNotNull(createdEmployee);
        assertEquals("librarianKeit", createdEmployee.getUsername(), "Username mismatch");

        firstNameField = lookup("#name").query();
        lastNameField = lookup("#last").query();
        userField = lookup("#u").query();
        firstNameField.setText("keit");
        lastNameField.setText("nika");
        userField.setText("librarianKeit");
        WaitForAsyncUtils.waitForFxEvents();

        editRoleButton = lookup("#editRole").query();
        clickOn(editRoleButton);
        WaitForAsyncUtils.waitForFxEvents();

        userField.setText(ec.searchEmployee("librarianKeit").getUsername());
        managerRadioButton = lookup("#manager").query();
        clickOn(managerRadioButton);
        WaitForAsyncUtils.waitForFxEvents();
        double salary = 400.0;
        salaryField = lookup("#salary").query();
        salaryField.setText(String.valueOf(salary));
        WaitForAsyncUtils.waitForFxEvents();


        saveButton = lookup("#save").query();
        assertThat(saveButton).hasText("Save");
        saveButton.setOnAction(ee -> {
            e.setRole(Role.MANAGER);
            saveButton.setText("changed");
        });
        clickOn(saveButton);
        WaitForAsyncUtils.waitForFxEvents();
        goBackButtonR = lookup("#goBack").query();
        assertThat(goBackButtonR).hasText("Go Back");
        goBackButtonR.setOnAction(e1 -> goBackButton.setText("Going Back"));
        clickOn(goBackButtonR);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Role.MANAGER, e.getRole());
        assertEquals("Going Back", goBackButton.getText());
    }

    @Test
    @DisplayName("Edit Permission View")
    void testI() throws IOException {
        Employee e1 = new Employee("librarianKeiti", "12345678kn", "keiti", "nika", "keittn@gmail.com", Role.LIBRARIAN, "355695214074", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, new Date(0));
        File tempFile = File.createTempFile("employeeTest2", ".dat");
        tempFile.deleteOnExit();
        EmployeeController ec = new EmployeeController();
        boolean isCreated = ec.create(e1, tempFile,Main.employeesAll);
        assertTrue(isCreated);
        Employee createdEmployee = ec.searchEmployee("librarianKeiti");
        assertNotNull(createdEmployee);
        assertEquals("librarianKeiti", createdEmployee.getUsername());

        firstNameField = lookup("#name").query();
        lastNameField = lookup("#last").query();
        userField = lookup("#u").query();
        firstNameField.setText("keiti");
        lastNameField.setText("nika");
        userField.setText("librarianKeiti");
        WaitForAsyncUtils.waitForFxEvents();
        editPermissionButton = lookup("#editPermission").query();
        clickOn(editPermissionButton);
        WaitForAsyncUtils.waitForFxEvents();
        addBooksBox = lookup("#addBooks").query();
        clickOn(addBooksBox);
        WaitForAsyncUtils.waitForFxEvents();
        sP = lookup("#saveP").query();
        assertThat(sP).hasText("Save");
        sP.setOnAction(ep ->{
            e1.setAddBooks(Access.YES);
            sP.setText("changed");
        });
        clickOn(sP);
        WaitForAsyncUtils.waitForFxEvents();
        pP = lookup("#previousP").query();
        assertThat(pP).hasText("Previous");
        pP.setOnAction(e -> pP.setText("Going Back"));
        clickOn(pP);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Access.YES, e1.getAddBooks());
        assertEquals("Going Back", pP.getText());
    }


    private void skipAlert() {
        Button okButton = lookup(".button").queryAllAs(Button.class)
                .stream()
                .filter(button -> "OK".equals(button.getText()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("OK button not found in alert"));

        clickOn(okButton);
        sleep(500);
    }


}
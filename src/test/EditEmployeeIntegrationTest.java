package test;

import controller.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import view.EditEmployee;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

class EditEmployeeIntegrationTest extends ApplicationTest {

    Button goBackButton, editRoleButton, editPermissionButton;
    GridPane editEmployeeGridPane;
    EditEmployee ee;
    AdminController ac;
    TextField firstNameField, lastNameField, userField;
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
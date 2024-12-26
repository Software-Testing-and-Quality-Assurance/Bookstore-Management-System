package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Employee;
import model.Role;

public class EditRoleView {
    private final TextField usernameField = new TextField();
    private final TextField salaryField = new TextField();
    private final ToggleGroup tgRole = new ToggleGroup();
    private final RadioButton librarianRadioButton = new RadioButton("Librarian");
    private final RadioButton managerRadioButton = new RadioButton("Manager");
    private final AdminController adminController;
    private Scene previousScene;

    public EditRoleView(Stage parentStage, AdminController adminController, String username, Scene previousScene) {
        this.adminController = adminController;
        this.previousScene = previousScene;
        Employee employee = adminController.ec.searchEmployee(username);

        if (employee != null) {
            usernameField.setText(username);
            salaryField.setText(String.valueOf(employee.getSalary()));

            librarianRadioButton.setToggleGroup(tgRole);
            managerRadioButton.setToggleGroup(tgRole);

            if (employee.getRole() == Role.LIBRARIAN) {
                librarianRadioButton.setSelected(true);
            } else {
                managerRadioButton.setSelected(true);
            }
        }
    }

    public Scene showView(Stage parentStage, String username) {
        String commonStyleL = " -fx-text-fill: white; -fx-font-size: 14px;";

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #614840;");

        Label userL = new Label("Username:");
        Label salaryL = new Label("Salary:");
        Label roleL = new Label("Role:");
        
        userL.setStyle(commonStyleL);
        salaryL.setStyle(commonStyleL);
        roleL.setStyle(commonStyleL);

        gridPane.add(userL, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(salaryL, 0, 2);
        gridPane.add(salaryField, 1, 2);
        gridPane.add(roleL, 0, 3);
        
        librarianRadioButton.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        managerRadioButton.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        
        HBox roleBox = new HBox(10, librarianRadioButton, managerRadioButton);
        gridPane.add(roleBox, 1, 3);
        
        Button saveButton = new Button("Save");
        Button goBackButton = new Button("Go Back");
        
        saveButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 13px;");
        goBackButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 13px;");
        
        gridPane.add(saveButton, 0, 4);
        gridPane.add(goBackButton, 1, 4);

        saveButton.setOnAction(e -> handleSaveAction(username, parentStage));
        goBackButton.setOnAction(e -> {
            parentStage.setScene(previousScene);
            parentStage.show();
        });

        Scene scene = new Scene(gridPane, 400, 200);
        scene.setFill(javafx.scene.paint.Color.web("#614840"));
        parentStage.setTitle("Edit role");
        return scene;
    }

    private void handleSaveAction(String username, Stage parentStage) {
        Employee employee = adminController.ec.searchEmployee(username);

        if (employee != null) {
            String newRole = getSelectedRole();

            try {
                double newSalary = Double.parseDouble(salaryField.getText());

                if (newRole != null && !newRole.equals(employee.getRole().toString())) {
                    adminController.updateEmployee(username, newSalary, Role.valueOf(newRole), null, null, null, null);
                    showSuccessAlert("Role and Salary Updated Successfully", "The role of " + employee.getName() +
                            " has been updated to " + newRole + " and the salary has been updated to " + newSalary);
                } else {
                    showErrorAlert("Cannot Change Salary Without Changing Role", "Please select a new role to change the salary.");
                }
            } catch (NumberFormatException ex) {
                showErrorAlert("Invalid Salary Format", "Please enter a valid salary.");
            }
        }
    }

    private String getSelectedRole() {
        RadioButton selectedRole = (RadioButton) tgRole.getSelectedToggle();
        return selectedRole != null ? selectedRole.getText().toUpperCase() : null;
    }

    private void showSuccessAlert(String header, String content) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(header);
        successAlert.setContentText(content);


        DialogPane dialogPane = successAlert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #614840; -fx-border-color: #614840; -fx-border-width: 2px;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #614840;");
        dialogPane.lookup(".content").setStyle("-fx-background-color: #614840; -fx-text-fill: white;");
        dialogPane.lookup(".button-bar").setStyle("-fx-background-color: #614840;");

        successAlert.showAndWait();
    }

    private void showErrorAlert(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);

        DialogPane dialogPane = errorAlert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #614840; -fx-border-color: #614840; -fx-border-width: 2px;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #614840;");
        dialogPane.lookup(".content").setStyle("-fx-background-color: #614840; -fx-text-fill: white;");
        dialogPane.lookup(".button-bar").setStyle("-fx-background-color: #614840;");

        errorAlert.showAndWait();
    }
}


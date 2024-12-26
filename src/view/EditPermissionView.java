
package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Access;
import model.Employee;

public class EditPermissionView {

    private final CheckBox createBillBox = new CheckBox("Create Bills");
    private final CheckBox addBooksBox = new CheckBox("Add Books");
    private final CheckBox checkBooksBox = new CheckBox("Check Books");
    private final CheckBox checkLibrarianBox = new CheckBox("Check Librarian");
    private Scene previousScene; 

    public EditPermissionView(Stage parentStage, AdminController ac, String username, Scene previousScene) {
        this.previousScene = previousScene;
        
        Employee employee = ac.ec.searchEmployee(username);
        if (employee != null) {
            createBillBox.setSelected(employee.getCreateBill() == Access.YES);
            addBooksBox.setSelected(employee.getAddBooks() == Access.YES);
            checkBooksBox.setSelected(employee.getCheckBooks() == Access.YES);
            checkLibrarianBox.setSelected(employee.getCheckLibrarian() == Access.YES);
        }
    }

    public Scene showView(Stage parentStage, AdminController ac, String username) {
    	
    	createBillBox.setStyle("-fx-text-fill: white;");
    	addBooksBox.setStyle("-fx-text-fill: white;");
    	checkBooksBox.setStyle("-fx-text-fill: white;");
    	checkLibrarianBox.setStyle("-fx-text-fill: white;");
    	
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #614840;"); 

        gridPane.add(createBillBox, 1, 0);
        gridPane.add(addBooksBox, 1, 1);
        gridPane.add(checkBooksBox, 1, 2);
        gridPane.add(checkLibrarianBox, 1, 3);


        Button saveButton = new Button("Save");
        Button goBackButton = new Button("Previous");
        goBackButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;"); 
        saveButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;");
        gridPane.add(saveButton, 0, 4);
        gridPane.add(goBackButton, 1, 4);

        Scene sc = new Scene(gridPane, 700, 500);

        goBackButton.setOnAction(e -> {
            parentStage.setScene(previousScene);
            parentStage.show();
        });

        saveButton.setOnAction(e -> handleSaveAction(ac, username));
        parentStage.setTitle("Edit Permissions");
        return sc;
    }

    private void handleSaveAction(AdminController ac, String username) {
        Employee employee = ac.ec.searchEmployee(username);
        if (employee != null) {
            employee.setCreateBill(createBillBox.isSelected() ? Access.YES : Access.NO);
            employee.setAddBooks(addBooksBox.isSelected() ? Access.YES : Access.NO);
            employee.setCheckBooks(checkBooksBox.isSelected() ? Access.YES : Access.NO);
            employee.setCheckLibrarian(checkLibrarianBox.isSelected() ? Access.YES : Access.NO);
            ac.ec.updateAll(); 
            showSuccessAlert("Permissions Updated", "Permissions for " + employee.getName() + " have been updated.");
        }
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

}


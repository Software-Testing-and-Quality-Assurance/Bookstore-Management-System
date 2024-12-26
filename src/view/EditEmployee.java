package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import model.*;

public class EditEmployee {

    private Scene previousScene;

    public Scene showView(Stage st, AdminController ac) {
        String commonStyleL = " -fx-text-fill: white; -fx-font-size: 14px;";
        GridPane editEmployeeGridPane = new GridPane();
        editEmployeeGridPane.setHgap(10);
        editEmployeeGridPane.setVgap(10);
        editEmployeeGridPane.setPadding(new Insets(10, 10, 10, 10));
        editEmployeeGridPane.setAlignment(Pos.CENTER);
        editEmployeeGridPane.setStyle("-fx-background-color: #614840;"); 

        Label credentialLabel = new Label("Enter the required information:");
        credentialLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;"); 
        editEmployeeGridPane.add(credentialLabel, 0, 0, 2, 1);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameLabel.setStyle(commonStyleL);
        editEmployeeGridPane.add(firstNameLabel, 0, 2);
        editEmployeeGridPane.add(firstNameField, 1, 2);

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameLabel.setStyle(commonStyleL);
        editEmployeeGridPane.add(lastNameLabel, 0, 3);
        editEmployeeGridPane.add(lastNameField, 1, 3);

        Label usernameLabel = new Label("Username:");
        TextField userField = new TextField();
        usernameLabel.setStyle(commonStyleL);
        editEmployeeGridPane.add(usernameLabel, 0, 4);
        editEmployeeGridPane.add(userField, 1, 4);

        HBox editButtons = new HBox(10); 
        Button editRoleButton = new Button("Edit Role");
        Button editPermissionButton = new Button("Edit Permissions");
        Button goBackButton = new Button("Previous");
        goBackButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 13px;"); 
        editRoleButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 13px;"); 
        editPermissionButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 13px;"); 
        editButtons.getChildren().addAll(editRoleButton, editPermissionButton, goBackButton);

        editEmployeeGridPane.add(editButtons, 1, 5); 

        Scene sc = new Scene(editEmployeeGridPane);
        st.setWidth(700);
        st.setHeight(500);

        goBackButton.setOnAction(e -> {
            AdminView mv = new AdminView();
            Scene scene = mv.showView(st);
            st.setScene(scene);
            st.show();
        });

        editRoleButton.setOnAction(e -> {
            String username = userField.getText();
            if(firstNameField.getText().equals("") || lastNameField.getText().equals("") || userField.getText().equals("")) {
                Main.emptyAlert();
                return;
            }
            Scene previousScene = sc;  
            EditRoleView erv = new EditRoleView(st, ac, username, previousScene);
            Scene ers = erv.showView(st, username);
            st.setScene(ers);
        });

        editPermissionButton.setOnAction(e -> {
            if(firstNameField.getText().equals("") || lastNameField.getText().equals("") || userField.getText().equals("")) {
                Main.emptyAlert();
                return;
            }
            EditPermissionView epv = new EditPermissionView(st, ac, userField.getText(), sc);
            Scene eps = epv.showView(st, ac, userField.getText());
            st.setScene(eps);           
        });

        return sc;
    }
}


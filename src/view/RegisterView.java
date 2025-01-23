package view;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import model.Role;


public class RegisterView {
	
	public Scene showView(Stage st) {
		
		
		System.out.println(" "+Main.employeesAll.size());
		GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: #6F4E37;");


        String commonStyleF = "-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px;";
        String commonStyleL = " -fx-text-fill: white; -fx-font-size: 14px;";


        TextField firstNameField = new TextField();
        firstNameField.setId("firstname");
        Label firstnameL = new Label("First Name:");
        firstnameL.setId("firstNameL");
        firstNameField.setStyle(commonStyleF);
        firstnameL.setStyle(commonStyleL);
        pane.add(firstnameL, 0, 0);
        pane.add(firstNameField, 1, 0);


        TextField surnameField = new TextField();
        surnameField.setId("surname");
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setId("lastName");

        lastNameLabel.setStyle(commonStyleL);
        surnameField.setStyle(commonStyleF);
        pane.add(lastNameLabel, 0, 1);
        pane.add(surnameField, 1, 1);


        TextField emailField = new TextField();
        emailField.setId("email");
        Label emailLabel = new Label("Email:");
        emailLabel.setId("emailL");
        emailLabel.setStyle(commonStyleL);
        emailField.setStyle(commonStyleF);
        pane.add(emailLabel, 0, 2);
        pane.add(emailField, 1, 2);


        TextField userField = new TextField();
        userField.setId("user");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setId("userL");
        usernameLabel.setStyle(commonStyleL);
        userField.setStyle(commonStyleF);
        pane.add(usernameLabel, 0, 3);
        pane.add(userField, 1, 3);


        PasswordField passF = new PasswordField();
        passF.setId("pass");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setId("passL");
        passwordLabel.setStyle(commonStyleL);
        passF.setStyle(commonStyleF);
        pane.add(passwordLabel, 0, 4);
        pane.add(passF, 1, 4);


        PasswordField vpassF = new PasswordField();
        vpassF.setId("vpass");
        Label verifyPasswordLabel = new Label("Verify Password:");
        verifyPasswordLabel.setId("vpassL");
        verifyPasswordLabel.setStyle(commonStyleL);
        vpassF.setStyle(commonStyleF);
        pane.add(verifyPasswordLabel, 0, 5);
        pane.add(vpassF, 1, 5);


        ToggleGroup tgRole = new ToggleGroup();
        RadioButton lib = new RadioButton("Librarian");
        lib.setId("lib");
        RadioButton man = new RadioButton("Manager");
        man.setId("man");
        lib.setStyle("-fx-text-fill: white;");
        man.setStyle("-fx-text-fill: white;");
        lib.setToggleGroup(tgRole);
        man.setToggleGroup(tgRole);

        Label roleLabel = new Label("Role");
        roleLabel.setId("role");
        roleLabel.setStyle(commonStyleL);
        pane.add(roleLabel, 0, 6);

        HBox choice = new HBox();
        choice.setId("choice");
        choice.getChildren().addAll(lib, man);
        pane.add(choice, 1, 6);


        TextField phoneF = new TextField();
        phoneF.setId("phone");
        Label phoneLabel = new Label("Phone");
        phoneLabel.setId("phoneL");
        phoneLabel.setStyle(commonStyleL);
        phoneF.setStyle(commonStyleF);
        pane.add(phoneLabel, 0, 7);
        pane.add(phoneF, 1, 7);


        DatePicker birthdayDatePicker = new DatePicker();
        birthdayDatePicker.setId("birth");
        Label birthdayLabel = new Label("Birthday:");
        birthdayLabel.setId("birthLabel");
        birthdayLabel.setStyle(commonStyleL);
        birthdayDatePicker.getEditor().setStyle(commonStyleF);
        pane.add(birthdayLabel, 0, 8);
        pane.add(birthdayDatePicker, 1, 8);


        Button button = new Button("Sign Up");
        button.setId("signup");
        button.setStyle("-fx-background-color: #A3917B; -fx-text-fill: white; -fx-font-size: 14px;");
        pane.add(button, 1, 9);


        Button goBack = new Button("Previous");
        goBack.setId("previous");
        goBack.setStyle("-fx-background-color: #A3917B; -fx-text-fill: white; -fx-font-size: 14px;");
        pane.add(goBack, 0, 9);

        AdminController ac = new AdminController();
        goBack.setOnAction(e -> {
            st.setScene(Main.properView(st));
            st.show();
        });
		
		button.setOnAction(e->  {
			 if(firstNameField.getText().isEmpty() || surnameField.getText().isEmpty() || userField.getText().isEmpty() || passF.getText().isEmpty()
					 || phoneF.getText().isEmpty() || vpassF.getText().isEmpty() || birthdayDatePicker.getValue()==null) {
				 Main.emptyAlert();
				 return;
			 }
			 String firstName = firstNameField.getText();
			 String lastName = surnameField.getText();
			 String email = emailField.getText();
			 String username = userField.getText();
			 String password = passF.getText();
			 String phone= phoneF.getText();
			 String verifyPassword = vpassF.getText();
			 LocalDate lD1 = birthdayDatePicker.getValue();
			 Instant instant1 = Instant.from(lD1.atStartOfDay(ZoneId.systemDefault()));
			 Date birthday = Date.from(instant1);
			 Role role;
             if((!email.matches(".*@(gmail\\.com|hotmail\\.com|yahoo\\.com)"))){
                 Alert pAlert = new Alert(AlertType.ERROR);
                 pAlert.setHeaderText("Invalid Email");
                 pAlert.setContentText("Invalid Email.");
                 pAlert.showAndWait();
                 return;
             }
             if(!phone.matches("3556[7-9]\\d{7}")){
                 Alert pAlert = new Alert(AlertType.ERROR);
                 pAlert.setHeaderText("Invalid Phone Number");
                 pAlert.setContentText("Enter valid phone number.");
                 pAlert.showAndWait();
                 return;
             }
			if (!password.equals(verifyPassword)) {
				    Alert pAlert = new Alert(AlertType.ERROR);
				    pAlert.setHeaderText("Password Mismatch");
				    pAlert.setContentText("Passwords do not match.");
				    pAlert.showAndWait();
				    return;
				}
			if (lib.isSelected()) {
					 role = Role.LIBRARIAN;
			} else if (man.isSelected()) {
					 role = Role.MANAGER;}
			else{
					Alert nah = new Alert(AlertType.ERROR);
					nah.setHeaderText("Choose a role!");
					nah.setContentText("Pick a role");
					nah.showAndWait();
					return;
				}
				System.out.println("Librarian Selected: " + lib.isSelected());
				System.out.println("Manager Selected: " + man.isSelected());
				System.out.println("Role: " + role);
                boolean addE = false;
                try { addE = ac.addEmployee(username, password, firstName, lastName, email, phone, role==Role.LIBRARIAN?60000.0:80000.0, role, birthday);
                } catch (IllegalArgumentException y){
                    Alert al = new Alert(AlertType.ERROR);
                    al.setHeaderText("Invalid Entry");
                    al.setContentText(y.getMessage());
                    al.showAndWait();
                    System.out.println(y.getMessage());
                }

				if (addE) {
					Main.doneAlert();
					firstNameField.clear();
					surnameField.clear();
					emailField.clear();
					userField.clear();
					passF.clear();
					vpassF.clear();
					phoneF.clear();
					tgRole.selectToggle(null);
					birthdayDatePicker.setValue(null);
					}

		});

		Scene sc = new Scene(pane);
		st.setWidth(500);
		st.setHeight(700);
		return sc;
		}
}
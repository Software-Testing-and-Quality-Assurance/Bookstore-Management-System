package view;

import main.Main;
import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import model.Employee;
import model.Role;

public class LoginView {
    public Scene showView(Stage st) {
        Main.currentUser = null;
        GridPane p = new GridPane();

        Text welcomeText = new Text("Welcome to Our Bookstore");
        welcomeText.setFont(new Font("Helvetica", 26));
        welcomeText.setStyle("-fx-fill: white;");
 


        p.setHgap(10);
        p.setVgap(12); 
        p.setAlignment(Pos.CENTER);

        p.setStyle("-fx-background-image: url('file:Desktop.jpg');"
                + "-fx-background-size: cover;");

        p.add(welcomeText, 0, 0, 2, 1); 

        Label username = new Label("Username:");
        TextField userF = new TextField();
        username.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-underline: true;");
        userF.setStyle("-fx-background-color: #3c3c3c; -fx-text-fill: white;");
        userF.setPadding(new Insets(5, 10, 5, 10));
        p.add(username, 0, 1);
        p.add(userF, 1, 1);

        Label passw = new Label("Password: ");
        PasswordField passwF = new PasswordField();

        passw.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-underline: true;");
        passwF.setStyle("-fx-background-color: #3c3c3c; -fx-text-fill: white;");
        passwF.setPadding(new Insets(5, 10, 5, 10));
        p.add(passw, 0, 2);
        p.add(passwF, 1, 2);
       

        Button login = new Button("Log in");
        HBox h = new HBox();
        h.getChildren().addAll(login);
        h.setSpacing(10);

        login.setStyle("-fx-background-color: #352421; -fx-text-fill: white;");
        p.add(h, 1, 4);


        login.setOnAction(e -> {

			LoginController lc = new LoginController();
			Employee employee = lc.login(userF.getText(), passwF.getText());

			if (employee != null) {
				if (!(employee.getDateTerminated()==null)) {
					Alert al = new Alert(AlertType.WARNING);
					al.setHeaderText("You are FIRED!");
					al.setContentText("It's not you, it's us. I am sorry you had to find out this way.");
					al.showAndWait();
					userF.clear();
					passwF.clear();
				}
				
				else if(employee.getRole() == Role.LIBRARIAN) {
					LibrarianView lv = new LibrarianView();
					Main.currentUser = employee;
					st.setScene(lv.showView(st));				
					}
				else if (employee.getRole()== Role.MANAGER) {
					
					for(Book b: Main.bookStock) {
						if(b.getStock()<5)
						{
							Alert al = new Alert (AlertType.INFORMATION);
							al.setHeaderText("Book stock less than 5");
							al.setContentText("Book "+b.getTitle()+" has stock of "+ b.getStock());
							al.showAndWait();
						}
					}
					ManagerView mv = new ManagerView();
					Main.currentUser = employee;
					st.setScene(mv.showView(st));
					
					}
					
				else if (employee.getRole()==Role.ADMIN) {
					AdminView av = new AdminView();
					Main.currentUser = employee;
					st.setScene(av.showView(st));
				}
				else {
					Alert pass = new Alert(Alert.AlertType.ERROR);
					pass.setHeaderText("Invalid password");
					pass.showAndWait();
				}
			} 
			else {
				Alert al = new Alert(AlertType.ERROR);
				al.setHeaderText("Enter the correct username and passw");
				al.showAndWait();
				userF.clear();
				passwF.clear();
			}

		});
		
		st.setTitle("Login");
		Scene sc = new Scene(p, 600, 500);
		
		st.setWidth(600);
		st.setHeight(500);
		st.setResizable(false);
		return sc;
	}
}


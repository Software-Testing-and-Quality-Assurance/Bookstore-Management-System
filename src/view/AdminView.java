package view;
import main.Main;
import controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
public class AdminView {
	AdminController ac = new AdminController();
	Button goBackButton = new Button("Previous");

	public Scene showView(Stage st) {	
		Button addEmployee = new Button("Add a new Employee");
		addEmployee.setMinWidth(175);

		Button removeEmployee = new Button("Remove an Employee");
		removeEmployee.setMinWidth(175);

		Button editEmployee = new Button("Edit Employee data");
		editEmployee.setMinWidth(175);

		Button revenue = new Button("Total cost and income data");
		revenue.setMinWidth(175);

		Button manageEmployee = new Button("Log Employee data");
		manageEmployee.setMinWidth(175);

		Button signOut = new Button("Sign Out");
		signOut.setMinWidth(175);

		Button addBook = new Button("Add books");
		addBook.setMinWidth(175);
		Button billInfo = new Button("Bill info");
		billInfo.setMinWidth(175);
		Button bookInfo = new Button("Book log");
		bookInfo.setMinWidth(175);
		
		Text welcomeText = new Text("Welcome Administrator");
        welcomeText.setFont(new Font("Helvetica", 18));

		GridPane gd = new GridPane(700,800);
		gd.setVgap(20);
		gd.setHgap(20);
		gd.setPadding(new Insets(15,15,15,15));		
		gd.setAlignment(Pos.CENTER);
		gd.add(welcomeText,0,0);
		
		gd.add(addEmployee, 0, 1);
		gd.add(removeEmployee, 1, 1);
		gd.add(editEmployee, 0, 2);
		gd.add(manageEmployee, 1, 2);
		gd.add(revenue, 0, 3);
		gd.add(addBook, 1, 3);
		gd.add(billInfo, 0, 4);
		gd.add(bookInfo, 1, 4);
		gd.add(signOut, 0, 5);

		gd.setStyle("-fx-background-color: #6F4E37;"); 

		addEmployee.setStyle("-fx-background-color: #A76B4D; -fx-text-fill: white;");
		removeEmployee.setStyle("-fx-background-color: #8E6E59; -fx-text-fill: white;");
		editEmployee.setStyle("-fx-background-color: #B0937D; -fx-text-fill: white;");
		revenue.setStyle("-fx-background-color: #B6874E; -fx-text-fill: white;");
		manageEmployee.setStyle("-fx-background-color: #52413c; -fx-text-fill: white;");
		addBook.setStyle("-fx-background-color: #A3917B; -fx-text-fill: white;");
		billInfo.setStyle("-fx-background-color: #B89A78; -fx-text-fill: white;");
		bookInfo.setStyle("-fx-background-color: #5F735F; -fx-text-fill: white;");
		signOut.setStyle("-fx-background-color: #7C3030; -fx-text-fill: white;");

		welcomeText.setStyle("-fx-fill: white;");

		addEmployee.setOnAction(e-> {
			RegisterView rg = new RegisterView();
			st.setScene(rg.showView(st));
			st.show();
		});
		removeEmployee.setOnAction(e-> {
			Scene rg = removeEmployee(st);
			st.setScene(rg);
			st.show();
		});
		revenue.setOnAction(e-> {
			ChooseDates cdv = new ChooseDates();
			cdv.showView(st, "Revenue");
			
		});
		manageEmployee.setOnAction(e-> {
			
			EmployeeStat es = new EmployeeStat();
			es.getTableView().setItems(Main.employeesAll);
			es.showView(st);
		});
		
		editEmployee.setOnAction(e->{
			EditEmployee ee = new EditEmployee();
			st.setScene(ee.showView(st, ac));
			st.show();
		});
		
		addBook.setOnAction(e-> {
			if (Main.currentUser.getAddBooks()==Access.NO) {
	    		Main.accessAlert();
				return;
	    	}
			addExistBook(st);
		});

		billInfo.setOnAction(e->{
			if(Main.currentUser.getCheckLibrarian()==Access.NO) {
	    		Main.accessAlert();
	    		return;
			}
			ChooseDates cdv = new ChooseDates();
			cdv.showView(st, "Bill info");
		});
		
		bookInfo.setOnAction(e -> {
			if(Main.currentUser.getCheckBooks()==Access.NO) {
	    		Main.accessAlert();
	    		return;
			}
			
			ChooseDates cdv = new ChooseDates();
			cdv.showView(st, "Book Bought");
		});
		
		
		signOut.setOnAction(e-> {
			LoginView lv = new LoginView();
			st.setScene(lv.showView(st));
			st.show();
		});
		
		
		st.setTitle("Administrator");
		st.setWidth(550);
	    st.setHeight(600);
	    st.setResizable(false);
		Scene sc = new Scene(gd);
		return sc;
	}
	
	
	public Scene removeEmployee(Stage st) {
	    GridPane rem = new GridPane();
	    rem.setHgap(10);
	    rem.setVgap(10);
	    rem.setPadding(new Insets(10, 10, 10, 10));
	    rem.setAlignment(Pos.CENTER);
	    Label credent = new Label("Enter the required information:");
	    credent.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
	    rem.add(credent, 0, 0);
	    rem.setStyle("-fx-background-color: #614840;");
	    String commonStyleL = " -fx-text-fill: white; -fx-font-size: 14px;";

	    Button delete = new Button("Remove");
	    delete.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;");
	    delete.setMinWidth(100); // Set a fixed width for the button

	    goBackButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;");
	    goBackButton.setMinWidth(100); 

	    Label firstNameL = new Label("First Name:");
	    TextField firstNameField = new TextField();
	    firstNameL.setStyle(commonStyleL);
	    rem.add(firstNameL, 0, 1);
	    rem.add(firstNameField, 1, 1);

	    Label lastNameL = new Label("Last Name:");
	    TextField lastNameField = new TextField();
	    lastNameL.setStyle(commonStyleL);
	    rem.add(lastNameL, 0, 2);
	    rem.add(lastNameField, 1, 2);

	    Label usernameL = new Label("Username:");
	    TextField userField = new TextField();
	    usernameL.setStyle(commonStyleL);
	    rem.add(usernameL, 0, 3);
	    rem.add(userField, 1, 3);

	    rem.add(delete, 0, 4);

	    
	    	delete.setOnAction(e->{
				Employee a = ac.ec.searchEmployee(userField.getText());
				if(a!=null) {
					
					if(a.getName().equals(firstNameField.getText()) && a.getSurname().equals(lastNameField.getText())) {
						if(a.getDateTerminated()!=null) {
							Alert pAlert = new Alert(AlertType.ERROR);
							pAlert.setHeaderText("Already Fired");
							pAlert.setContentText("This employee user is already fired.");
							pAlert.showAndWait();
						}
						else{
							ac.removeEmployee(a.getUsername());
							Main.doneAlert();}
					}
					else {
						Main.incorrectAlert();
						return;
					}
				} else {
					Alert pAlert = new Alert(AlertType.ERROR);
					pAlert.setHeaderText("Incorrect Credentials");
					pAlert.setContentText("Employee does not exist!");
					pAlert.showAndWait();
				}
				
				userField.clear();
				firstNameField.clear();
				lastNameField.clear();
				
			});	    

	    rem.add(goBackButton, 1, 4);
	    Scene sc = new Scene(rem);
	    st.setWidth(500);
	    st.setHeight(700);
	    st.setResizable(false);
	    goBackButton.setOnAction(e -> {
	        st.setScene(Main.properView(st));
	        st.show();
	    });
	    return sc;
	}

	
	
	public void addExistBook(Stage st) {
	    GridPane g = new GridPane();
	    g.setVgap(20); 
	    g.setHgap(10); 

	    Label isbnL = new Label("Book isbn:");
	    TextField isbnF = new TextField();
	    g.add(isbnL, 0, 0);
	    g.add(isbnF, 1, 0);

	    Label stockLabel = new Label("Nr of Books:");
	    TextField stockF = new TextField();
	    g.add(stockLabel, 0, 2);
	    g.add(stockF, 1, 2);

	    isbnL.setStyle("-fx-text-fill: white;");
	    stockLabel.setStyle("-fx-text-fill: white;");

	    Button addNew = new Button("Add new book");
	    Button add = new Button("Add existing book");

	    addNew.setStyle("-fx-background-color:#69040b ; -fx-text-fill: white;"); 
	    add.setStyle("-fx-background-color: #630607; -fx-text-fill: white;");

	    // Add some padding to the buttons
	    addNew.setPadding(new Insets(10));
	    add.setPadding(new Insets(10));

	    g.add(addNew, 0, 4);
	    g.add(add, 1, 4);

	    g.setAlignment(Pos.CENTER);
	    g.setStyle("-fx-background-color: #614840;"); 
	    st.setScene(new Scene(g));
	    st.setWidth(600);
	    st.setHeight(600);
	    st.show();

	    add.setOnAction(e -> {
	        ManagerController m = new ManagerController();
	        BookController b = new BookController();

	        try {
	            String isbn = isbnF.getText();
	            int stock = Integer.parseInt(stockF.getText());
	            if (isbn.equals("")) {
	                throw new IllegalArgumentException();
	            }
	            if (m.addExist(isbn, stock)) {
	                Main.doneAlert();
	                st.setScene(Main.properView(st));
	                st.show();
	            } else {
	                isbnF.clear();
	                stockF.clear();
	                Alert al = new Alert(AlertType.ERROR);
	                al.setHeaderText("No book found");
	                al.setContentText("Our bookstore does not have a book with this ISBN. Enter an existing book, or press \"Add new book\"");
	                al.getDialogPane().setStyle("-fx-background-color: #614840; -fx-border-color: #8E5B4A; -fx-border-width: 2px;");
	                al.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #8E5B4A;");
	                al.getDialogPane().lookup(".content").setStyle("-fx-background-color: #614840;");
	                al.getDialogPane().lookup(".button-bar").setStyle("-fx-background-color: #8E5B4A;");
	                al.showAndWait();
	            }
	        } catch (IllegalArgumentException e2) {
	            Alert nah = new Alert(AlertType.ERROR);
	            nah.setHeaderText("Enter valid input");
	            nah.setContentText("Enter a text and a number! ");
	            nah.getDialogPane().setStyle("-fx-background-color: #8E5B4A; -fx-border-color: #834333; -fx-border-width: 2px;");
	            nah.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #834333;");
	            nah.getDialogPane().lookup(".content").setStyle("-fx-background-color:#630607");
	            nah.getDialogPane().lookup(".button-bar").setStyle("-fx-background-color: #834333");
	            nah.showAndWait();

	        }
	    });

	    addNew.setOnAction(e -> {
	        AddBookView abv = new AddBookView();
	        st.setScene(abv.showView(st));
	        st.setWidth(500);
	        st.setHeight(700);
	        st.show();
	    });
	}
}


package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import controller.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;
import view.*;

public class Main extends Application{
	public static ObservableList<Employee> employeesAll = FXCollections.observableArrayList();
	public static ObservableList<Book> bookStock = FXCollections.observableArrayList();
	public static ObservableList<TotalBill> allBills = FXCollections.observableArrayList();

    public static Map<String, ArrayList<TotalBill>> billsPerLibrarian;
	public static Employee currentUser;
	public static int billId;
	
	public static File id;
	public static File DATA_FILE;
	public static File BOOK_FILE;
	public static File ALL_BILLS_FILE;
	public static String PRINT_BILL_PATH;
	public static File PRINT_BILL_FILE;

	@Override
	public void start(Stage s) {
		id = new File("last_assigned_id.txt");
		DATA_FILE= new File("employees.dat");
		BOOK_FILE= new File("books.dat");
		ALL_BILLS_FILE= new File("allBills.dat");
		PRINT_BILL_PATH = "printBill";

		seedData();
		readLastAssignedId();
		AdminController ac = new AdminController();
		ac.ec.loadUsersFromFile(Main.DATA_FILE,Main.employeesAll);
		BookController bs = new BookController();
		bs.loadBooksFromFile(Main.BOOK_FILE, Main.bookStock);
		TotalBillController tbc = new TotalBillController();
		tbc.loadBillsFromFile();


		LoginView lv = new LoginView();
		s.setTitle("Welcome to BookStore");
		s.setScene(lv.showView(s));
		s.show();
		
	}
	
	@Override
	public void stop() {
		saveLastAssignedId();
		}
	
	
	public static void saveLastAssignedId() {
        try (PrintWriter writer = new PrintWriter(id)) {
            writer.println(billId);
        } catch (FileNotFoundException e) {
            //log
        }
   	}
	
	public static void readLastAssignedId() {
        try (Scanner in = new Scanner(id)) {
            billId= in.nextInt();
        } catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
   	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void accessAlert() {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("You do not have access to this action!");
		a.setHeaderText("User info");
		a.getDialogPane().setId("custom-alert-pane");
		a.showAndWait();
	}
	public static void doneAlert() {
		Alert successA = new Alert(AlertType.INFORMATION);
		successA.setHeaderText("Done");
		successA.showAndWait();
	}
	
	public static void emptyAlert() {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText("Empty Fields!");
		a.setHeaderText("You cannot leave any field empty");
		a.showAndWait();
	}
	
	public static void incorrectAlert() {
		Alert pAlert = new Alert(AlertType.ERROR);
		pAlert.setHeaderText("Incorrect Credentials");
		pAlert.setContentText("Enter correct information of employees.");
		pAlert.showAndWait();
	}
	
	public static void invalidAlert() {
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Invalid input");
	    alert.setContentText("Please enter valid input.");
		alert.getDialogPane().setId("custom-alert-pane");
	    alert.showAndWait();
	}
	
	public static Scene properView(Stage st) {
		if(currentUser.getRole()==Role.LIBRARIAN)
			return new LibrarianView().showView(st);
		else if (currentUser.getRole()==Role.MANAGER)
			return new ManagerView().showView(st);
		else
			return new AdminView().showView(st);
	}
	
	public static void seedData() {
		if(Main.DATA_FILE.length() == 0) {
            Employee[] employees = {
            		new Employee("admin", "p455w0r8", "Admin", "Istrator", "admin@gmail.com", Role.ADMIN, "355691212122", 100000,
            	    		Access.YES, Access.YES, Access.YES, Access.YES, new Date(0)),
            		new Employee("manager", "p455w0r8", "Man", "Ager", "man@gmail.com", Role.MANAGER, "355691234567", 80000,
            	    		Access.NO, Access.YES, Access.YES, Access.YES, new Date(0)),
            		new Employee("librarian", "p455w0r8", "Lib", "Rarian", "lib@gmail.com", Role.LIBRARIAN, "355691111111", 60000,
            	    		Access.YES, Access.NO, Access.NO, Access.NO, new Date(0))};
            
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.DATA_FILE))) {
                for(Employee e : employees) {
                    outputStream.writeObject(e);
                }
            } catch (IOException ex) {
				//log
            }
        }
	}
}

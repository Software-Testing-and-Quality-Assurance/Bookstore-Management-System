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
	public static ObservableList<TotalBill> allbills= FXCollections.observableArrayList();

    public static Map<String, ArrayList<TotalBill>> billsPerLibrarian;
    public static boolean firstManagerLogin;
	public static Employee currentUser;
	public static int billId;
	
	public static final File id = new File("last_assigned_id.txt");
	public static final File DATA_FILE= new File("employees.dat");	
	public static final File BOOK_FILE= new File("books.dat");
	public static final File ALL_BILLS_FILE= new File("allBills.dat");

	@Override
	public void start(Stage s) throws Exception {
		seedData();
		readLastAssignedId();
		AdminController ac = new AdminController();
		ac.ec.loadUsersFromFile();
		BookController bs = new BookController();
		bs.loadBooksFromFile();
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
            e.printStackTrace();
        }
   	}
	
	public static void readLastAssignedId() {
        try (Scanner in = new Scanner(id)) {
            billId= (int)in.nextInt();
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
		a.showAndWait();
		return;
	}
	public static void doneAlert() {
		Alert successA = new Alert(AlertType.INFORMATION);
		successA.setHeaderText("Done");
		successA.showAndWait();
		return;
	}
	
	public static void emptyAlert() {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText("Empty Fields!");
		a.setHeaderText("You cannot leave any field empty");
		a.showAndWait();
		return;
	}
	
	public static void incorrectAlert() {
		Alert pAlert = new Alert(AlertType.ERROR);
		pAlert.setHeaderText("Incorrect Credentials");
		pAlert.setContentText("Enter correct information of employees.");
		pAlert.showAndWait();
	}
	
	public static void invalidAlert(Stage st) {
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Invalid input");
	    alert.setContentText("Please enter valid input.");
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
	
	private static void seedData() {
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
                // log
            }
        }
	}
}

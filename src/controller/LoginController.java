package controller;
import main.Main;
import model.Employee;
import controller.EmployeeController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class LoginController {
	AdminController ac = new AdminController();
	
	
	public Employee login(String username, String password) {
		for(Employee em: Main.employeesAll) {
			
			if(username.equals(em.getUsername()) && password.equals(em.getPassword())) 
					//System.out.println("Got it");
					
						return em;
			}
		
		
		return null;
	}
	
}

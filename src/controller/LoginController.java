package controller;
import main.Main;
import model.Employee;
public class LoginController {
	
	
	public Employee login(String username, String password) {
		for(Employee em: Main.employeesAll) {
			
			if(username.equals(em.getUsername()) && password.equals(em.getPassword())) return em;
			}
		
		
		return null;
	}
	
}

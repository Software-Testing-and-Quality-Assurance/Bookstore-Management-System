package controller;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import model.Access;
import model.Book;
import model.Role;
import model.TotalBill;
import model.Employee;
public class AdminController {

	public final EmployeeController ec;

	public AdminController(){
		this.ec= new EmployeeController();
	}

	public AdminController(EmployeeController ec){
		this.ec=ec;
	}
	
	public boolean addEmployee(String username, String password, String name, String surname, String email, String phone, double salary, Role role, Date birthday) throws IllegalArgumentException{
		if (username == null || username.isEmpty() ||
				password == null || password.isEmpty() ||
				name == null || name.isEmpty() ||
				surname == null || surname.isEmpty() ||
				email == null || email.isEmpty() ||
				phone == null || phone.isEmpty() ||
				role == null ||
				birthday == null) {
			throw new IllegalArgumentException("Don't leave empty fields!");
		}
		else if(password.length() < 8)
				throw new IllegalArgumentException("Password has to have 8 or more characters!");
		else if(password.equals(username))
			throw new IllegalArgumentException("Password cannot equal the username.");
		else if (!email.matches(".*@(gmail\\.com|hotmail\\.com|yahoo\\.com)"))
			throw new IllegalArgumentException("Enter valid email address.");
		else if (!phone.matches("3556[7-9]\\d{7}"))
			throw new IllegalArgumentException("Enter valid phone number.");
		else if (salary<=0)
			throw new IllegalArgumentException("Salary has to be more than 0.");
		else if (ec.employeeFound(username))
			throw new IllegalArgumentException("This username is taken. Try something else.");
		System.out.println("in Role: " + role);
		String x = "Values extracted: " + name + ", " + surname + ", " + username + " " + phone + " " + " " + password + " " + salary;
		if (role==Role.LIBRARIAN ) {
			System.out.println(x);
			Employee newE = new Employee(username, password, name, surname, email, Role.LIBRARIAN, phone, salary, Access.YES, Access.NO, Access.NO, Access.NO,birthday );
			ec.create(newE);
			return true;
		} else if (role==Role.MANAGER) {
			System.out.println(x);
			Employee newE = new Employee(username, password, name, surname, email, Role.MANAGER, phone, salary,Access.NO, Access.YES, Access.YES, Access.YES,birthday);
			ec.create(newE);
			return true;
		}
		return false;
	}
	
	public void removeEmployee(String user) {
		Employee found = ec.searchEmployee(user);
		found.setStatus("Fired");
		found.setDateTerminated(new Date());
        ec.updateAll();
	}
	
	public double[] revenue(Date beginning, Date end, LocalDate localDate1, LocalDate localDate2) {
		double[] rev = new double[2];
		double totalIncome=0;
		double totalCost=0;
		
		for(Map.Entry<String, ArrayList <TotalBill>> all : Main.billsPerLibrarian.entrySet()) {
			for(TotalBill a : all.getValue()) {
				Date ofBill = a.getOrderDate();
				if(!end.before(ofBill) && !beginning.after(ofBill)) {
					
					totalIncome+=a.getTotalOrderAmount();
				}
			}
		}
	    
	    for(Book one: Main.bookStock) {
	    	for(Map.Entry<Date, Integer> purchase: one.getBoughtPerDate().entrySet()) {
	    		if(!(end.before(purchase.getKey())) && !(beginning.after(purchase.getKey()))) {
	    			totalCost+=purchase.getValue()*one.getPurchasePrice();
	    			System.out.println("Cost per buy: "+purchase.getValue()*one.getPurchasePrice() +" total "+totalCost);
	    		}
	    	}
	    }
	    
	    for(Employee em: Main.employeesAll) {
	    	
	    	if(!(end.before(em.getDateEmployed()))  &&  (em.getDateTerminated()==null || em.getDateTerminated().after(end))){
	    		long monthsBetween = ChronoUnit.MONTHS.between(localDate1.withDayOfMonth(1), localDate2.withDayOfMonth(1));
	    		System.out.println(monthsBetween);
	    		totalCost+=em.getSalary()*monthsBetween;
	    		System.out.println("Cost per employee "+em.getSalary()*monthsBetween); 
	    	}
	    }

		rev[0] = totalIncome;
		rev[1] = totalCost;
	    return rev;
	}
	
	public void updateEmployee(String username, double salary, Role role, Access createBill, Access addBook, Access checkBook, Access checkLib) {
	    try {
	        if (!ec.employeeFound(username)) {
	            throw new Exception("This username does not exist");
	        }

	        Employee employee = ec.searchEmployee(username);
	        		
	            	System.out.println("Old: "+employee.getRole()+" New: "+role+" "+ createBill+" "+addBook+" "+checkBook+" "+checkLib);
	                ec.searchEmployee(username).setRole(role);
	                ec.searchEmployee(username).setSalary(salary);

	                updateAccessPermissions(employee);
	                ec.updateAll();
		} catch (Exception e) {
	            	Alert errorAlert = new Alert(AlertType.ERROR);
	            	errorAlert.setHeaderText("Error Updating Role");
	            	errorAlert.setContentText(e.getMessage());
	            	errorAlert.showAndWait();
		}
	}

	private void updateAccessPermissions(Employee employee) {
	    if (employee.getRole() == Role.LIBRARIAN) {
	        employee.setCreateBill(Access.YES);
	        employee.setAddBooks(Access.NO);
	        employee.setCheckLibrarian(Access.NO);
	        employee.setCheckBooks(Access.NO);
	    } else if (employee.getRole() == Role.MANAGER) {
	        employee.setCreateBill(Access.NO);
	        employee.setAddBooks(Access.YES);
	        employee.setCheckLibrarian(Access.YES);
	        employee.setCheckBooks(Access.YES);
	    }
	}
}
package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Employee extends User implements Serializable{

	@Serial
	private static final long serialVersionUID = -303001364271211671L;
	
    private double salary;
    private Role role;
    private Access createBill, addBooks, checkLibrarian, checkBooks;
	private final Date dateEmployed;
	private Date dateTerminated;

	private final transient StringProperty status;

	public Employee(String username, String password, String name, String surname, String email, Role role, String phone, double salary,
    		Access createBill, Access addBooks, Access checkLibrarian, Access checkBooks,Date birthDate) {
		super(username, password, name, surname, email, phone);
		this.setRole(role);
		this.setSalary(salary);
		this.setCreateBill(createBill);
		this.setAddBooks(addBooks);
		this.setCheckLibrarian(checkLibrarian);
		this.setCheckBooks(checkBooks);
		dateTerminated = null;
		dateEmployed = new Date();
		this.setBirthDate(birthDate);
		System.out.println(role+" "+createBill+" "+addBooks);
		this.status = new SimpleStringProperty("Active");
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public Access getCreateBill() {
		return createBill;
	}

	public void setCreateBill(Access createBill) {
		this.createBill = createBill;
	}

	public Access getAddBooks() {
		return addBooks;
	}

	public void setAddBooks(Access addBooks) {
		this.addBooks = addBooks;
	}

	public Access getCheckLibrarian() {
		return checkLibrarian;
	}

	public void setCheckLibrarian(Access checkLibrarian) {
		this.checkLibrarian = checkLibrarian;
	}
	
	public Access getCheckBooks() {
		return checkBooks;
	}

	public void setCheckBooks(Access checkBooks) {
		this.checkBooks = checkBooks;
	}
	
	public Date getDateEmployed() {
		return dateEmployed;
	}

	public void setBirthDate(Date birthDate) {
    }
	public String getStatus() {
		return status.get();
	}


    @Override
	public String toString() {
		return "Name=" + getName() + ", lastName=" + getSurname() + ", email=" + getEmail() + ", password=" + getPassword()
				+ ", profession=" + role+", username="+ getUsername() +" "+createBill+" "+addBooks;
	}

	public Date getDateTerminated() {
		return dateTerminated;
	}

	public void setDateTerminated(Date dateTerminated) {
		this.dateTerminated = dateTerminated;
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
	
}
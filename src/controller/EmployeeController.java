package controller;
import javafx.collections.ObservableList;
import main.Main;
import java.io.*;

import model.Employee;

public class EmployeeController {

	public boolean loadUsersFromFile(File dataFile, ObservableList<Employee> employeesAll) {
		boolean isSuccess = false;
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile))) {
			while (true) {
				try {
					Employee employee = (Employee) inputStream.readObject();
					employeesAll.add(employee);
					System.out.printf("Username: %s, Password: %s%n", employee.getUsername(), employee.getPassword());
					isSuccess = true; // Mark success if at least one employee is added
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println("Data loaded from file! " + employeesAll.size());
			System.out.println("#####");
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
			return false; // Return false if an IO exception occurs
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found");
			return false; // Return false if the class is not found
		}

		if (employeesAll.isEmpty()) {
			System.out.println("No employees loaded.");
			return false; // Return false if no employees were loaded
		}

		return isSuccess;
	}


	public boolean create(Employee employee,File dataFile, ObservableList<Employee> employeesAll ) {
		if (employee == null) {
			System.out.println("Employee is null, cannot create.");
			return false;
		}

		if (employeesAll.stream().anyMatch(e -> e.getUsername().equals(employee.getUsername()))) {
			System.out.println("Employee already exists: " + employee.getUsername());
			System.out.println("Employee added to employees list: " + employeesAll.size());
			return false;  // Do not add the employee if already exists
		}

		try (FileOutputStream outputStream = new FileOutputStream(dataFile, true)) {
			ObjectOutputStream writer;
			if (dataFile.length() > 0) {
				writer = new HeaderlessObjectOutputStream(outputStream);
			} else {
				writer = new ObjectOutputStream(outputStream);
			}

			System.out.println("Writing employee: " + employee.getUsername());
			writer.writeObject(employee);
			employeesAll.add(employee);
			System.out.println("Employee added to employees list: " + employeesAll.size());
			return true;
		} catch (NullPointerException ex) {
			System.out.println("NullPointerException: " + ex.getMessage());
			return false;
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
			return false;
		}
	}

	public Employee searchEmployee(String username) {
		for (Employee e : Main.employeesAll)
			if(e.getUsername().equals(username))
				return e;			
		return null;
	}

	public boolean employeeFound(String username) {
		return !Main.employeesAll.isEmpty() && searchEmployee(username) != null;
	}

	 
	public boolean updateAll() {
	        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.DATA_FILE))) {
	            for(Employee e : Main.employeesAll) {
	                outputStream.writeObject(e);
	            }
	            return true;
	        } catch (IOException ex) {
	            return false;
	        }
	    }


}
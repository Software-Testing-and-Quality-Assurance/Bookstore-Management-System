package controller;
import main.Main;
import java.io.*;

import model.Employee;

public class EmployeeController {

	public void loadUsersFromFile() {
	    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.DATA_FILE))) {

			while (true) {
				try {
					Employee employee = (Employee) inputStream.readObject();
					Main.employeesAll.add(employee);
					System.out.printf("Username: %s, Password: %s%n", employee.getUsername(), employee.getPassword());
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println("Data loaded from file! "+ Main.employeesAll.size());
			System.out.println("#####");
	    }  catch (EOFException ignored) {
	    	for (Employee e: Main.employeesAll) {
	        	System.out.println(e.toString()+"check: "+e.getCheckBooks()+" "+e.getDateTerminated());
	        }
        } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("Class not found");
	    }
    }

	public boolean create(Employee employee) {
		if (employee == null) {
			System.out.println("Employee is null, cannot create.");
			return false;
		}

		if (Main.employeesAll.stream().anyMatch(e -> e.getUsername().equals(employee.getUsername()))) {
			System.out.println("Employee already exists: " + employee.getUsername());
			System.out.println("Employee added to employees list: " + Main.employeesAll.size());
			return false;  // Do not add the employee if already exists
		}

		try (FileOutputStream outputStream = new FileOutputStream(Main.DATA_FILE, true)) {
			ObjectOutputStream writer;
			if (Main.DATA_FILE.length() > 0) {
				writer = new HeaderlessObjectOutputStream(outputStream);
			} else {
				writer = new ObjectOutputStream(outputStream);
			}

			System.out.println("Writing employee: " + employee.getUsername());
			writer.writeObject(employee);
			Main.employeesAll.add(employee);
			System.out.println("Employee added to employees list: " + Main.employeesAll.size());
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
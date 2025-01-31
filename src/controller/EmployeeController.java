package controller;
import javafx.collections.ObservableList;
import main.Main;
import java.io.*;

import model.Employee;

public class EmployeeController {

	public boolean loadUsersFromFile(File dataFile, ObservableList<Employee> employeesAll) {
		boolean isSuccess = false;
		try (ObjectInputStreamWrapper inputStream = createObjectInputStreamWrapper(createFileInputStream(dataFile))) {
			while (true) {
				try {
					Employee employee = (Employee) inputStream.readObject();
					if (employee.getDateTerminated()==null){

						employee.setStatus("Active");
					} else {
						employee.setStatus("Fired");
					}
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

	public ObjectInputStreamWrapper createObjectInputStreamWrapper(FileInputStream fileInputStream) throws IOException {
		return new ObjectInputStreamWrapper(fileInputStream);
	}

	// Code refactoring for mocking and doing unit testing
	public FileInputStream createFileInputStream(File dataFile) throws FileNotFoundException {
		return new FileInputStream(dataFile);
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

		try (FileOutputStream outputStream = createFileOutputStream(dataFile)) {

			if (dataFile.length() > 0) {
				ObjectOutputStream writer = createHeaderlessObjectOutputStream(outputStream);
				writer.writeObject(employee);
				writer.close();
			} else {
				ObjectOutputStreamWrapper writer = createObjectOutputStream(outputStream);
				writer.writeObject(employee);
				writer.close();
			}
			employeesAll.add(employee);
			return true;
		} catch (NullPointerException ex) {
			System.out.println("NullPointerException: " + ex.getMessage());
			return false;
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
			return false;
		}
	}

	public FileOutputStream createFileOutputStream(File dataFile) throws FileNotFoundException {
		return new FileOutputStream(dataFile, true);
	}
	// Code refactoring for mocking and doing unit testing
	public ObjectOutputStreamWrapper createObjectOutputStream(FileOutputStream fileOutputStream) throws IOException {
		return new ObjectOutputStreamWrapper(fileOutputStream);
	}
	public ObjectOutputStream createHeaderlessObjectOutputStream(FileOutputStream fileOutputStream) throws IOException {
		return new HeaderlessObjectOutputStream(fileOutputStream);
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
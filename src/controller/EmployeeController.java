package controller;
import main.Main;



import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import controller.HeaderlessObjectOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Employee;

public class EmployeeController {

	public void loadUsersFromFile() {
	    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.DATA_FILE))) {
	        while (true) {
	                Employee employee = (Employee) inputStream.readObject();
	                Main.employeesAll.add(employee);
	                System.out.println("add one");
	        }
	        
	    }  catch (EOFException ignored) {
	    	for (Employee e: Main.employeesAll) {
	        	System.out.println(e.toString()+"check: "+e.getCheckBooks()+" "+e.getDateTerminated());
	        }
	        System.out.println("Data loaded from file! "+ Main.employeesAll.size());
        }catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("Class not found");
	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	
	public boolean create(Employee employee) {
	    try (FileOutputStream outputStream = new FileOutputStream(Main.DATA_FILE, true)) {
	        ObjectOutputStream writer;
	        if (Main.DATA_FILE.length() > 0)
	            writer = new HeaderlessObjectOutputStream(outputStream);
	        else
	            writer = new ObjectOutputStream(outputStream);
	        System.out.println(employee.getDateTerminated());;
	        writer.writeObject(employee);
	       Main. employeesAll.add(employee);
	        return true;
	    } catch (NullPointerException ex) {
	    	System.out.println(ex.getMessage());
	    }
	    catch (IOException ex) {
	    	System.out.println("Cannot create");
	        return false;
	    }
		return false;
	}
	
	public boolean delete(Employee employee) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.DATA_FILE))) {
            for(Employee e : Main.employeesAll) {
                if(!e.equals(employee))
                    outputStream.writeObject(e);
                else {
                	System.out.println("fired");
                }
            }
            Main.employeesAll.remove(employee);
            Main.doneAlert();
            return true;
        } catch (IOException ex) {
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
		if(Main.employeesAll.isEmpty())
			return false;
		if(searchEmployee(username)==null)
				return false;
		return true;
	
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
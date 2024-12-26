package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.*;

public class LibrarianStatController {
	
	public ObservableList<LibStat> filterDate(Date date1, Date date2) {
		EmployeeController ec = new EmployeeController();
		 String username;
	     int nrOfBills;
	     Integer nrOfBooks;
	     double amount;
	     ObservableList<LibStat> filter = FXCollections.observableArrayList();
	     
		for(Map.Entry<String, ArrayList <TotalBill>> all : Main.billsPerLibrarian.entrySet()) {
			username = all.getKey();
			nrOfBills=0;
			nrOfBooks=0;
			amount=0;
			for(TotalBill a : all.getValue()) {
		        Date dateA = a.getOrderDate();

				if(!(date2.before(dateA)) && !(date1.after(dateA))) {

					nrOfBills++;
					nrOfBooks+=a.getTotalNrOfBooks();
					amount+=a.getTotalOrderAmount();
				}
			}
			filter.add(new LibStat(ec.searchEmployee(username).getName(),ec.searchEmployee(username).getSurname(), nrOfBills,nrOfBooks, amount));
		}
		
		for(LibStat a: filter) {
			System.out.println(a.name()+" "+ a.nrOfBills()+" "+a.nrOfBooks());
		}
		return filter;
	}
	
}

package controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.*;
public class LibrarianStatController {

    private final Map<String, ArrayList<TotalBill>> billsPerLibrarian;

	public LibrarianStatController(EmployeeController ec, TotalBillController tbc, Map<String, ArrayList<TotalBill>> billsPerLibrarian) {
		if (ec == null || tbc == null || billsPerLibrarian == null) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}
        this.billsPerLibrarian = billsPerLibrarian;
	}
	public ObservableList<LibStat> filterDate(Date date1, Date date2) {
		EmployeeController ec = new EmployeeController();
		TotalBillController tbc = new TotalBillController();
		 String username;
	     int nrOfBills;
	     int nrOfBooks;
	     double amount;
	     ObservableList<LibStat> filter = FXCollections.observableArrayList();
	     
		for(Map.Entry<String, ArrayList <TotalBill>> all : billsPerLibrarian.entrySet()) {
			username = all.getKey();
			nrOfBills=0;
			nrOfBooks=0;
			amount=0;
			for(TotalBill a : all.getValue()) {
		        Date dateA = a.getOrderDate();

				if(!(date2.before(dateA)) && !(date1.after(dateA))) {

					nrOfBills++;
					nrOfBooks+=tbc.getTotalNrOfBooks(a);
					amount+=a.getTotalOrderAmount();
				}
			}
			filter.add(new LibStat(ec.searchEmployee(username).getName(),ec.searchEmployee(username).getSurname(), nrOfBills,nrOfBooks, amount));
		}
		
		for(LibStat a: filter) {
			System.out.println(a.getName()+" "+ a.getNrOfBills()+" "+a.getNrOfBooks());
		}
		return filter;
	}
	
}

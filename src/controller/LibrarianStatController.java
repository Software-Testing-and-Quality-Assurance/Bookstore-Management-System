package controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
public class LibrarianStatController {
	public ObservableList<LibStat> filterDate(Date date1, Date date2, Map<String, ArrayList<TotalBill>> billsPerLibrarian) {

		EmployeeController ec = new EmployeeController();
		TotalBillController tbc = new TotalBillController();
		String username;
		int nrOfBills;
		int nrOfBooks;
		double amount;
		ObservableList<LibStat> filter = FXCollections.observableArrayList();

		for (Map.Entry<String, ArrayList<TotalBill>> all : billsPerLibrarian.entrySet()) {
			username = all.getKey();
			nrOfBills = 0;
			nrOfBooks = 0;
			amount = 0;
			boolean hasBillsInRange = false;

			for (TotalBill a : all.getValue()) {
				Date dateA = a.getOrderDate();

				if (!(date2.before(dateA)) && !(date1.after(dateA))) {
					System.out.println("Bill on " + dateA + " is in range.");
					nrOfBills++;
					nrOfBooks += tbc.getTotalNrOfBooks(a);
					amount += a.getTotalOrderAmount();
					hasBillsInRange = true;
				}
			}
			if (hasBillsInRange) {
				filter.add(new LibStat(ec.searchEmployee(username).getName(), ec.searchEmployee(username).getSurname(), nrOfBills, nrOfBooks, amount));
			}
		}

		for (LibStat a : filter) {
			System.out.println(a.getName() + " " + a.getNrOfBills() + " " + a.getNrOfBooks());
		}

		return filter;
	}
}

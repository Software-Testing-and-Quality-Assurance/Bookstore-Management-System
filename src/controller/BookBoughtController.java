package controller;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;

public class BookBoughtController {
	public ObservableList<Book> filterDate(Date date1, Date date2, ObservableList<Book> stock) {

		ObservableList<Book> filter = FXCollections.observableArrayList();
	     
		for(Book one : stock) {
			Date fpDate = one.getFirstPurchaseDate();
			System.out.println(fpDate);

			if((date1.before(fpDate)) && (date2.after(fpDate))) {
				System.out.println("Get in 2");
				filter.add(one);}
		}
		
		for(Book a: filter) {
			System.out.println(a.getTitle()+" "+ a.getAuthor()
					+" "+a.getStock()+" "+a.getFirstPurchaseDate()+" "+a.getPurchasePrice()+" "+a.getSellingPrice());
		}
		return filter;
	}
}

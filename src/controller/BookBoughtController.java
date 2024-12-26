package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Book;
import model.LibStat;
import model.TotalBill;

public class BookBoughtController {
	public ObservableList<Book> filterDate(Date date1, Date date2) {

	     ObservableList<Book> filter = FXCollections.observableArrayList();
	     
		for(Book one : Main.bookStock) {
			Date fpDate = one.getFirstPurchaseDate();
			System.out.println(fpDate);

			if(!(date2.before(fpDate)) && !(date1.after(fpDate))) {
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

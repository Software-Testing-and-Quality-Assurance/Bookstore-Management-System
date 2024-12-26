package controller;

import java.util.ArrayList;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import model.Access;
import model.Book;
import model.TotalBill;

public class LibrarianController {
	BookController bc = new BookController();
    TotalBillController tbc = new TotalBillController();
	public boolean checkOutBook(String ISBN, Integer quantity, TotalBill tb) {
		
        Book book = bc.searchBook(ISBN);
        try{
        	if (book.getStock()>=quantity) {
        		book.setStock(book.getStock() - quantity);
        		bc.updateAll();
        		tb.addBook(book, quantity);
        		return true;
        	}else {
        	Alert al = new Alert(AlertType.ERROR);
        	al.setHeaderText("Not enough books!");
        	al.setContentText("There aren't enough books for this bill!");
        	al.showAndWait();}
        	
        }catch (NullPointerException ex) {
        	Alert al = new Alert(AlertType.ERROR);
        	al.setHeaderText("No book found!");
        	al.setContentText("This isbn doesn't belong to any existing book!");
        	al.showAndWait();
        	
        }
        return false;
    }
	
	public boolean checkOutFinal(TotalBill tb) {
		try {
		System.out.println(tb.getBooks().size());
    	Main.billId++;
        tbc.create(tb);
        tbc.printTheBill(tb);
        return true;}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
    }


}

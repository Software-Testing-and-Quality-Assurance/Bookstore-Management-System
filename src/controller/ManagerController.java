package controller;

import java.util.Date;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import model.Access;
import model.Author;
import model.Book;

public class ManagerController {
	BookController bc = new BookController();
	public boolean addBooks(String isbn, String title, String supplier, String category, double sellingPrice, double originalPrice, String authorName, String authorSurname, int quantity) {
    	
        if (!addExist(isbn,quantity)) {
            Book newBook = new Book(isbn, title, supplier, category, sellingPrice, originalPrice, new Author(authorName, authorSurname), quantity);
            Date bought = new Date();
            newBook.getBoughtPerDate().put(bought, quantity);
            return bc.create(newBook);
        } else {
            return false;
        }
    	
    }
	
    public boolean addExist(String isbn, int quantity) {
    	Book existing = bc.searchBook(isbn);
    	if (existing==null)
    		return false;
        existing.restock(quantity);
        existing.getBoughtPerDate().put(new Date(), quantity);
        bc.updateAll();
        System.out.println("Updated stock for existing book: " + existing.getTitle() + " - New stock: " + existing.getStock());
        return true;
    }
}

package controller;
import main.Main;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Access;
import model.Author;
import model.Book;
import model.Employee;

public class BookController {
	
	public void loadBooksFromFile() {
	    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.BOOK_FILE))) {
	        while (true) {
	           
	                Book book = (Book) (inputStream.readObject());
	                Main.bookStock.add(book);
	                System.out.println("add one book");
	        }
	        
	    }  catch (EOFException ignored) {
	    	for (Book b: Main.bookStock) {
	        	System.out.println(b.getIsbn()+" Book Titled " + b.getTitle() + " by " + b.getAuthor()
	    				+ " has " + b.getStock() + " copies "+" category:"+b.getCategory()+" "+b.getFirstPurchaseDate());
	        }
	        System.out.println("books loaded from file! "+ Main.bookStock.size());
        }catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("Class not found book");
	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	
	
	public boolean create(Book book) {
	    try (FileOutputStream outputStream = new FileOutputStream(Main.BOOK_FILE, true)) {
	        ObjectOutputStream writer;
	        if (Main.BOOK_FILE.length() > 0)
	            writer = new HeaderlessObjectOutputStream(outputStream);
	        else
	            writer = new ObjectOutputStream(outputStream);

	        writer.writeObject(book);
	       Main. bookStock.add(book);
	        return true;
	    } catch (NullPointerException ex) {
	    	System.out.println(ex.getMessage());
	    }
	    catch (IOException ex) {
	    	System.out.println("Cannot create book");
	        return false;
	    }
		return false;
	}
	
	public boolean delete(Book book) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.BOOK_FILE))) {
            Main.bookStock.remove(book);

            for(Book b : Main.bookStock) {

                outputStream.writeObject(b);
            }
            Alert successA = new Alert(AlertType.INFORMATION);
			successA.setHeaderText("Done");
			successA.showAndWait();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
	
	public Book searchBook(String isbn) {
		for (Book e : Main.bookStock)
			if(e.getIsbn().equals(isbn))
				return e;			
		return null;
	}
	
	public boolean bookFound(String isbn) {
		if(Main.bookStock.isEmpty())
			return false;
		if(searchBook(isbn)==null)
				return false;
		return true;
	
	}
	
	public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.BOOK_FILE))) {
            for(Book b : Main.bookStock) {
                outputStream.writeObject(b);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
	
}

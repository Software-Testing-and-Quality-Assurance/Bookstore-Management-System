package controller;
import main.Main;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Book;

public class BookController {

	public void loadBooksFromFile() {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Main.BOOK_FILE))) {
			while (true) {
				try {
					Book book = (Book) inputStream.readObject();
					Main.bookStock.add(book);
					System.out.println(book.getIsbn() + " Book Titled " + book.getTitle() + " by " + book.getAuthor()
							+ " has " + book.getStock() + " copies " + " category: " + book.getCategory() + " " + book.getFirstPurchaseDate());
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println("Books loaded from file! " + Main.bookStock.size());
			System.out.println("#####");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found book");
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

	public Book searchBook(String isbn) {
		for (Book e : Main.bookStock)
			if(e.getIsbn().equals(isbn))
				return e;			
		return null;
	}

	public boolean bookFound(String isbn) {
		return !Main.bookStock.isEmpty() && searchBook(isbn) != null;
	}


	public void updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.BOOK_FILE))) {
            for(Book b : Main.bookStock) {
                outputStream.writeObject(b);
            }
		} catch (IOException ex) {
			//log
		}
    }
	
}

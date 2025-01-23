package controller;
import javafx.collections.ObservableList;
import main.Main;

import java.io.*;

import model.Book;

public class BookController {

	public boolean loadBooksFromFile(File book_file, ObservableList<Book> bookStock) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(book_file))) {
			while (true) {
				try {
					Book book = (Book) inputStream.readObject();
					bookStock.add(book);
					System.out.println(book.getIsbn() + " Book Titled " + book.getTitle() + " by " + book.getAuthor()
							+ " has " + book.getStock() + " copies " + " category: " + book.getCategory() + " " + book.getFirstPurchaseDate());
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println("Books loaded from file! " + bookStock.size());
			System.out.println("#####");
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found book");
		}
		return false;
    }

	public boolean create(Book book, File book_file, ObservableList<Book> bookStock) {
	    try (FileOutputStream outputStream = new FileOutputStream(book_file, true)) {
	        ObjectOutputStream writer;
	        if (book_file.length() > 0)
	            writer = new HeaderlessObjectOutputStream(outputStream);
	        else
	            writer = new ObjectOutputStream(outputStream);
	        writer.writeObject(book);
	        bookStock.add(book);
	        return true;
	    } catch (NullPointerException ex) {
	    	System.out.println(ex.getMessage());
	    }
	    catch (IOException ex) {
	    	System.out.println("Cannot create book");
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

	public boolean isOutOfStock(String isbn) {
		return (searchBook(isbn).getStock()==0);
	}

	public void restock(int newStock,String isbn) {
		if(isOutOfStock(isbn)) {
			this.searchBook(isbn).setStock(newStock);
			return;
		}
		this.searchBook(isbn).setStock(newStock+searchBook(isbn).getStock());
	}

	public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.BOOK_FILE))) {
            for(Book b : Main.bookStock) {
                outputStream.writeObject(b);
            }
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
    }
	
}

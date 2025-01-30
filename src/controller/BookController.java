package controller;
import javafx.collections.ObservableList;
import main.Main;

import java.io.*;

import model.Book;

public class BookController {

	public boolean loadBooksFromFile(File book_file, ObservableList<Book> bookStock) {
		try (ObjectInputStreamWrapper inputStream = createObjectInputStreamWrapper(createFileInputStream(book_file))) {
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
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found book");
		}
		return false;
	}

	// Code refactoring for mocking and doing unit testing
	public ObjectInputStreamWrapper createObjectInputStreamWrapper(FileInputStream fileInputStream) throws IOException {
		return new ObjectInputStreamWrapper(fileInputStream);
	}

	// Code refactoring for mocking and doing unit testing
	public FileInputStream createFileInputStream(File book_file) throws FileNotFoundException {
		return new FileInputStream(book_file);
	}

	public boolean create(Book book, File book_file, ObservableList<Book> bookStock) {
		try (FileOutputStream outputStream = createFileOutputStream(book_file)) {
			if (book_file.length() > 0) {
				ObjectOutputStream writer = createHeaderlessObjectOutputStream(outputStream);
				writer.writeObject(book);
				writer.close();
            }
			else {
				ObjectOutputStreamWrapper writer = createObjectOutputStream(outputStream);
				writer.writeObject(book);
				writer.close();
            }
            bookStock.add(book);
            return true;
        } catch (NullPointerException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Cannot create book");
		}
		return false;
	}

	// Code refactoring for mocking and doing unit testing
	public FileOutputStream createFileOutputStream(File book_file) throws FileNotFoundException {
		return new FileOutputStream(book_file, true);
	}
	// Code refactoring for mocking and doing unit testing
	public ObjectOutputStreamWrapper createObjectOutputStream(FileOutputStream fileOutputStream) throws IOException {
		return new ObjectOutputStreamWrapper(fileOutputStream);
	}

	// Code refactoring for mocking and doing unit testing
	public ObjectOutputStream createHeaderlessObjectOutputStream(FileOutputStream fileOutputStream) throws IOException {
		return new HeaderlessObjectOutputStream(fileOutputStream);
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

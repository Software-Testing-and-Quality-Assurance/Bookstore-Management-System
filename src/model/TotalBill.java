package model;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import controller.BookController;

public class TotalBill implements Serializable{

	private static final long serialVersionUID = -5906827024116395864L;
		private String librarianUser;
	    private Date orderDate;
	    private transient Map<Book,Integer> soldBooks = new HashMap<Book, Integer>();

	    public TotalBill(String librarianUser) {
	        this.librarianUser = librarianUser;
	        this.orderDate = new Date();
	        soldBooks = new HashMap<Book, Integer> ();
	    }

	    public void addBook(Book book, Integer quantity) {
	            soldBooks.put(book, quantity);   
	    }


	    public String getLibrarianUser() {
	        return librarianUser;
	    }

	    public Date getOrderDate() {
	        return orderDate;
	    }

	    public int getTotalNrOfBooks() {
	    	int sum=0;
	    	for(Map.Entry<Book,Integer> book: soldBooks.entrySet()) {
	    		sum+=book.getValue();
	    	}
	    	return sum;
	    }
	    
	    public double getTotalOrderAmount() {
	        double totalAmount = 0;
	        for (Map.Entry<Book,Integer> m : soldBooks.entrySet()) {
	            totalAmount += ((Book) m.getKey()).getSellingPrice()*(Integer)m.getValue();
	        }
	        return totalAmount;
	    }
	    
	    public double getTotalPurchaseAmount() {
	        double totalAmount = 0;
	        for (Map.Entry<Book,Integer> m : soldBooks.entrySet()) {
	            totalAmount += ((Book) m.getKey()).getPurchasePrice()*(Integer)m.getValue();
	        }
	        return totalAmount;
	    }
	    
	    
	    @Override
	    public String toString(){
	    	return librarianUser+" "+getTotalOrderAmount();
	    }

		public Map<Book,Integer> getBooks() {
			return soldBooks;
		}

		public void setBooks(Map<Book,Integer> books) {
			this.soldBooks = books;
		}
		
		@Serial
	    private void writeObject(ObjectOutputStream outputStream) throws IOException {
	        outputStream.defaultWriteObject();
	        outputStream.writeInt(soldBooks.size());
	        for(Map.Entry<Book, Integer> entry: soldBooks.entrySet()) {
	        	outputStream.writeObject((Book)entry.getKey());
	        	outputStream.writeInt((int)entry.getValue());
	        }

	    }

	    @Serial
	    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
	        inputStream.defaultReadObject();
	        int size = inputStream.readInt();
	        Map<Book, Integer> bill = new HashMap<>();
	        for(int i=0; i<size;i++) {
	        	Book book = (Book) inputStream.readObject();
	        	Integer quant = inputStream.readInt();
	        	bill.put(book,quant);
	        	
	        }
	        this.soldBooks = bill;
		
	    }
		
	}	

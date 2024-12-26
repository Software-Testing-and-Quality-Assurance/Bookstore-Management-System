package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.lang.instrument.IllegalClassFormatException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Book implements Serializable{
	private static final long serialVersionUID = 4908353983116607163L;
	
	private String isbn,title, supplier;
	private String category;
	private double purchasePrice, sellingPrice;
	private Author author;
	private int stock;
	private Date firstPurchaseDate;
	private Map<Date, Integer> boughtPerDate = new HashMap<>();
	
	public Book(String isbn, String title, String supplier, String category, double sellingPrice, double purchasePrice, Author author, int stock) {
		if(isbn.isEmpty() || title.isEmpty() || category.toString().isBlank() || author.toString().isBlank())
			throw new IllegalArgumentException("These fields should not be empty.");
		this.setIsbn(isbn);
		this.setTitle(title);
		this.setSupplier(supplier);
		this.setCategory(category);
		this.setSellingPrice(sellingPrice);
		this.setPurchasePrice(purchasePrice);
		this.setAuthor(author);
		this.setStock(stock);
		this.firstPurchaseDate = new Date();
	}
	
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}




	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Book))
			throw new IllegalArgumentException("Not a book");

	    return this.isbn.equals(((Book) o).getIsbn());
			
		
	}
	
	public boolean isOutOfStock() {
			return(stock==0?true:false);
	}
		
	public void restock(int newStock) {
		if(isOutOfStock()) {
			this.stock = newStock;
			return;
		}
		this.stock+=newStock;
		return;
		}
	
	public Date getFirstPurchaseDate() {
		return firstPurchaseDate;
	}


	public Map<Date, Integer> getBoughtPerDate() {
		return boughtPerDate;
	}


	public void setBoughtPerDate(Map<Date, Integer> boughtPerDate) {
		this.boughtPerDate = boughtPerDate;
	}


	

}

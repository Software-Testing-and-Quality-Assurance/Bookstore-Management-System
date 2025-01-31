package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Book implements Serializable{
	@Serial
	private static final long serialVersionUID = 4908353983116607163L;
	
	private String isbn;
    private String title;
    private String category;
	private double purchasePrice, sellingPrice;
	private Author author;
	private int stock;
	private final Date firstPurchaseDate;
	private final Map<Date, Integer> boughtPerDate = new HashMap<>();
	private String supplier;

	public Book(String isbn, String title, String supplier, String category, double sellingPrice, double purchasePrice, Author author, int stock) {
		if(isbn.isEmpty() || title.isEmpty() || category.isBlank() || author.toString().isBlank())
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

	public Book(String isbn, String title, String supplier, String category, double sellingPrice, double purchasePrice, Author author, int stock, Date firstPurchaseDate) {
		this.setIsbn(isbn);
		this.setTitle(title);
		this.setSupplier(supplier);
		this.setCategory(category);
		this.setSellingPrice(sellingPrice);
		this.setPurchasePrice(purchasePrice);
		this.setAuthor(author);
		this.setStock(stock);
		this.firstPurchaseDate = firstPurchaseDate;
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
	public String getSupplier() {
		return supplier;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Book))
			throw new IllegalArgumentException("Not a book");

	    return this.isbn.equals(((Book) o).getIsbn());
			
		
	}
	public Date getFirstPurchaseDate() {
		return firstPurchaseDate;
	}
	public Map<Date, Integer> getBoughtPerDate() {
		return boughtPerDate;
	}

}

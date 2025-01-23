package model;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TotalBill implements Serializable{
	@Serial
	private static final long serialVersionUID = -5906827024116395864L;
	private final String librarianUser;
	private final Date orderDate;
	private String billContent;

	public Map<Book, Integer> getSoldBooks() {
		return soldBooks;
	}

	private transient Map<Book,Integer> soldBooks;

	public TotalBill(String librarianUser) {
		this.librarianUser = librarianUser;
		this.orderDate = new Date();
		soldBooks = new HashMap<Book, Integer> ();
	}
	public TotalBill(String librarianUser,Date orderDate) {
		this.librarianUser = librarianUser;
		this.orderDate = orderDate;
		soldBooks = new HashMap<Book, Integer> ();
	}
	public String getLibrarianUser() {
		return librarianUser;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setBillContent(String s){ this.billContent=s; }

	public String getBillContent(){ return this.billContent; }

	public double getTotalOrderAmount() {
		double totalAmount = 0;
		for (Map.Entry<Book,Integer> m : soldBooks.entrySet()) {
			totalAmount += (m.getKey()).getSellingPrice()*m.getValue();
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
}

package model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	@Serial
	private void writeObject(ObjectOutputStream outputStream) throws IOException {
		outputStream.defaultWriteObject();
		outputStream.writeInt(soldBooks.size());
		for(Map.Entry<Book, Integer> entry: soldBooks.entrySet()) {
			outputStream.writeObject(entry.getKey());
			outputStream.writeInt(entry.getValue());
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

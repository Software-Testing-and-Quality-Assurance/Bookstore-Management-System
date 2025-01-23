package controller;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.Main;
import model.Book;
import model.TotalBill;

public class TotalBillController {
	public void loadBillsFromFile() {
		Main.billsPerLibrarian = new HashMap<>();
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(Main.ALL_BILLS_FILE))) {
			while (true) {
				try {
					TotalBill tb = (TotalBill) in.readObject();
					Main.allBills.add(tb);
					Main.billsPerLibrarian.computeIfAbsent(tb.getLibrarianUser(), key -> new ArrayList<>()).add(tb);
					System.out.println("add one");
				} catch (EOFException e) {
					break;
				}
			}
			for (TotalBill t : Main.allBills) {
				System.out.println(t.getLibrarianUser() + t.getOrderDate());
				for (Map.Entry<Book, Integer> me : t.getBooks().entrySet()) {
					System.out.println(me.getKey().getTitle() + " " + me.getValue());
				}
			}

			System.out.println("***");
			for (Map.Entry<String, ArrayList<TotalBill>> me : Main.billsPerLibrarian.entrySet()) {
				System.out.println(me.getKey() + " " + me.getValue().size());
			}

			System.out.println("Data loaded from file! " + Main.allBills.size());
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		} catch (Exception e) {
			//log
		}
	}

	public boolean addBook(TotalBill bill, Book book, Integer quantity) throws NullPointerException {
		if  (book == null)
			throw new NullPointerException("Book not found");
		else if (bill == null)
			throw new NullPointerException("Empty bill");
		else if (quantity == null)
			throw new NullPointerException("Empty quantity");
		else if (quantity > book.getStock() || quantity<=0)
			return false;
		else
			bill.getBooks().put(book, quantity);
		return true;
	}


	public int getTotalNrOfBooks(TotalBill bill) {
		int sum=0;
		if(bill == null){
			return 0;
		}
		for(Map.Entry<Book,Integer> book: bill.getBooks().entrySet()) {
			sum+=book.getValue();
		}
		return sum;
	}
	public void printTheBill(TotalBill tb) {
		Main.PRINT_BILL_FILE = new File(Main.PRINT_BILL_PATH+Main.billId+".txt");
		String billContent="";
    	try(PrintWriter pw = new PrintWriter(Main.PRINT_BILL_FILE)) {
    		EmployeeController ec = new EmployeeController();
            pw.println("Order by: "+ec.searchEmployee(tb.getLibrarianUser()).getName()+" "+ec.searchEmployee(tb.getLibrarianUser()).getSurname());
            pw.println("Date "+ tb.getOrderDate());
    		pw.println("TotalPrice: "+ tb.getTotalOrderAmount());
			billContent = "Order by: "+ec.searchEmployee(tb.getLibrarianUser()).getName()+" "+ec.searchEmployee(tb.getLibrarianUser()).getSurname() + "\n";
			billContent += "Date "+ tb.getOrderDate()+ "\n";
			billContent += "TotalPrice: "+ tb.getTotalOrderAmount()+ "\n";
            for(Map.Entry<Book,Integer> bi: tb.getBooks().entrySet()) {
               	pw.println(bi.getKey().getTitle()+", copies: "+bi.getValue()+", price: "+(bi.getKey()).getSellingPrice()*bi.getValue());
				billContent += bi.getKey().getTitle()+", copies: "+bi.getValue()+", price: "+(bi.getKey()).getSellingPrice()*bi.getValue()+ "\n";
            }
		} catch (IOException ex) {
        	System.out.println(ex.getMessage());
		}
		tb.setBillContent(billContent);
		System.out.println(billContent);
    }
	
	public void create(TotalBill totalBill) {
		try (FileOutputStream outputStream = new FileOutputStream(Main.ALL_BILLS_FILE, true)) {
			ObjectOutputStream writer;
			if (Main.ALL_BILLS_FILE.length() > 0)
				writer = new HeaderlessObjectOutputStream(outputStream);
			else
				writer = new ObjectOutputStream(outputStream);
			writer.writeObject(totalBill);
			Main.allBills.add(totalBill);
			Main.billsPerLibrarian.computeIfAbsent(totalBill.getLibrarianUser(), add -> new ArrayList<>()).add(totalBill);

		} catch (NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("Cannot create");
		}
	}
}

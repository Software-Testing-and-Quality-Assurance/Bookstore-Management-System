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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.Main;
import model.Book;
import model.Employee;
import model.TotalBill;

public class TotalBillController {
	private BookController bc = new BookController();
	public void loadBillsFromFile() {
    	Main.billsPerLibrarian = new HashMap<>();
    	try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(Main.ALL_BILLS_FILE))) {
    		while (true) {
    				TotalBill tb = (TotalBill) (in.readObject());
	                Main.allbills.add(tb);
	                Main.billsPerLibrarian.computeIfAbsent(tb.getLibrarianUser(), add -> new ArrayList<>()).add(tb);
	                System.out.println("add one");
	               }
    	} catch (EOFException ignored) {
        	for (TotalBill t: Main.allbills) {
		       	System.out.println(t.getLibrarianUser()+t.getOrderDate());
		       	{
		       		for(Map.Entry<Book,Integer> me: t.getBooks().entrySet()) {
				       	System.out.println(me.getKey().getTitle()+" "+me.getValue());
		       		}
		       	}
		       	
		    
	        }
        	 System.out.println("***");
		     for(Map.Entry<String, ArrayList<TotalBill>> me : Main.billsPerLibrarian.entrySet()) {
		    	 System.out.println(me.getKey()+" "+me.getValue().size());
		     }
	        System.out.println("Data loaded from file! "+ Main.allbills.size());

	        } catch (FileNotFoundException e) {
	        System.out.println();
	    } catch (ClassNotFoundException ex1) {
	        System.out.println();
	    } catch (IOException ex) {
	        System.out.println();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
    }
	    

	
public boolean printTheBill(TotalBill tb) {
		File PRINT_BILL_FILE= new File("printBill"+Main.billId+".txt");
    	try(PrintWriter pw = new PrintWriter(PRINT_BILL_FILE)) {
    		EmployeeController ec = new EmployeeController();
            pw.println("Order by: "+ec.searchEmployee(tb.getLibrarianUser()).getName()+" "+ec.searchEmployee(tb.getLibrarianUser()).getSurname());
            pw.println("Date "+ tb.getOrderDate());
    		pw.println("TotalPrice: "+ tb.getTotalOrderAmount());
            for(Map.Entry<Book,Integer> bi: tb.getBooks().entrySet()) {
               	pw.println(bi.getKey().getTitle()+", copies: "+bi.getValue()+", price: "+(bi.getKey()).getSellingPrice()*(Integer)bi.getValue());
            }
            return true;
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        	return false;
        }
    }
	
	public boolean create(TotalBill totalBill) {
		try (FileOutputStream outputStream = new FileOutputStream(Main.ALL_BILLS_FILE, true)) {
			ObjectOutputStream writer;
			if (Main.ALL_BILLS_FILE.length() > 0)
				writer = new HeaderlessObjectOutputStream(outputStream);
			else
				writer = new ObjectOutputStream(outputStream);
			writer.writeObject(totalBill);
			Main. allbills.add(totalBill);
			Main.billsPerLibrarian.computeIfAbsent(totalBill.getLibrarianUser(), add -> new ArrayList<>()).add(totalBill);

			return true;
		} catch (NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("Cannot create");
			return false;
		}
		return false;
	}
}

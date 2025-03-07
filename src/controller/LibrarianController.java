package controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import model.Book;
import model.TotalBill;

public class LibrarianController {
	BookController bc = new BookController();
    TotalBillController tbc = new TotalBillController();
	public boolean checkOutBook(String ISBN, Integer quantity, TotalBill tb) {
		
        Book book = bc.searchBook(ISBN);
        try{
        	if (tbc.addBook(tb, book, quantity)) {
        		book.setStock(book.getStock() - quantity);
        		bc.updateAll();
        		return true;
        	}else {
        	Alert al = new Alert(AlertType.ERROR);
        	al.setHeaderText("Not enough books!");
			al.setContentText("There aren't enough books for this bill!");
			al.getDialogPane().setId("custom-alert-pane");
        	al.showAndWait();}
        	return false;
        }catch (NullPointerException ex) {
        	Alert al = new Alert(AlertType.ERROR);
        	al.setHeaderText("Try again");
        	al.setContentText(ex.getMessage());
			al.getDialogPane().setId("custom-alert-pane");
        	al.showAndWait();
			return false;
        }
    }
	
	public boolean checkOutFinal(TotalBill tb) {
		try {
		System.out.println(tb.getBooks().size());
    	Main.billId++;
        tbc.create(tb);
        tbc.printTheBill(tb);
        return true;}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
    }


}

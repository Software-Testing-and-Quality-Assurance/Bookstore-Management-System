package controller;
import java.util.Date;
import model.Author;
import model.Book;

public class ManagerController {
    private final BookController bc;

    public ManagerController() {
        this.bc = new BookController();
    }
    public ManagerController(BookController bookController) {
        this.bc = bookController;
         }
	public boolean addBooks(String isbn, String title, String supplier, String category, double sellingPrice, double originalPrice, String authorName, String authorSurname, int quantity) {
        if (isbn == null || isbn.isEmpty() || title == null || title.isEmpty() || supplier == null || supplier.isEmpty() || category == null || category.isEmpty() ||
                authorName == null || authorName.isEmpty() || authorSurname == null || authorSurname.isEmpty() || sellingPrice < 0 || originalPrice < 0 || quantity <= 0) {
            return false;
        }
        if (!addExist(isbn,quantity)) {
            Book newBook = new Book(isbn, title, supplier, category, sellingPrice, originalPrice, new Author(authorName, authorSurname), quantity);
            Date bought = new Date();
            newBook.getBoughtPerDate().put(bought, quantity);
            return bc.create(newBook);
        } else {
            return false;
        }

    }

    public boolean addExist(String isbn, int quantity) {
    	Book existing = bc.searchBook(isbn);
    	if (existing==null)
    		return false;
        bc.restock(quantity,isbn);
        existing.getBoughtPerDate().put(new Date(), quantity);
        bc.updateAll();
        System.out.println("Updated stock for existing book: " + existing.getTitle() + " - New stock: " + existing.getStock());
        return true;
    }
}

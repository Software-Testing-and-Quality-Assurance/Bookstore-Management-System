package model;

import javafx.beans.property.*;

public class LibStat {

    private StringProperty name;
    private StringProperty surname;
    private IntegerProperty nrOfBills;
    private IntegerProperty nrOfBooks;
    private DoubleProperty amount;
  
        

	public LibStat(String name, String surname, int nrOfBills, Integer nrOfBooks,double amount) {
        
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.nrOfBills = new SimpleIntegerProperty(nrOfBills);
        this.nrOfBooks = new SimpleIntegerProperty(nrOfBooks);
        this.amount = new SimpleDoubleProperty(amount);
    }
	
	
	public StringProperty nameProperty() {
	    return name;
	}

	public StringProperty surnameProperty() {
	    return surname;
	}

	public IntegerProperty nrOfBillsProperty() {
	    return nrOfBills;
	}

	public IntegerProperty nrOfBooksProperty() {
	    return nrOfBooks;
	}

	public DoubleProperty amountProperty() {
	    return amount;
	}
	
	public String name() {
        return name.get();
    }

    public String surname() {
        return surname.get();
    }

    public int nrOfBills() {
        return nrOfBills.get();
    }

    public int nrOfBooks() {
        return nrOfBooks.get();
    }

    public double amount() {
        return amount.get();
    }
}

package model;

import javafx.beans.property.*;

public class LibStat {
    private final StringProperty name;
    private final StringProperty surname;
    private final IntegerProperty nrOfBills;
    private final IntegerProperty nrOfBooks;
    private final DoubleProperty amount;

    public LibStat(String name, String surname, int nrOfBills, int nrOfBooks, double amount) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.nrOfBills = new SimpleIntegerProperty(nrOfBills);
        this.nrOfBooks = new SimpleIntegerProperty(nrOfBooks);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public int getNrOfBills() {
        return nrOfBills.get();
    }

    public int getNrOfBooks() {
        return nrOfBooks.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
}

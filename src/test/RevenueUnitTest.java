/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * Unit  Testing * * * * * * * * * *
 * * * * * * Test done by: Klaudia Tamburi * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevenueUnitTest {

    /*
    * Purpose of test is to check if the function that calculates
    * the total income for a specific duration and the total cost
    * of the library works correctly
    * */

    private AdminController ac;
    private ObservableList<Employee> employeesAll;
    private Map<String, ArrayList<TotalBill>> billsPerLibrarian;
    private ObservableList<Book> bookStock;

    @BeforeEach
    void setUp() {
        ac = new AdminController();
        employeesAll = FXCollections.observableArrayList();
        billsPerLibrarian = new HashMap<>();
        bookStock = FXCollections.observableArrayList();
    }

    @Test
    @DisplayName("Unit Testing backend function calculating revenue")
    void revenue() {
        // Initializing some data to test the revenue function
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 5);
        Date bought = calendar.getTime();

        Book b = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 15.0, 10.0, new Author("M.L.", "RIO"), 10, bought);
        Book b1 = new Book("123456789", "And then there were none", "Klaudia", "Mystery", 10.0, 5.0, new Author("Agatha", "Christie"), 10, bought);
        b.getBoughtPerDate().put(bought, 10);
        b1.getBoughtPerDate().put(bought, 10);

        bookStock.add(b);
        bookStock.add(b1);

        calendar.set(2003, Calendar.OCTOBER, 18);
        Date birthDate = calendar.getTime();
        calendar.set(2023, Calendar.JANUARY, 18);
        Date employed = calendar.getTime();
        Employee e = new Employee("Klaudia1", "12345678", "Klaudia", "Tamburi", "kt@gmail.com", Role.LIBRARIAN, "355695214014", 100.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate, employed);
        Employee e1 = new Employee("Klaudia2", "12345678", "Klaudia", "Tamburi", "klau@gmail.com", Role.LIBRARIAN, "355695214012", 100.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate, employed);

        employeesAll.add(e);
        employeesAll.add(e1);

        calendar.set(2025, Calendar.JANUARY, 18);
        Date soldDate = calendar.getTime();
        TotalBill bill = new TotalBill(e.getUsername(),soldDate);

        calendar.set(2024, Calendar.DECEMBER, 10);
        soldDate = calendar.getTime();
        TotalBill bill1 = new TotalBill(e1.getUsername(),soldDate);

        bill.getBooks().put(b, 5);
        bill1.getBooks().put(b1, 6);

        billsPerLibrarian.put(bill.getLibrarianUser(), new ArrayList<>());
        billsPerLibrarian.put(bill1.getLibrarianUser(), new ArrayList<>());

        billsPerLibrarian.get(bill.getLibrarianUser()).add(bill);
        billsPerLibrarian.get(bill1.getLibrarianUser()).add(bill1);

        LocalDate firstDate = LocalDate.of(2024, 12, 1);
        LocalDate secondDate = LocalDate.of(2025, 1, 22);

        Instant instant1 = Instant.from(firstDate.atStartOfDay(ZoneId.systemDefault()));
        Date date1 = Date.from(instant1);
        Instant instant2 = Instant.from(secondDate.atStartOfDay(ZoneId.systemDefault()));
        Date date2 = Date.from(instant2);

        // Pass data to function
        double[] revenue = ac.revenue(date1, date2, firstDate, secondDate, employeesAll,  billsPerLibrarian, bookStock);

        // total income
        assertEquals(135, revenue[0]);
        // total cost
        assertEquals(350, revenue[1]);
    }
}
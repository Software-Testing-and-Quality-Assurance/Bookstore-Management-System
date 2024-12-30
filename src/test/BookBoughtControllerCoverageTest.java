/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * Coverage  Testing * * * * * * * * *
 * * * * * * Test done by: Klaudia Tamburi * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import controller.BookBoughtController;
import javafx.collections.ObservableList;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;

import static org.junit.jupiter.api.Assertions.*;

class BookBoughtControllerCoverageTest {
    /*
    * Test Covarage Test Cases
    * Statament Coverage: Execution of each code line at least once (1 test case - 95% coverage)
    * Branch Coverage: Execution of each code branch at least once (1 test case - 95% coverage)
    * Condition Coverage: Execution of each possible condition combination at least once (4 dates test cases - 100% coverage)
    * MC/DC: 100% code execution with the least amount of input tests taking into consideration all conditions and branches
    * (4 dates test cases or 3 books in stock 2 dates test cases - 100% coverage)
     */

    private BookBoughtController controller;
    private ObservableList<Book> bookStock;

    @BeforeEach
    void setUp() {
        controller = new BookBoughtController();
        bookStock = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();
        cal.set(2019, Calendar.OCTOBER, 2);
        Date date = cal.getTime();
        bookStock.add(new Book("ISBN1", "Title1", "Supplier1", "Category1", 70.0, 50.0, new Author("Author1", "1"), 10, date));
    }

    @Test
    @DisplayName("Statement Coverage Testing")
    void test1() {
        //We only care to make sure each line is executed at least once; only 1 test case needed; doesn't achieve 100% coverage
        Calendar cal = Calendar.getInstance();
        cal.set(2019, Calendar.OCTOBER, 1);
        Date date1 = cal.getTime();
        cal.set(2019, Calendar.OCTOBER, 3);
        Date date2 = cal.getTime();

        ObservableList<Book> filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertEquals(1, filteredBooks.size());
        assertTrue(filteredBooks.stream().anyMatch(book -> book.getTitle().equals("Title1")));
    }

    @Test
    @DisplayName("Branch Coverage Testing")
    void test2() {
        //We care for each code branch to be executed; case covers when if condition is entered and not entered; again we don't achieve 100% coverage
        Calendar cal = Calendar.getInstance();

        // Branch test: Enter if condition; date range one day before and after oct 2nd, 19 
        cal.set(2019, Calendar.OCTOBER, 1);
        Date date1 = cal.getTime();
        cal.set(2019, Calendar.OCTOBER, 3);
        Date date2 = cal.getTime();

        ObservableList<Book> filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertEquals(1, filteredBooks.size());
        assertTrue(filteredBooks.stream().anyMatch(book -> book.getTitle().equals("Title1")));

        // Another branch test: Don't enter if condition; date range before oct 2nd, 19
        cal.set(2019, Calendar.JANUARY, 1);
        date1 = cal.getTime();
        cal.set(2019, Calendar.JANUARY, 31);
        date2 = cal.getTime();

        filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertTrue(filteredBooks.isEmpty());
    }

    @Test
    @DisplayName("Condition Coverage Testing")
    void test3() {
        //We care for each condition state to be executed; we try all possible combinations of the conditions; achieve 100% coverage
        //We care for each code branch to be executed; case covers when if condition is entered and not entered; again we don't achieve 100% coverage
        Calendar cal = Calendar.getInstance();

        // Condition test: Both conditions inside the if statement evaluate T
        cal.set(2018, Calendar.DECEMBER, 31);
        Date date1 = cal.getTime();
        cal.set(2019, Calendar.OCTOBER, 3);
        Date date2 = cal.getTime();

        ObservableList<Book> filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertEquals(1,filteredBooks.size());
        assertTrue(filteredBooks.stream().anyMatch(book -> book.getTitle().equals("Title1")));

        // Condition test: Both conditions inside the if statement evaluate F
        cal.set(2019, Calendar.OCTOBER, 3);
        date1 = cal.getTime();
        cal.set(2019, Calendar.AUGUST, 2);
        date2 = cal.getTime();

        filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertTrue(filteredBooks.isEmpty());

        // Condition test: First condition evaluates T and second F
        cal.set(2019, Calendar.SEPTEMBER, 1);
        date1 = cal.getTime();
        cal.set(2019, Calendar.SEPTEMBER, 2);
        date2 = cal.getTime();

        filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertTrue(filteredBooks.isEmpty());

        // Condition test: First condition evaluates F and second T
        cal.set(2019, Calendar.NOVEMBER, 1);
        date1 = cal.getTime();
        cal.set(2019, Calendar.NOVEMBER, 2);
        date2 = cal.getTime();

        filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertTrue(filteredBooks.isEmpty());
    }

    @Test
    @DisplayName("MC/DC Coverage Testing")
    void test4() {
        // MC/DC Test: Each condition independently and combined; We care to achieve 100% coverage with the least amount of test cases
        // the least amount of test cases would be the stock to have 3 books with dates that evaluate each the if condition to T&&T, T&&F, F&&F, F&&T
        // where 3 for the given d1<d2 evaluate the first T&&T, the second F&&T, the third T&&F
        // for the condition to evaluate F&&F, d1>d2
        // for 100% code

        // Set up the other books needed in stock
        Calendar cal = Calendar.getInstance();
        cal.set(2019, Calendar.JANUARY, 2);
        Date date = cal.getTime();
        bookStock.add(new Book("ISBN2", "Title2", "Supplier2", "Category2", 70.0, 50.0, new Author("Author2", "2"), 10, date));
        cal.set(2020, Calendar.JANUARY, 1);
        date = cal.getTime();
        bookStock.add(new Book("ISBN3", "Title3", "Supplier3", "Category3", 70.0, 50.0, new Author("Author3", "3"), 10, date));

        // The first three cases d1<d2, T&&T, F&&T, T&&F
        cal.set(2019, Calendar.OCTOBER, 1);
        Date date1 = cal.getTime();
        cal.set(2019, Calendar.OCTOBER, 3); 
        Date date2 = cal.getTime();
        ObservableList<Book> filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertEquals(1, filteredBooks.size());
        assertTrue(filteredBooks.stream().anyMatch(book -> book.getTitle().equals("Title1")));

        //  The fourth case d1>d2 F&&F
        cal.set(2020, Calendar.JULY, 1);
        date1 = cal.getTime();
        cal.set(2018, Calendar.JULY, 3); 
        date2 = cal.getTime();
        filteredBooks = controller.filterDate(date1, date2, bookStock);
        assertTrue(filteredBooks.isEmpty());
    }

}
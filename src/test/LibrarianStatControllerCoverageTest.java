package test;
import main.Main;
import controller.LibrarianStatController;
import javafx.collections.ObservableList;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
class LibrarianStatControllerCoverageTest {
    /*Keit Nika
     *1. Statement Coverage - each statement is covered at least once - 95% coverage achieved
     *2. Branch Coverage - each branch is covered at least once - 100% coverage achieved
     *3. Condition Coverage - each condition is covered at least once - 100% coverage achieved
     *4. MCDC -  100% code execution with the least amount of input tests - 100% coverage achieved
     * */
    private LibrarianStatController lst;
    private Map<String, ArrayList<TotalBill>> billsPerLibrarian;

    @BeforeEach
    void setUp() {
        Main.employeesAll.clear();
        billsPerLibrarian = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.MAY, 25);
        Date birthDate = calendar.getTime();
        Employee e = new Employee("librarianKeit", "12345678kn", "keit", "nika", "keitn@gmail.com", Role.LIBRARIAN, "355695214014", 1000.0, Access.YES, Access.NO, Access.NO, Access.NO, birthDate);
        Main.employeesAll.add(e);
        lst = new LibrarianStatController();
    }

    @Test
    @DisplayName("Statement Coverage")
    void test1() {
        ArrayList<TotalBill> bills = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.set(2024, Calendar.DECEMBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        billsPerLibrarian.put("librarianKeit", bills);
        calendar.set(2024, Calendar.DECEMBER, 1);
        Date startDate = calendar.getTime();
        calendar.set(2024, Calendar.DECEMBER, 31);
        Date endDate = calendar.getTime();
        ObservableList<LibStat> result = lst.filterDate(startDate, endDate,billsPerLibrarian);
        assertEquals(1, result.getFirst().getNrOfBills());
    }

    @Test
    @DisplayName("Branch Coverage Test")
    void test2(){
        ArrayList<TotalBill> bills = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.set(2024, Calendar.OCTOBER, 5);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));

        billsPerLibrarian.put("librarianKeit",bills);

        calendar.set(2024, Calendar.NOVEMBER, 1);
        Date startDate1 = calendar.getTime();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date endDate1 = calendar.getTime();

        ObservableList<LibStat> result = lst.filterDate(startDate1, endDate1,billsPerLibrarian);
        assertTrue(result.isEmpty());

        calendar.set(2024, Calendar.OCTOBER, 1);
        Date startDate = calendar.getTime();
        calendar.set(2024, Calendar.OCTOBER, 31);
        Date endDate = calendar.getTime();
        result = lst.filterDate(startDate, endDate,billsPerLibrarian);
        assertEquals(1, result.getFirst().getNrOfBills());
    }

    @Test
    @DisplayName("Condition Coverage Test")
    void test3() {
        ArrayList<TotalBill> bills = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // In range bill
        calendar.set(2024, Calendar.NOVEMBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        calendar.set(2024, Calendar.NOVEMBER, 2);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        calendar.set(2024, Calendar.NOVEMBER, 29);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));

        // Out of range bills
        calendar.set(2024, Calendar.OCTOBER, 5);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));

        calendar.set(2024, Calendar.DECEMBER, 5);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));

        billsPerLibrarian.put("librarianKeit", bills);
        calendar.set(2024, Calendar.NOVEMBER, 1);
        Date startDate = calendar.getTime();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date endDate = calendar.getTime();
        ObservableList<LibStat> result = lst.filterDate(startDate, endDate, billsPerLibrarian);
        assertEquals(3, result.getFirst().getNrOfBills());

        bills.clear();
        calendar.set(2024, Calendar.OCTOBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        billsPerLibrarian.put("librarianKeit",bills);
        calendar.set(2024, Calendar.OCTOBER, 30);
        Date startDate2 = calendar.getTime();
        calendar.set(2024, Calendar.OCTOBER, 1);
        Date endDate2 = calendar.getTime();
        result = lst.filterDate(startDate2, endDate2, billsPerLibrarian);
        assertTrue(result.isEmpty());
    }
    @Test
    @DisplayName("MCDC")
    void test4(){
        ArrayList<TotalBill> bills = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        //before date range
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        //after date range
        calendar.set(2024, Calendar.NOVEMBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        billsPerLibrarian.put("librarianKeit",bills);
        calendar.set(2024, Calendar.OCTOBER, 1);
        Date startDate1 = calendar.getTime();
        calendar.set(2024, Calendar.OCTOBER, 30);
        Date endDate1 = calendar.getTime();
        ObservableList<LibStat>  result = lst.filterDate(startDate1, endDate1, billsPerLibrarian);
        assertTrue(result.isEmpty());

        bills.clear();
        calendar.set(2024, Calendar.OCTOBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime()));
        billsPerLibrarian.put("librarianKeit",bills);
        calendar.set(2024, Calendar.OCTOBER, 30);
        Date startDate2 = calendar.getTime();
        calendar.set(2024, Calendar.OCTOBER, 1);
        Date endDate2 = calendar.getTime();
        result = lst.filterDate(startDate2, endDate2, billsPerLibrarian);
        assertTrue(result.isEmpty());

        bills.clear();
        calendar.set(2024, Calendar.OCTOBER, 10);
        bills.add(new TotalBill("librarianKeit", calendar.getTime())); //in range
        billsPerLibrarian.put("librarianKeit",bills);
        calendar.set(2024, Calendar.OCTOBER, 1);
        Date startDate = calendar.getTime();
        calendar.set(2024, Calendar.OCTOBER, 30);
        Date endDate = calendar.getTime();
        result = lst.filterDate(startDate, endDate, billsPerLibrarian);
        assertEquals(1,result.getFirst().getNrOfBills());
    }

}

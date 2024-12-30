package test;
import main.Main;
import controller.EmployeeController;
import controller.LibrarianStatController;
import controller.TotalBillController;
import javafx.collections.ObservableList;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianStatControllerStatementCoverageTest {
/*Keit Nika
1.statement coverage => every line of code has been run at least once
test1() achieved 95% statement coverage
2 - branch coverage
3 - condition coverage
4 - MCDC
* */
    private EmployeeController ec;
    private TotalBillController tbc;
    private LibrarianStatController lst;
    private Map<String, ArrayList<TotalBill>> billsPerLibrarian;

    @BeforeEach
    void setUp() {
        ec = mock(EmployeeController.class);
        tbc = mock(TotalBillController.class);
        billsPerLibrarian = new HashMap<>();
        lst = new LibrarianStatController(ec, tbc, billsPerLibrarian);
        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getUsername()).thenReturn("librarianKeit");
        Main.employeesAll.add(mockEmployee);
        when(ec.searchEmployee("librarianKeit")).thenReturn(mockEmployee);
    }

    @Test
    @DisplayName("Statement Coverage")
    void test1() {
        ArrayList<TotalBill> bills = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 10);
        Date inRange = calendar.getTime();
        TotalBill billInRange = new TotalBill("librarianKeit", inRange);
        bills.add(billInRange);
        calendar.set(2024, Calendar.NOVEMBER, 5);
        Date outOfRange = calendar.getTime();
        TotalBill billOutOfRange = new TotalBill("librarianKeit", outOfRange);
        bills.add(billOutOfRange);
        billsPerLibrarian.put("librarianKeit", bills);
        calendar.set(2024, Calendar.DECEMBER, 1);
        Date startDate = calendar.getTime();
        calendar.set(2024, Calendar.DECEMBER, 31);
        Date endDate = calendar.getTime();
        ObservableList<LibStat> result = lst.filterDate(startDate, endDate);
        assertEquals(1, result.size());
    }
    
}

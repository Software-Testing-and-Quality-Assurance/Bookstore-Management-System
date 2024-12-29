package test;

import controller.TotalBillController;
import model.Book;
import model.TotalBill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TotalBillControllerTest {
    Book b,b1,b2,b3;
    TotalBill bill;
    TotalBillController tbc;
    @BeforeEach
    void setUp(){
        b = mock(Book.class);
        b1 = mock(Book.class);
        b2 = mock(Book.class);
        b3 = mock(Book.class);
        bill = mock(TotalBill.class);
        tbc = new TotalBillController();
    }
    @Test
    @DisplayName("Boundary Value Testing 1 - empty map")
    void test1(){
        Map<Book,Integer> m = new HashMap<>();
        when(bill.getSoldBooks()).thenReturn(m);
        int total = tbc.getTotalNrOfBooks(bill);
        assertEquals(0,total);
        Mockito.verify(bill).getSoldBooks();
    }
    @Test
    @DisplayName("Boundary Value Testing 2 - one book")
    void test2() {
        Map<Book,Integer> m = new HashMap<>();
        m.put(b,1);
        when(bill.getSoldBooks()).thenReturn(m);
        int total = tbc.getTotalNrOfBooks(bill);
        assertEquals(1,total);
        Mockito.verify(bill).getSoldBooks();
    }
    @Test
    @DisplayName("Boundary Value Testing 3 - multiple books, one has 0 copies sold")
    void test3() {
        Map<Book,Integer> m = new HashMap<>();
        m.put(b,10);
        m.put(b1,0);
        m.put(b2,2);
        m.put(b3,1);
        when(bill.getSoldBooks()).thenReturn(m);
        int total = tbc.getTotalNrOfBooks(bill);
        assertEquals(13,total);
        Mockito.verify(bill).getSoldBooks();
    }

    @Test
    @DisplayName("Boundary Value Testing 4 - a lot of books sold")
    void test4() {
        Map<Book, Integer> m = new HashMap<>();
        m.put(b, Integer.MAX_VALUE);
        m.put(b1, Integer.MAX_VALUE);
        m.put(b2, Integer.MAX_VALUE);
        Mockito.when(bill.getSoldBooks()).thenReturn(m);
        int total =Integer.MAX_VALUE + Integer.MAX_VALUE + Integer.MAX_VALUE;
        int t = tbc.getTotalNrOfBooks(bill);
        assertEquals(total, t);
        Mockito.verify(bill).getSoldBooks();
    }
    @Test
    @DisplayName("Boundary Value Testing 5 - null input")
    void test5() {
        assertThrows(NullPointerException.class, () -> tbc.getTotalNrOfBooks(null));
    }


}
/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * Boundary Value  Testing * * * * * * * *
 * * * * * * Test done by: Klaudia Tamburi * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

/*
 * JUnit 5 License:
 * This code uses JUnit 5, licensed under the Eclipse Public License 2.0.
 * See: http://www.eclipse.org/legal/epl-2.0/
 */

/*
 * Mockito License:
 * This code uses Mockito, licensed under the MIT License.
 * See: https://opensource.org/licenses/MIT
 */

package test;

import controller.TotalBillController;
import model.Book;
import model.TotalBill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TotalBillControllerBoundaryValueTest {
    /*
    * Test Cases for function addBook
    * 1 - null input - assert False
    * 2 - out of lower boundary - negative value or 0 - assert False
    * 3 - out of upper boundary - quantity > book stock - assert False
    * 4 - minimum value input - quantity = 1 - assert True
    * 5 - maximum value input - quantity = book stock - assert True
    * */

    private TotalBill tbMock;
    private Book bookMock;
    private TotalBillController tbc;

    @BeforeEach
    public void setUp() {
        tbMock = mock (TotalBill.class,CALLS_REAL_METHODS);
        bookMock = mock (Book.class,CALLS_REAL_METHODS);
        tbc = new TotalBillController();
    }

    @Test
    @DisplayName("Boundary Value Testing 1 - null input")
    void test_1(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(tbMock, bookMock, null);
        });
        assertEquals("Empty quantity", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(tbMock, null, 1);
        });
        assertEquals("Book not found", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(null, bookMock, 1);
        });
        assertEquals("Empty bill", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(null, null, 1);
        });
        assertEquals("Book not found", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(tbMock, null, null);
        });
        assertEquals("Book not found", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(null, bookMock, null);
        });
        assertEquals("Empty bill", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            tbc.addBook(null, null, null);
        });
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    @DisplayName("Boundary Value Testing 2 - Quantity Negative Number or 0")
    void test_2(){
        assertFalse(tbc.addBook(tbMock, bookMock, 0));
        assertFalse(tbc.addBook(tbMock, bookMock, -1));
    }

    @Test
    @DisplayName("Boundary Value Testing 3 - Quantity Greater than Book Stock")
    void test_3(){
        int stock = 10;
        int quantity = 11;

        when(bookMock.getStock()).thenReturn(stock);

        assertFalse(tbc.addBook(tbMock, bookMock, quantity));

        verify(bookMock, times(1)).getStock();
    }

    @Test
    @DisplayName("Boundary Value Testing 4 - Minimun Quantity Value Input")
    void test_4(){
        int stock = 10;
        int quantity = 1;

        when(bookMock.getStock()).thenReturn(stock);
        //noinspection unchecked
        when(tbMock.getBooks()).thenReturn(mock(Map.class));

        assertTrue(tbc.addBook(tbMock, bookMock, quantity));

        verify(tbMock.getBooks(), times(1)).put(bookMock, quantity);
        verify(bookMock, times(1)).getStock();
    }

    @Test
    @DisplayName("Boundary Value Testing 5 - Maximum Quantity Value Input")
    void test_5(){
        int stock = 10;
        int quantity = 10;

        when(bookMock.getStock()).thenReturn(stock);
        //noinspection unchecked
        when(tbMock.getBooks()).thenReturn(mock(Map.class));

        assertTrue(tbc.addBook(tbMock, bookMock, quantity));

        verify(tbMock.getBooks(), times(1)).put(bookMock, quantity);
        verify(bookMock, times(1)).getStock();
    }

}
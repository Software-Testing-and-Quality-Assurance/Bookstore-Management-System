package test;

import controller.BookController;
import controller.ManagerController;
import model.Author;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ManagerControllerClassEvaluationTest {
/*Class Evaluation Test - Keit Nika
1 - adding a new book
2 - invalid test
3 - test when book exists
*/
    BookController bc;
    ManagerController mc;
    @BeforeEach
    void setUp(){
        bc = mock(BookController.class);
        mc = new ManagerController(bc);
    }

    @Test
    @DisplayName("Add New Book Test")
    void test1() {
        Book b = new Book("1234", "Sikur te isha djale", "Keiti", "Drama", 25.0, 20.0, new Author("Haki", "Stermilli"), 100);
        when(bc.searchBook("1234")).thenReturn(null);
        when(bc.create(b)).thenReturn(true);
        boolean res = mc.addBooks("1234", "Sikur te isha djale", "Keiti", "Drama", 25.0, 20.0, "Haki", "Stermilli", 100);
        Assertions.assertTrue(res);
        verify(bc).create(b);
    }
    @Test
    @DisplayName("Add Book Invalid Input Test(negative value, null value, 0 quantity")
    void test2(){
        boolean res = mc.addBooks("1234", "Sikur te isha djale", "Keiti", "Drama", -25.0, 20.0, "Haki", "Stermilli", 10);
        Assertions.assertFalse(res);
        res = mc.addBooks(null, "Sikur te isha djale", "Keit", "Drama", 25.0, 20.0, "Haki", "Stermilli", 5);
        Assertions.assertFalse(res);
        res = mc.addBooks("1234", "Sikur te isha djale", "Keit", "Drama", 25.0, 20.0, "Haki", "Stermilli", 0);
        Assertions.assertFalse(res);

    }

    @Test
    @DisplayName("Test when book exists")
    void test3() {
        Book b = new Book("1234", "Sikur te isha djale", "Keiti", "Drama", 25.0, 20.0, new Author("Haki", "Stermilli"), 100);
        when(bc.searchBook("1234")).thenReturn(b);
        doAnswer(invocation -> {
            b.setStock(100+b.getStock());
            return null;
        }).when(bc).restock(100,"1234");
        boolean result = mc.addBooks("1234", "Sikur te isha djale", "Keiti", "Drama", 25.0, 20.0, "Haki", "Stermilli", 100);
        Assertions.assertFalse(result);
    }

}
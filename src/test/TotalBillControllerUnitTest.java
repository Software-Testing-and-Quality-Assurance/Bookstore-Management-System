package test;

import controller.TotalBillController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Author;
import model.Book;
import model.TotalBill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static test.AdminSystemTest.tempDir;

class TotalBillControllerUnitTest {
    @TempDir
    File tempDir;
    private TotalBillController tbc;
    private Map<String, ArrayList<TotalBill>> billsPerLibrarian;
    private ObservableList<TotalBill> allBills;

    @BeforeEach
    void setUp() {
        tbc = new TotalBillController();
        Main.ALL_BILLS_FILE = new File(tempDir,"bills.dat");
        allBills = FXCollections.observableArrayList();
        billsPerLibrarian = new HashMap<>();
    }

    @Test
    void testLoadBillsFromFile() throws IOException {
        Book book = new Book("123456789", "If we were villains", "Klaudia", "Mystery", 16.0, 10.0, new Author("M.L.", "RIO"), 100);
        TotalBill b = new TotalBill("librarianKeit");
        allBills.add(b);
        b.getBooks().put(book,2);
        billsPerLibrarian.computeIfAbsent("librarianKeit", k -> new ArrayList<>()).add(b);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Main.ALL_BILLS_FILE))) {
            outputStream.writeObject(b);
        }
        tbc.loadBillsFromFile();

        assertEquals(allBills.size(),Main.allBills.size());
        assertEquals(allBills.getFirst().toString(),Main.allBills.getFirst().toString());
        assertEquals(1,Main.allBills.size());

    }
}

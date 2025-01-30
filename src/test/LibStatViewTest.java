package test;

import controller.LibrarianStatController;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Access;
import model.Employee;
import model.LibStat;
import model.Role;
import model.TotalBill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import view.LibStatView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.assertions.api.Assertions.assertThat;

class LibStatViewTest extends ApplicationTest {

    Button goBackButton;
    TableView<LibStat> tableView;
    LibStatView libStatView;
    LibrarianStatController lsc;
    TableColumn<LibStat, String> nameColumn;
    TableColumn<LibStat, String> surnameColumn;
    TableColumn<LibStat, Integer> nrOfBillsColumn;
    TableColumn<LibStat, Integer> nrOfBooksColumn;
    TableColumn<LibStat, Double> amountPropertyColumn;

    @Start
    public void start(Stage stage) {
        lsc = new LibrarianStatController();
        libStatView = new LibStatView();
        libStatView.showScene(stage);
        stage.show();
    }

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        Employee e = new Employee("admini", "p455w0r8", "Admin", "Istrator", "admini@gmail.com", Role.ADMIN, "355691212122", 100000,
                Access.YES, Access.YES, Access.YES, Access.YES, new Date(0));

        Main.employeesAll.clear();
        Main.employeesAll.add(e);

        tableView = libStatView.getTableView();
        nameColumn = (TableColumn<LibStat, String>) tableView.getColumns().get(0);
        surnameColumn = (TableColumn<LibStat, String>) tableView.getColumns().get(1);
        nrOfBillsColumn = (TableColumn<LibStat, Integer>) tableView.getColumns().get(2);
        nrOfBooksColumn = (TableColumn<LibStat, Integer>) tableView.getColumns().get(3);
        amountPropertyColumn = (TableColumn<LibStat, Double>) tableView.getColumns().get(4);

        Map<String, ArrayList<TotalBill>> billsPerLibrarian = new HashMap<>();

        ArrayList<TotalBill> bills = new ArrayList<>();
        TotalBill bill1 = new TotalBill("admini",new Date(100));
        TotalBill bill2 = new TotalBill("admini",new Date(System.currentTimeMillis()));

        bills.add(bill1);
        bills.add(bill2);

        billsPerLibrarian.put("admini", bills);
        Date date1 = new Date(0);
        Date date2 = new Date(System.currentTimeMillis());
        ObservableList<LibStat> filteredList = lsc.filterDate(date1, date2, billsPerLibrarian);
        tableView.setItems(filteredList);
    }

    @Test
    void testGoBackButton() {
        goBackButton = lookup("#previous").query();
        assertThat(goBackButton).hasText("Previous");
        goBackButton.setOnAction(e -> goBackButton.setText("clicked"));
        clickOn(goBackButton);
        sleep(500);
        assertEquals("clicked", goBackButton.getText());
    }

    @Test
    void testColumnsAndTable() {
        assertNotNull(tableView);
        assertEquals("name", ((PropertyValueFactory<LibStat, String>) nameColumn.getCellValueFactory()).getProperty());
        assertEquals("surname", ((PropertyValueFactory<LibStat, String>) surnameColumn.getCellValueFactory()).getProperty());
        assertEquals("nrOfBills", ((PropertyValueFactory<LibStat, Integer>) nrOfBillsColumn.getCellValueFactory()).getProperty());
        assertEquals("nrOfBooks", ((PropertyValueFactory<LibStat, Integer>) nrOfBooksColumn.getCellValueFactory()).getProperty());
        assertEquals("amount", ((PropertyValueFactory<LibStat, Double>) amountPropertyColumn.getCellValueFactory()).getProperty());
        assertNotNull(tableView.getItems());
        assertEquals(1, tableView.getItems().size());
        LibStat firstEmployee = tableView.getItems().getFirst();
        assertEquals("Admin", firstEmployee.getName());
        assertEquals(2, firstEmployee.getNrOfBills());

    }
}

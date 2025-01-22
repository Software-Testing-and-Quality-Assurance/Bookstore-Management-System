package test;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.LibStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import view.LibStatView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.assertions.api.Assertions.assertThat;


class LibStatViewTest extends ApplicationTest {
/*Keit Nika
  Integration Testing
* Testing LibStatView Class*/
//we can test by showing properView, but since we do not care about
//what shows after the button is clicked, but we want to know if button
//works, we mock the event that happens when button is clicked
    Button goBackButton;
    TableView<LibStat> tableView;
    LibStatView libStatView;
    TableColumn<LibStat, String> nameColumn;
    TableColumn<LibStat, String> surnameColumn;
    TableColumn<LibStat, Integer> nrOfBillsColumn;
    TableColumn<LibStat, Integer> nrOfBooksColumn;
    TableColumn<LibStat, Double> amountPropertyColumn;
  //  Employee cu = new Employee("admini", "p455w0r8", "Admin", "Istrator", "admini@gmail.com", Role.ADMIN, "355691212122", 100000,
   //         Access.YES, Access.YES, Access.YES, Access.YES, new Date(0));
    @Start
    public void start(Stage stage) {
        libStatView = new LibStatView();
        libStatView.showScene(stage);
        stage.show();
    }

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp(){
       // main.Main.currentUser = cu;
        tableView = libStatView.getTableView();
        nameColumn = (TableColumn<LibStat, String>) tableView.getColumns().get(0);
        surnameColumn = (TableColumn<LibStat, String>) tableView.getColumns().get(1);
        nrOfBillsColumn = (TableColumn<LibStat, Integer>) tableView.getColumns().get(2);
        nrOfBooksColumn = (TableColumn<LibStat, Integer>) tableView.getColumns().get(3);
        amountPropertyColumn = (TableColumn<LibStat, Double>) tableView.getColumns().get(4);

    }
    @Test
    void testGoBackButton() {

        goBackButton = lookup("#previous").query();
        assertThat(goBackButton).hasText("Previous");
        goBackButton.setOnAction(e->goBackButton.setText("clicked"));
        clickOn(goBackButton);
        sleep(500);
        assertEquals("clicked",goBackButton.getText());
    }
    @Test
    void testColumnsAndTable(){
        //see if table view exists
        assertNotNull(tableView);
        //checking the column names and validating cellValueFactory
        assertEquals("name", ((PropertyValueFactory<LibStat, String>) nameColumn.getCellValueFactory()).getProperty());
        assertEquals("surname", ((PropertyValueFactory<LibStat, String>) surnameColumn.getCellValueFactory()).getProperty());
        assertEquals("nrOfBills", ((PropertyValueFactory<LibStat, Integer>) nrOfBillsColumn.getCellValueFactory()).getProperty());
        assertEquals("nrOfBooks", ((PropertyValueFactory<LibStat, Integer>) nrOfBooksColumn.getCellValueFactory()).getProperty());
        assertEquals("amount", ((PropertyValueFactory<LibStat, Double>) amountPropertyColumn.getCellValueFactory()).getProperty());

        goBackButton = lookup("#previous").query();

        assertEquals("Name", nameColumn.getText());
        assertEquals("Surname", surnameColumn.getText());
        assertEquals("Nr of bills", nrOfBillsColumn.getText());
        assertEquals("Nr of books", nrOfBooksColumn.getText());
        assertEquals("Total Amount", amountPropertyColumn.getText());

        assertEquals("Previous",goBackButton.getText());

    }
}

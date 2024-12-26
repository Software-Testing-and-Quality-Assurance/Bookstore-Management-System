package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import main.Main;
import model.Access;
import model.Employee;
import model.Role;

public class EmployeeStat {
    private final TableView<Employee> tableView = new TableView<>();
    private final TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
    private final TableColumn<Employee, String> surnameColumn = new TableColumn<>("Surname");
    private final TableColumn<Employee, String> usernameColumn = new TableColumn<>("Username");
    private final TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
    private final TableColumn<Employee, String> phoneColumn = new TableColumn<>("Phone");
    private final TableColumn<Employee, Role> roleColumn = new TableColumn<>("Role");
    private final TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
    private final TableColumn<Employee, Access> createBillColumn = new TableColumn<>("Create Bills");
    private final TableColumn<Employee, Access> addBooksColumn = new TableColumn<>("Add Books");
    private final TableColumn<Employee, Access> checkBooksColumn = new TableColumn<>("Check Books");
    private final TableColumn<Employee, Access> checkLibrarianColumn = new TableColumn<>("Check Librarian");
    private final TableColumn<Employee, String> statusColumn = new TableColumn<>("Current Status");

    private final Button goBack = new Button("Previous");

    public void showView(Stage st) {
        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        surnameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        usernameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        roleColumn.setMinWidth(100);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        salaryColumn.setMinWidth(100);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        createBillColumn.setMinWidth(100);
        createBillColumn.setCellValueFactory(new PropertyValueFactory<>("createBill"));

        addBooksColumn.setMinWidth(100);
        addBooksColumn.setCellValueFactory(new PropertyValueFactory<>("addBooks"));

        checkBooksColumn.setMinWidth(100);
        checkBooksColumn.setCellValueFactory(new PropertyValueFactory<>("checkBooks"));

        checkLibrarianColumn.setMinWidth(100);
        checkLibrarianColumn.setCellValueFactory(new PropertyValueFactory<>("checkLibrarian"));

        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.getColumns().addAll(nameColumn, surnameColumn, usernameColumn, emailColumn, phoneColumn, roleColumn, salaryColumn,
        createBillColumn, addBooksColumn, checkBooksColumn, checkLibrarianColumn, statusColumn);
        


        goBack.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-size: 14px;");

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(goBack);
        goBack.setOnAction(e -> {
            st.setScene(Main.properView(st));
            st.show();
        });

        BorderPane bp = new BorderPane();
        bp.setCenter(tableView);
        bp.setBottom(hBox);
        bp.setStyle("-fx-background-color: white;");

        tableView.setStyle("-fx-control-inner-background: #C9E8A3;");
        
        //C9E8A3 jeshile cute
        Scene scene = new Scene(bp);
        st.setWidth(1200);
        st.setHeight(650);
        st.setTitle("Employee Data View");
        st.setScene(scene);
    }

    public TableColumn<Employee, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<Employee, String> getSurnameColumn() {
        return surnameColumn;
    }

    public TableColumn<Employee, String> getUsernameColumn() {
        return usernameColumn;
    }

    public TableColumn<Employee, String> getEmailColumn() {
        return emailColumn;
    }

    public TableColumn<Employee, String> getPhoneColumn() {
        return phoneColumn;
    }

    public TableColumn<Employee, Role> getRoleColumn() {
        return roleColumn;
    }

    public TableColumn<Employee, Double> getSalaryColumn() {
        return salaryColumn;
    }

    public TableColumn<Employee, Access> getCreateBillColumn() {
        return createBillColumn;
    }

    public TableColumn<Employee, Access> getAddBooksColumn() {
        return addBooksColumn;
    }

    public TableColumn<Employee, Access> getCheckBooksColumn() {
        return checkBooksColumn;
    }

    public TableColumn<Employee, Access> getCheckLibrarianColumn() {
        return checkLibrarianColumn;
    }

    public TableView<Employee> getTableView() {
        return tableView;
    }

    public Button getGoBack() {
        return goBack;
    }
}


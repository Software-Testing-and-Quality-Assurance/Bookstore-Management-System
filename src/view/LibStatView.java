package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.Main;
import model.LibStat;

public class LibStatView extends BorderPane {
	
    private final TableView<LibStat> librarianStatistics = new TableView<>();
    private final TableColumn<LibStat, String> nameColumn;
    private final TableColumn<LibStat, String> surnameColumn;
    private final TableColumn<LibStat, Integer> nrOfBillsColumn;
    private final TableColumn<LibStat, Integer> nrOfBooksColumn;
    private final TableColumn<LibStat, Double> amountPropertyColumn;
    private final Button goBackButton = new Button("Previous");

    public LibStatView() {
        librarianStatistics.setEditable(true);
        librarianStatistics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nrOfBillsColumn = new TableColumn<>("Nr of bills");
        nrOfBillsColumn.setMinWidth(100);
        nrOfBillsColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBills"));
        nrOfBillsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nrOfBooksColumn = new TableColumn<>("Nr of books");
        nrOfBooksColumn.setMinWidth(100);
        nrOfBooksColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooks"));
        nrOfBooksColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        amountPropertyColumn = new TableColumn<>("Total Amount");
        amountPropertyColumn.setMinWidth(100);
        amountPropertyColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountPropertyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        librarianStatistics.getColumns().addAll(nameColumn, surnameColumn, nrOfBillsColumn, nrOfBooksColumn, amountPropertyColumn);


        librarianStatistics.lookup(".table-view").setStyle("-fx-background-color: #C9E8A3;");
       // #5F735F pale yellow cute
        librarianStatistics.setStyle("-fx-control-inner-background: #C9E8A3;");

        this.setCenter(librarianStatistics);
        goBackButton.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-size: 14px;");
        this.setBottom(goBackButton);
    }

    public void showScene(Stage st) {
        Scene sc = new Scene(this);
        sc.setFill(Color.web("#C9E8A3"));
        st.setTitle("Librarian Stat View");
        st.setWidth(800);        
        st.setHeight(650);
        st.setScene(sc);

        goBackButton.setOnAction(e -> {
            st.setScene(Main.properView(st));
            st.show();
        });
    }

    public TableView<LibStat> getTableView() {
        return this.librarianStatistics;
    }
}

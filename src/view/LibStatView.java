package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import model.LibStat;

public class LibStatView extends BorderPane {
	
    private final TableView<LibStat> librarianStatistics = new TableView<>();
    private final Button goBackButton = new Button("Previous");

    public LibStatView() {
        librarianStatistics.setEditable(true);
        librarianStatistics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<LibStat, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<LibStat, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<LibStat, Integer> nrOfBillsColumn = new TableColumn<>("Nr of bills");
        nrOfBillsColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBills"));

        TableColumn<LibStat, Integer> nrOfBooksColumn = new TableColumn<>("Nr of books");
        nrOfBooksColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooks"));

        TableColumn<LibStat, Double> amountPropertyColumn = new TableColumn<>("Total Amount");
        amountPropertyColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        librarianStatistics.getColumns().addAll(nameColumn, surnameColumn, nrOfBillsColumn, nrOfBooksColumn, amountPropertyColumn);


        librarianStatistics.lookup(".table-view").setStyle("-fx-background-color: #C9E8A3;");
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

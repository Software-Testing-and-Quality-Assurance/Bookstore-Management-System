package view;

import java.util.Date;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import main.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.*;

public class BookBought extends BorderPane{

	private final TableView<Book> tableView = new TableView<>();
    private final TableColumn<Book, String> isbnColumn= new TableColumn<>("ISBN");;
    private final TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
    private final TableColumn<Book, Author> authorColumn = new TableColumn<>("Author");
    private final TableColumn<Book, Integer> stockOfBook = new TableColumn<>("Stock");
    private final TableColumn<Book, Double> sellingPrice = new TableColumn<>("Selling Price");
    private final TableColumn<Book, Double> purchasePrice = new TableColumn<>("Purchase Price");
    private final TableColumn<Book, Date> firstPurchaseDate = new TableColumn<>("Purchase Date");



    private final Button goBack = new Button("Previous:");
    

    public void showView(Stage st) {
    	tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        isbnColumn.setMinWidth(100);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        isbnColumn.setSortType(TableColumn.SortType.DESCENDING);
        
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, Author>("author"));

        
        stockOfBook.setMinWidth(100);
        stockOfBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));

        
        sellingPrice.setMinWidth(100);
        sellingPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("sellingPrice"));
        
        purchasePrice.setMinWidth(100);
        purchasePrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("purchasePrice"));
        
        firstPurchaseDate.setMinWidth(180);
        firstPurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, Date>("firstPurchaseDate"));

        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, stockOfBook,sellingPrice, purchasePrice, firstPurchaseDate);
        tableView.setStyle("-fx-control-inner-background: #C9E8A3;");

        FlowPane flowPane = new FlowPane(3, 3);
        

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(goBack);
        goBack.setOnAction(e->{
        	st.setScene(Main.properView(st));
			st.show();
        });
        BorderPane bp = new BorderPane();
        bp.setCenter(tableView);
        bp.setBottom(hBox);
        Scene scene = new Scene(bp, 1000, 750);

        st.setTitle("BookBought View");
        st.setScene(scene);
        st.setWidth(800);        
        st.setHeight(650);
        st.show();
    }

    public TableColumn<Book, String> getIsbnColumn() {
        return isbnColumn;
    }
    

    public TableColumn<Book, String> getTitleColumn() {
        return titleColumn;
    }

    public TableColumn<Book, Author> getAuthorColumn() {
        return authorColumn;
    }

    public TableColumn<Book, Integer> getStockColumn() {
        return stockOfBook;
    }

    public TableColumn<Book, Double> getSellingPriceColumn() {
        return sellingPrice;
    }

    public TableColumn<Book, Double> getPurchasePriceColumn() {
        return purchasePrice;
    }
    
    public TableColumn<Book, Date> getPurchaseDateeColumn() {
        return firstPurchaseDate;
    }
    
    public TableView<Book> getTableView() {
        return tableView;
    }
    public Button getGoBack() {
        return goBack;
    }
    
}



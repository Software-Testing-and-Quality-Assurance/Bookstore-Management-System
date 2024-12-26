package view;

import controller.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import model.Author;
import model.Employee;

public class AddBookView {
	
		private String isbn;
		private String title;
		private String supplier;
		private String category;
		private double sellingP;
		private double originalP;
		private String authorName;
		private String authorSurname;
		private int stock;
		private Employee employee;
		ManagerController mc = new ManagerController();
		BookController bc = new BookController();
		public Scene showView(Stage st) {
			
			GridPane g = new GridPane();
			g.setHgap(10);
			g.setVgap(10);
			g.setPadding(new Insets(10, 10, 10, 10));
			g.setAlignment(Pos.CENTER);
			Button addB = new Button("Add Book:");
		    addB.setStyle("-fx-background-color: #630607; -fx-text-fill: white;"); 

			Label isbnL = new Label("Book isbn:");
			isbnL.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField isbnF = new TextField();
		    g.add(isbnL, 0, 0);
		    g.add(isbnF, 1, 0);

		    Label titleL = new Label("Title:");
		    titleL.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField titleF = new TextField();
		    g.add(titleL, 0, 1);
		    g.add(titleF, 1, 1);

		    Label supplierL = new Label("Supplier:");
		    supplierL.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField supplierF = new TextField();
		    g.add(supplierL, 0, 2);
		    g.add(supplierF, 1, 2);
			
		    Label categoryLabel = new Label("Category:");
		    categoryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField categoryF = new TextField();
		    g.add(categoryLabel, 0, 3);
		    g.add(categoryF, 1, 3);

		    Label sellingPriceLabel = new Label("Selling Price:");
		    sellingPriceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField sellingPF = new TextField();
		    g.add(sellingPriceLabel, 0, 4);
		    g.add(sellingPF, 1, 4);

		    Label originalPriceLabel = new Label("Original Price:");
		    originalPriceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField originalPF = new TextField();
		    g.add(originalPriceLabel, 0, 5);
		    g.add(originalPF, 1, 5);

		    Label authorNameLabel = new Label("Author Name:");
		    authorNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField authorNameF = new TextField();
		    g.add(authorNameLabel, 0, 6);
		    g.add(authorNameF, 1, 6);

		    Label authorSurnameLabel = new Label("Author Surname:");
		    authorSurnameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField authorSurnameF = new TextField();
		    g.add(authorSurnameLabel, 0, 7);
		    g.add(authorSurnameF, 1, 7);
		    
		    Label stockLabel = new Label("Nr of Books:");
		    stockLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		    TextField stockF = new TextField();
		    g.add(stockLabel, 0, 8);
		    g.add(stockF, 1, 8);
		    
		    Button goBackButton = new Button("Previous");
		    goBackButton.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;");
			g.add(goBackButton, 0, 9);
		    g.add(addB, 1, 9);
		    
		    goBackButton.setOnAction(e-> {
				st.setScene(Main.properView(st));
				st.show();
			});
		    
		    addB.setOnAction(e-> {
		    	if(isbnF.getText().equals("") || titleF.getText().equals("") || supplierF.getText().equals("") || categoryF.getText().equals("")
						 || sellingPF.getText().equals("") || originalPF.getText().equals("") || authorNameF.getText().equals("") || 
						 authorSurnameF.getText().equals("")) {
					 Main.emptyAlert();
					 return;
				 }
		    	
		    		try {
		    			isbn = isbnF.getText();
				    	title = titleF.getText();
				    	supplier = supplierF.getText();
				    	category = categoryF.getText();
				    	sellingP = Double.parseDouble(sellingPF.getText());
				    	originalP = Double.parseDouble(originalPF.getText());
				    	authorName = authorNameF.getText();
				    	authorSurname = authorSurnameF.getText();
				    	stock = Integer.parseInt(stockF.getText());
				    	if(bc.bookFound(isbn)) {
				    		Alert al = new Alert(AlertType.WARNING);
				    		al.setHeaderText("Exists!");
				    		al.setContentText("You have already logged this book before.");
				    		
				    	}
				    		
				    	else{boolean isAdd = mc.addBooks(isbn, title, supplier, category, sellingP, originalP, authorName, authorSurname, stock);
				    	if (isAdd) {
				    		Main.doneAlert();
				    	}}
		    		} catch (NumberFormatException ex) {
		    			Main.invalidAlert(st);
		    		}
		    	
		    	
		    	isbnF.clear();
	    		titleF.clear();
	    		supplierF.clear();
	    		categoryF.clear();
	    		sellingPF.clear();
	    		originalPF.clear();
	    		authorNameF.clear();
	    		authorSurnameF.clear();
	    		stockF.clear();
		    });
			Scene sc = new Scene(g);
		    g.setStyle("-fx-background-color: #7a584e;");
		    st.setWidth(500);
			st.setHeight(700);
			return sc;
		}
		
	
}


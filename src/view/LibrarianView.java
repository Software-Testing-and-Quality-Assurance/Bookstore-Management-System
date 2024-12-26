package view;

import controller.LibrarianController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import model.Access;
import model.TotalBill;

public class LibrarianView {

    public Scene showView(Stage st) {
        GridPane gd = new GridPane();
        gd.setPadding(new Insets(20));
        gd.setAlignment(Pos.CENTER);
        gd.setHgap(10);
        gd.setVgap(10);
		gd.setStyle("-fx-background-color: #6F4E37;"); 
		Label welcomeLabel = new Label("Welcome, Librarian!");
        welcomeLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
        gd.add(welcomeLabel, 0, 0, 2, 1); 
        
        Button checkOut = createButton("Create a Bill", "Checkout and create a new bill");
        Button bookInfo = createButton("Book Log", "View information about books");
        Button addBooks = createButton("Add a Book", "Add a new book to the inventory");
        Button signOut = createButton("Sign Out", "Sign out from the application");

        
        gd.add(checkOut, 0, 1);
        gd.add(bookInfo, 0, 2);
        gd.add(addBooks, 0, 3);
        gd.add(signOut, 0, 4);

        addBooks.setOnAction(e -> {
            if (Main.currentUser.getAddBooks() == Access.NO) {
                Main.accessAlert();
                return;
            }
            AddBookView abv = new AddBookView();
            st.setScene(abv.showView(st));
            st.show();
        });

        checkOut.setOnAction(e -> {
            System.out.println(Main.currentUser.getCreateBill());
            if (Main.currentUser.getCreateBill() == Access.NO) {
                Main.accessAlert();
                return;
            }
            LibrarianController lc = new LibrarianController();
            TotalBill newBill = new TotalBill(Main.currentUser.getUsername());
            CreateBillView cbv = new CreateBillView(lc, newBill);
            st.setScene(cbv.showView(st));
            st.show();
        });

        bookInfo.setOnAction(e -> {
            if (Main.currentUser.getCheckBooks() == Access.NO) {
                Main.accessAlert();
                return;
            } else {
                BookBought bookBought = new BookBought();
                bookBought.getTableView().setItems(Main.bookStock);
                bookBought.showView(st);
                st.show();
            }
        });

        signOut.setOnAction(e -> {
            LoginView lv = new LoginView();
            st.setScene(lv.showView(st));
            st.show();
        });

        st.setTitle("Librarian");
        Scene sc = new Scene(gd);
        st.setWidth(550);
	    st.setHeight(600);
	    st.setResizable(false);
        return sc;
    }

    private Button createButton(String text, String tooltipText) {
        Button button = new Button(text);
        button.setMinWidth(150);
        button.setTooltip(new Tooltip(tooltipText));
        button.setStyle("-fx-background-color: #630607; -fx-text-fill: white; -fx-font-size: 14px;");
        return button;
    }
}


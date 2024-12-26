package view;

import controller.ManagerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;
import model.Access;

public class ManagerView {

    private Scene previousScene;

    public Scene showView(Stage st) {
        System.out.println(Main.currentUser.getUsername());


        GridPane gd = new GridPane();
        gd.setStyle("-fx-background-color: #6F4E37;");
        gd.setVgap(10);
        gd.setHgap(20);
        gd.setPadding(new Insets(15, 15, 15, 15));
        gd.setAlignment(Pos.CENTER);


        Label welcomeLabel = new Label("Welcome, Manager!");
        welcomeLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
        gd.add(welcomeLabel, 0, 0, 2, 1); 

        Button addBook = createStyledButton("Add books", "Add new or existing books", "#A76B4D");
        Button billInfo = createStyledButton("Bill info", "View billing information", "#B89A78");
        Button bookInfo = createStyledButton("Book log", "View information about books", "#5F735F");
        Button signOut = createStyledButton("Sign Out", "Sign out from the application", "#7C3030");

        gd.add(addBook, 0, 1);
        gd.add(billInfo, 1, 1);
        gd.add(bookInfo, 0, 2);
        gd.add(signOut, 1, 2);

        addBook.setOnAction(e -> {
            if (Main.currentUser.getAddBooks() == Access.NO) {
                Main.accessAlert();
                return;
            }

            previousScene = st.getScene(); 
            addExistBook(st);
        });

        bookInfo.setOnAction(e -> {
            if (Main.currentUser.getCheckBooks() == Access.NO) {
                Main.accessAlert();
                return;
            }

            ChooseDates cdv = new ChooseDates();
            cdv.showView(st, "Book Bought");
        });

        billInfo.setOnAction(e -> {
            if (Main.currentUser.getCheckLibrarian() == Access.NO) {
                Main.accessAlert();
                return;
            }

            ChooseDates cdv = new ChooseDates();
            cdv.showView(st, "Bill info");
        });

        signOut.setOnAction(e -> {
            LoginView lv = new LoginView();
            st.setScene(lv.showView(st));
            st.show();
        });

        Scene sc = new Scene(gd);
        st.setWidth(550);
        st.setHeight(600);
		st.setResizable(false);

        return sc;
    }

    private void addExistBook(Stage st) {
        GridPane g = new GridPane();
        g.setVgap(15); 
        Label headerLabel = new Label("Adding Books");
        headerLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white;");
        g.add(headerLabel, 0, 0, 2, 1); 

        Label isbnL = new Label("Book ISBN:");
        isbnL.setStyle("-fx-text-fill: white;"); 
        TextField isbnF = new TextField();
        g.add(isbnL, 0, 1);
        g.add(isbnF, 1, 1);

        Label stockLabel = new Label("Quantity:");
        stockLabel.setStyle("-fx-text-fill: white;"); 
        TextField stockF = new TextField();
        g.add(stockLabel, 0, 2);
        g.add(stockF, 1, 2);

        Button addNew = createStyledButton("Add new book", "Add a completely new book", "#A76B4D");
        Button add = createStyledButton("Add existing book", "Add an existing book to the inventory", "#8E6E59");

        g.add(addNew, 0, 3);
        g.add(add, 1, 3);

        GridPane.setMargin(addNew, new Insets(10, 0, 0, 0)); 
        GridPane.setMargin(add, new Insets(10, 0, 0, 30));

        g.setAlignment(Pos.CENTER);
        g.setStyle("-fx-background-color: #614840;"); 
        st.setScene(new Scene(g));
        st.setWidth(500);
	    st.setHeight(700);
	    st.setResizable(false);

        st.show();

        add.setOnAction(e -> {
            ManagerController m = new ManagerController();

            try {
                String isbn = isbnF.getText();
                int stock = Integer.parseInt(stockF.getText());
                if (isbn.equals("")) {
                    throw new IllegalArgumentException();
                }
                if (m.addExist(isbn, stock)) {
                    Main.doneAlert();
                    st.setScene(previousScene); 
                    st.show();
                } else {
                    isbnF.clear();
                    stockF.clear();
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setHeaderText("No book found");
                    al.setContentText("Our bookstore does not have a book with this ISBN. Enter an existing book, or press \"Add new book\"");
                    al.showAndWait();
                }
            } catch (IllegalArgumentException e2) {
                Alert nah = new Alert(Alert.AlertType.ERROR);
                nah.setHeaderText("Enter valid input");
                nah.setContentText("Enter a valid ISBN and quantity! ");
                nah.showAndWait();
            }
        });

        addNew.setOnAction(e -> {
            AddBookView abv = new AddBookView();
            st.setScene(abv.showView(st));
            st.setWidth(500);
            st.setHeight(700);
            st.show();
        });
    }
    
    private Button createStyledButton(String text, String tooltipText, String backgroundColor) {
        Button button = new Button(text);
        button.setMinWidth(100);
        button.setTooltip(new Tooltip(tooltipText));
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white;");
        return button;
    }
}


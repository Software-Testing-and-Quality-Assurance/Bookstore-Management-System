package view;

import controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import model.TotalBill;

public class CreateBillView {
    private final LibrarianController lc;
    private final TotalBill bill;
    boolean sameLibrarian = true;

    public CreateBillView(LibrarianController librarianController, TotalBill bill) {
        this.lc = librarianController;
        this.bill = bill;
    }

    public Scene showView(Stage st) {
        GridPane g = new GridPane(100, 100);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setAlignment(Pos.CENTER);
        g.setStyle("-fx-background-color: #6F4E37;"); 

        Button ok = new Button("Add");
        Button goBack = new Button("Previous");
        HBox h = new HBox();
        h.getChildren().addAll(goBack, ok);
        HBox.setMargin(goBack, new Insets(0, 10, 0, 0));

        

        Label isbnL = new Label("Book isbn:");
        TextField isbnF = new TextField();
        isbnL.setStyle("-fx-text-fill: white;-fx-font-size: 14px;");
        g.add(isbnL, 0, 0);
        g.add(isbnF, 1, 0);

        Label quantityL = new Label("Quantity:");
        TextField quantityF = new TextField();
        quantityL.setStyle("-fx-text-fill: white;-fx-font-size: 14px;");
        g.add(quantityL, 0, 2);
        g.add(quantityF, 1, 2);

        g.add(h, 0, 3);

        goBack.setOnAction(e -> {
            st.setScene(Main.properView(st));
            st.show();
        });

        ok.setOnAction(e -> {
            String isbn = isbnF.getText();
            try {
                int quantity = Integer.parseInt(quantityF.getText());
                if (lc.checkOutBook(isbn, quantity, bill))
                    System.out.println("OneBookBill.");

            } catch (NumberFormatException ex) {
    			Main.invalidAlert(st);
                System.out.println("Invalid quantity format. Please enter a valid integer.");
            }
            ;
            st.setScene(multiCheckout(st, bill));

        });

        Scene sc = new Scene(g);
        st.setWidth(600);
        st.setHeight(500);
        st.setResizable(false);
        return sc;
    }

    public Scene multiCheckout(Stage st, TotalBill tb) {
        Button next = new Button("Enter next");
        next.setMinWidth(80);
        Button done = new Button("Done");
        done.setMinWidth(80);

        GridPane bp = new GridPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        bp.setVgap(15);
        bp.setAlignment(Pos.CENTER);
        bp.setStyle("-fx-background-color: #4A0404;"); 
        bp.add(next, 0, 0);
        bp.add(done, 0, 1);

        next.setOnAction(ev -> {
            CreateBillView cbv = new CreateBillView(lc, tb);
            st.setScene(cbv.showView(st));
            st.show();
        });

        done.setOnAction(ev -> {
            if (lc.checkOutFinal(tb)) {
                System.out.println("totalBill completed!");
            }
            LibrarianView a = new LibrarianView();
            st.setScene(a.showView(st));
            st.show();
        });

        Scene sc = new Scene(bp);
        st.setWidth(320);
        st.setHeight(320);
        st.setResizable(false);
        return sc;

    }

}


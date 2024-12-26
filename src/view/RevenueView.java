package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class RevenueView {

    public Scene showView(Stage st, double[] revenue) {
        Text costText = new Text(String.valueOf(revenue[1]));
        Text incomeText = new Text(String.valueOf(revenue[0]));

        Label costLabel = new Label("Total Cost:");
        Label incomeLabel = new Label("Total Income:");

        costLabel.setFont(Font.font("Arial", 16));
        incomeLabel.setFont(Font.font("Arial", 16));
        costText.setFont(Font.font("Arial", 16));
        incomeText.setFont(Font.font("Arial", 16));
        costText.setFill(Color.BLACK);
        incomeText.setFill(Color.BLACK);
	    costLabel.setStyle("-fx-font-size: 16; -fx-text-fill: black;");
	    incomeLabel.setStyle("-fx-font-size: 16; -fx-text-fill: black;");


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(costLabel, 0, 1);
        gridPane.add(incomeLabel, 0, 2);
        gridPane.add(costText, 1, 1);
        gridPane.add(incomeText, 1, 2);

        Button goBackButton = new Button("Previous");
        goBackButton.setStyle("-fx-background-color: #52413c; -fx-text-fill: white; -fx-font-size: 14px;");

        VBox vbox = new VBox(20); 
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(gridPane, goBackButton);
		vbox.setStyle("-fx-background-color: #B6874E;"); 

        Scene scene = new Scene(vbox);

        goBackButton.setOnAction(event -> {
            st.setScene(Main.properView(st));
            st.show();
        });
        st.setHeight(600);
        st.setWidth(500);
	    st.setResizable(false);
        return scene;
    }
}

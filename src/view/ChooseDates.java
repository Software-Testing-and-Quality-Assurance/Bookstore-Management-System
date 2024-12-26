package view;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import controller.AdminController;
import controller.BookBoughtController;
import controller.LibrarianStatController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;


public class ChooseDates {
	public void showView(Stage st, String action) {
		st.setTitle("Date Range");

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(10, 10, 10, 10));
	    grid.setStyle("-fx-background-color: #5F735F;");


	    Label startDateLabel = new Label("Start Date");
	    startDateLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

	    DatePicker startDatePicker = new DatePicker();
	    startDatePicker.setPromptText("Start Date");

	    Label endDateLabel = new Label("End Date");
	    endDateLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

	    DatePicker endDatePicker = new DatePicker();
	    endDatePicker.setPromptText("End Date");

	    grid.add(startDateLabel, 0, 0);
	    grid.add(startDatePicker, 1, 0);
	    grid.add(endDateLabel, 0, 1);
	    grid.add(endDatePicker, 1, 1);


	    Scene scene = new Scene(grid);
	    st.setWidth(300);
	    st.setHeight(190);
		st.setResizable(false);
	    st.setScene(scene);
	    st.show();
	    
	    Button pickDatesButton = new Button("Pick Dates");
        pickDatesButton.setOnAction(e -> {
        	
        	LocalDate localDate1 = startDatePicker.getValue();
            LocalDate localDate2 = endDatePicker.getValue();

        	if(localDate1==null || localDate2==null)
        		Main.emptyAlert();
        	else {
        		Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
                Date date1 = Date.from(instant1);       
                Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
                Date date2 = Date.from(instant2);
                
                printSelectedDates(startDatePicker.getValue(), endDatePicker.getValue());
                if(action.equals("Bill info")) {
                	LibrarianStatController lsc = new LibrarianStatController();
                    LibStatView lsv = new LibStatView();
                    lsv.getTableView().setItems(lsc.filterDate(date1, date2));
                    lsv.showScene(st);
                }
                
                else if (action.equals("Book Bought")){
                	BookBoughtController bbc = new BookBoughtController();
                	BookBought bookBought = new BookBought();
        		    bookBought.getTableView().setItems(bbc.filterDate(date1, date2));


        		    bookBought.showView(st);
        		    st.show();
                }
                
                else {
                	RevenueView rv= new RevenueView();
                    double[] revenue = (new AdminController()).revenue(date1, date2, localDate1, localDate2);
                	Scene s = rv.showView(st,  revenue);
                	st.setScene(s);
                	st.show();
                }
        	}
            
        });
        grid.add(pickDatesButton, 1, 2);

	}
	 private void printSelectedDates(LocalDate startDate, LocalDate endDate) {
         if (startDate != null && endDate != null) {
             System.out.println("Start Date: " + startDate);
             System.out.println("End Date: " + endDate);
         } else {
             System.out.println("Please select both start and end dates.");
         }
     }
	 
}


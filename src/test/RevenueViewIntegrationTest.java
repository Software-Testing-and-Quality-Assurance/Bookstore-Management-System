/*
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * SWE 303: Software Testing & Quality Assurance * *
 * * * * * * * * * Project: Code Testing * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * Integration Testing * * * * * * * * *
 * * * * * Test done by: Klaudia Tamburi * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.RevenueView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevenueViewIntegrationTest extends ApplicationTest {
    /*
     * Testing the RevenueView to
     * see if the elements show up
     * correctly in UI
     */
    @Override
    public void start(Stage stage) {
        RevenueView revenueView = new RevenueView();
        double[] revenue = {5000.0, 2000.0};
        Scene scene = revenueView.showView(stage, revenue);
        stage.setScene(scene);
        stage.show();
    }

    Button goBack;
    Label totalCostLabel;
    Label totalIncomeLabel;
    Text totalCostValue;
    Text totalIncomeValue;

    @Test
    void testGoBackButton() {
        goBack = lookup("#previous").query();

        // Mock button click behaviour since we only care to see if button is clicked not to show next scene
        goBack.setOnAction(event -> goBack.setText("Clicked"));
        clickOn(goBack);
        sleep(500);
        assertEquals("Clicked", goBack.getText());
    }

    @Test
    void testLabelsAndTexts() {
        totalCostLabel = lookup("#costL").query();
        totalIncomeLabel = lookup("#incomeL").query();
        totalCostValue = lookup("#cost").query();
        totalIncomeValue = lookup("#income").query();
        goBack = lookup("#previous").query();

        assertEquals("Total Cost:", totalCostLabel.getText());
        assertEquals("Total Income:", totalIncomeLabel.getText());

        assertEquals("2000.0", totalCostValue.getText());
        assertEquals("5000.0", totalIncomeValue.getText());

        assertEquals("Previous", goBack.getText());
    }
}

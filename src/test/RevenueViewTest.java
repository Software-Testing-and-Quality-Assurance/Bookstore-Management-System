package test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.RevenueView;

class RevenueViewTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        RevenueView revenueView = new RevenueView();
        double[] revenue = {5000.0, 2000.0};
        Scene scene = revenueView.showView(stage, revenue);
        stage.setScene(scene);
        stage.show();
    }

    Button goBack;

    @Test
    void testGoBackButton() {
        goBack=lookup("#Previous").query();
        clickOn("Previous");

    }
}

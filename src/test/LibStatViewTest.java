package test;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import view.LibStatView;

import static org.testfx.assertions.api.Assertions.assertThat;


class LibStatViewTest extends ApplicationTest {

    private Button goBackButton;

    @Start
    public void start(Stage stage) {
        LibStatView libStatView = new LibStatView();
        libStatView.showScene(stage);
        stage.show();
    }
    @BeforeEach
    public void setUp() {
        goBackButton = lookup(".button").queryAs(Button.class);
    }
    @Test
    void showScene() {
        assertThat(goBackButton).hasText("Previous");
    }
}

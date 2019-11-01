package no.hiof.set.gruppe.tests.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.controller.abstractions.IController;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;


@ExtendWith(ApplicationExtension.class)
public class MainJavaFXTest {
    private Stage stage;
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource("Login.fxml"));
        Parent editLayout = loader.load();

        IController controller = (loader.getController());
        controller.setMainController(new MainJavaFX());

        Scene scene = new Scene(editLayout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Logg inn");
        stage.show();
    }

    @Test
    void enter_correct_login_information_from_btns(@NotNull FxRobot robot){
        robot.clickOn("#adminLogin");
        Assertions.assertThat(robot.lookup("#uName").queryAs(TextField.class)).hasText("Admin");
    }
}
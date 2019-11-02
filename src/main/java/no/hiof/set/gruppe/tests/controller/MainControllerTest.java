package no.hiof.set.gruppe.tests.controller;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
@ExtendWith(ApplicationExtension.class)
class MainControllerTest extends MainJavaFXTest{
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        new MainJavaFXTest().start(stage);
    }

    @Test
    void enter_correct_login_information_from_btns(@NotNull FxRobot robot){
        robot.clickOn("#adminLogin");
        Assertions.assertThat(robot.lookup("#uName").queryAs(TextField.class)).hasText("Admin");
        Assertions.assertThat(robot.lookup("#pass").queryAs(TextField.class)).hasText("Password1");

        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("ADMIN");
        robot.clickOn("#logOut");


        robot.clickOn("#arrangLogin");
        Assertions.assertThat(robot.lookup("#uName").queryAs(TextField.class)).hasText("Organizer");
        Assertions.assertThat(robot.lookup("#pass").queryAs(TextField.class)).hasText("Password3");
        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("Arrangør");
        robot.clickOn("#logOut");


        robot.clickOn("#userLogin");
        Assertions.assertThat(robot.lookup("#uName").queryAs(TextField.class)).hasText("ProtoUser");
        Assertions.assertThat(robot.lookup("#pass").queryAs(TextField.class)).hasText("Password2");
        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("Bruker");
        robot.clickOn("#logOut");
    }
}
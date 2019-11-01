package no.hiof.set.gruppe.tests.controller;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.user.ProtoUser;
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
        TextField uName = robot.lookup("#uName").queryAs(TextField.class);
        TextField pass = robot.lookup("#pass").queryAs(TextField.class);

        robot.clickOn("#adminLogin");
        Assertions.assertThat(uName).hasText("Admin");
        Assertions.assertThat(pass).hasText("Password1");

        robot.clickOn("#userLogin");
        Assertions.assertThat(uName).hasText("ProtoUser");
        Assertions.assertThat(pass).hasText("Password2");

        robot.clickOn("#arrangLogin");
        Assertions.assertThat(uName).hasText("Organizer");
        Assertions.assertThat(pass).hasText("Password3");
    }

    @Test
    void get_correct_view_admin(@NotNull FxRobot robot){
        robot.clickOn("#adminLogin");
        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("ADMIN");
    }

    @Test
    void get_correct_view_User(@NotNull FxRobot robot){
        robot.clickOn("#userLogin");
        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("Bruker");
    }

    @Test
    void get_correct_view_Organizer(@NotNull FxRobot robot){
        robot.clickOn("#arrangLogin");
        robot.clickOn("#logInn");
        Assertions.assertThat(robot.lookup("#mainTitle").queryAs(Text.class)).hasText("Mine arrangementer");
    }
}

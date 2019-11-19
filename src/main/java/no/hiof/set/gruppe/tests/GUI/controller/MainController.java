package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
@ExtendWith(ApplicationExtension.class)
class MainController extends MainJavaFXTest{
    @Start
    void start(@NotNull Stage stage) throws IOException {
        new MainJavaFXTest().start(stage);
    }

    @Test
    void enter_correct_login_information_from_btns(@NotNull FxRobot robot){
        String[] userMenuItems = {"adminLogin", "arrangLogin", "userLogin"};
        String[] stageTitles = {"Logget inn som serviceadministrator", "Logget inn som Arrangør", "Logget inn som Bruker"};
        ProtoUser[] users = {ProtoUser.ADMIN, ProtoUser.ORGANIZER, ProtoUser.USER};

        for(int i = 0; i < userMenuItems.length; i++){
            robot.clickOn("#cheatLogin");
            first_assert_user_information(userMenuItems[i], users[i], robot);
            then_assert_login_has_correct_window(robot, stageTitles[i]);
        }
    }

    private void first_assert_user_information(@NotNull String userBtn, @NotNull ProtoUser user, @NotNull FxRobot usingRobot){
        usingRobot.clickOn("#" + userBtn);
        Assertions.assertThat(usingRobot.lookup("#uName").queryAs(TextField.class)).hasText(user.getName());
        Assertions.assertThat(usingRobot.lookup("#pass").queryAs(TextField.class)).hasText(user.getPass());
    }

    private void then_assert_login_has_correct_window(@NotNull FxRobot usingRobot, String stageTitle) {
        usingRobot.clickOn("#logInn");
        Assertions.assertThat(usingRobot.lookup("#mainTitle").queryAs(Text.class)).hasText(stageTitle);
        usingRobot.clickOn("#menu");
        usingRobot.clickOn("#logOut");
    }
}
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
        String[] userBtns = {"adminLogin", "arrangLogin", "userLogin"};
        String[] stageTitles = {"ADMIN", "Arrangør", "Bruker"};
        ProtoUser[] users = {ProtoUser.ADMIN, ProtoUser.ORGANIZER, ProtoUser.USER};

        for(int i = 0; i < userBtns.length; i++){
            firstAssertUserInformation(userBtns[i], users[i], robot);
            thenAssertLoginHasCorrectWindow(robot, stageTitles[i]);
        }
    }

    private void firstAssertUserInformation(@NotNull String userBtn, @NotNull ProtoUser user, @NotNull FxRobot usingRobot){
        usingRobot.clickOn("#" + userBtn);
        Assertions.assertThat(usingRobot.lookup("#uName").queryAs(TextField.class)).hasText(user.getName());
        Assertions.assertThat(usingRobot.lookup("#pass").queryAs(TextField.class)).hasText(user.getPass());
    }

    private void thenAssertLoginHasCorrectWindow(@NotNull FxRobot usingRobot, String stageTitle) {
        usingRobot.clickOn("#logInn");
        Assertions.assertThat(usingRobot.lookup("#mainTitle").queryAs(Text.class)).hasText(stageTitle);
        usingRobot.clickOn("#logOut");
    }
}
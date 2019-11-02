package no.hiof.set.gruppe.tests.controller;

import javafx.stage.Stage;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class UserControllerTests extends MainJavaFXTest{
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.USER.getViewName());
        mainJavaFXTest.start(stage);
    }

    //Test for opening of window

    //Test for adding user to arrangement

    //test for removing user from arrangement

    //test sorting functionality

    //test that information about arrangement shows

}

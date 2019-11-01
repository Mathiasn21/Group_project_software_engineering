package no.hiof.set.gruppe.tests.controller;

import javafx.stage.Stage;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class OrganizerControllerTests extends MainJavaFXTest{
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ORGANIZER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void test1(@NotNull FxRobot robot){
        robot.clickOn("#newArrangementBtn");
    }
}

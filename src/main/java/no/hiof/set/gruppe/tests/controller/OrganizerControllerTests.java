package no.hiof.set.gruppe.tests.controller;

import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(ApplicationExtension.class)
public class OrganizerControllerTests extends MainJavaFXTest{
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ORGANIZER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void testCreatingNewArrangement(@NotNull FxRobot robot) {
        ListView listView = robot.lookup("#listview").queryAs(ListView.class);
        Arrangement arrangement = new Arrangement(
                "ThisIsNerdy",
                "Alle",
                42,
                "NerdStreet - 22",
                true,
                "2020-11-09",
                "2020-12-09",
                "Very Nerdy.");

        robot.clickOn("#newArrangementBtn");
        fillOutForm(robot, arrangement);
        robot.clickOn("#saveBtn");
        assertFieldsCorrespondToNewArrangement(robot, arrangement);

        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        robot.clickOn("#deleteBtn");

        /*
        double height = listView.getHeight();
        double width = listView.getWidth();
        double presumedCellSize = listView.getFixedCellSize();
        Node node = listView.getChildrenUnmodifiable().get(0);
        Bounds bounds = node.localToScreen(node.getBoundsInLocal());

        robot.clickOn(bounds.getCenterX(), bounds.getCenterY() + height/(listView.getFixedCellSize()/2)/4 + 10);
        */
    }


    private void assertFieldsCorrespondToNewArrangement(@NotNull FxRobot robot, @NotNull Arrangement arrangement) {
        Assertions.assertThat(robot.lookup("#arrangementName").queryAs(Text.class)).hasText(arrangement.getName());
        Assertions.assertThat(robot.lookup("#arrangementSport").queryAs(Text.class)).hasText(arrangement.getSport());
        Assertions.assertThat(robot.lookup("#arrangementAdress").queryAs(Text.class)).hasText(arrangement.getAddress());
        Assertions.assertThat(robot.lookup("#arrangementDate").queryAs(Text.class)).hasText(arrangement.getDateIntervall());
        Assertions.assertThat(robot.lookup("#arrangementGorI").queryAs(Text.class)).hasText(arrangement.getGroup());
        Assertions.assertThat(robot.lookup("#arrangementParticipants").queryAs(Text.class)).hasText(String.valueOf(arrangement.getParticipants()));
        Assertions.assertThat(robot.lookup("#arrangementDescription").queryAs(Text.class)).hasText(arrangement.getDescription());
    }
    private void fillOutForm(@NotNull FxRobot robot, @NotNull Arrangement arrangement) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        robot.clickOn("#nameInput").write(arrangement.getName());
        robot.clickOn("#participantsInput").write(String.valueOf(arrangement.getParticipants()));
        robot.clickOn("#adressInput").write(arrangement.getAddress());

        robot.clickOn("#startDateInput").write(arrangement.getStartDate().format(format));
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);
        robot.clickOn("#endDateInput").write(arrangement.getEndDate().format(format));
        robot.press(KeyCode.ENTER);
        robot.release(KeyCode.ENTER);

        robot.clickOn("#descriptionInput").write(arrangement.getDescription());
    }
}

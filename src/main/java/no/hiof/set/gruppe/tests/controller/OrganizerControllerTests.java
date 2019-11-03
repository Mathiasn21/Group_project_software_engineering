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
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(ApplicationExtension.class)
public class OrganizerControllerTests extends MainJavaFXTest{

    private Arrangement arrangement = new Arrangement(
                "ThisIsNerdy",
                        "Alle",
                        400,
                        "NerdStreet - 22",
                        true,
                        "2020-11-09",
                        "2020-12-09",
                        "Very Nerdy.");
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ORGANIZER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void testCreatingNewArrangement(@NotNull FxRobot robot) {
        ListView listView = robot.lookup("#listview").queryAs(ListView.class);
        robot.clickOn("#newArrangementBtn");
        fillOutForm(robot);
        robot.clickOn("#saveBtn");
        assertFieldsCorrespondToNewArrangement(robot);

        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        testAlterArrangement(robot);
        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        robot.clickOn("#deleteBtn");
    }

    //Test that alterAdd functionality works
    void testAlterArrangement(@NotNull FxRobot usingRobot){
        ListView listView = usingRobot.lookup("#listview").queryAs(ListView.class);
        listView.getSelectionModel().selectLast();
        usingRobot.clickOn("#editBtn");
        fillOutForm(usingRobot);
        usingRobot.clickOn("#saveBtn");
        arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        assertFieldsCorrespondToNewArrangement(usingRobot);
    }

    //test sorting works
    private void assertFieldsCorrespondToNewArrangement(@NotNull FxRobot robot) {
        Assertions.assertThat(robot.lookup("#arrangementName").queryAs(Text.class)).hasText(arrangement.getName());
        Assertions.assertThat(robot.lookup("#arrangementSport").queryAs(Text.class)).hasText(arrangement.getSport());
        Assertions.assertThat(robot.lookup("#arrangementAdress").queryAs(Text.class)).hasText(arrangement.getAddress());
        Assertions.assertThat(robot.lookup("#arrangementDate").queryAs(Text.class)).hasText(arrangement.getDateIntervall());
        Assertions.assertThat(robot.lookup("#arrangementGorI").queryAs(Text.class)).hasText(arrangement.getGroup());
        Assertions.assertThat(robot.lookup("#arrangementParticipants").queryAs(Text.class)).hasText(String.valueOf(arrangement.getParticipants()));
        Assertions.assertThat(robot.lookup("#arrangementDescription").queryAs(Text.class)).hasText(arrangement.getDescription());
    }

    private void fillOutForm(@NotNull FxRobot usingRobot) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        usingRobot.clickOn("#nameInput").write(arrangement.getName());
        usingRobot.clickOn("#participantsInput").write(String.valueOf(arrangement.getParticipants()));
        usingRobot.clickOn("#adressInput").write(arrangement.getAddress());

        usingRobot.clickOn("#startDateInput").write(arrangement.getStartDate().format(format));
        usingRobot.press(KeyCode.ENTER);
        usingRobot.release(KeyCode.ENTER);
        usingRobot.clickOn("#endDateInput").write(arrangement.getEndDate().format(format));
        usingRobot.press(KeyCode.ENTER);
        usingRobot.release(KeyCode.ENTER);

        usingRobot.clickOn("#descriptionInput").write(arrangement.getDescription());
    }
}

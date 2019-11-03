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
    private String[] arrangementsData;
    private final String[] lookupFields = new String[]{
            "#arrangementName", "#arrangementSport",
            "#arrangementAdress", "#arrangementDate",
            "#arrangementParticipants", "#arrangementGorI", "#arrangementDescription"};

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

        arrangementsData = ((Arrangement)listView.getSelectionModel().getSelectedItem()).getAllDataAsStringArr();
        assertFieldsCorrespondToNewArrangement(robot);

        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        testAlterArrangement(robot);
        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        robot.clickOn("#deleteBtn");
    }

    void testAlterArrangement(@NotNull FxRobot usingRobot){
        ListView listView = usingRobot.lookup("#listview").queryAs(ListView.class);
        listView.getSelectionModel().selectLast();
        usingRobot.clickOn("#editBtn");
        fillOutForm(usingRobot);
        usingRobot.clickOn("#saveBtn");

        arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        arrangementsData = arrangement.getAllDataAsStringArr();
        assertFieldsCorrespondToNewArrangement(usingRobot);
    }


    private void assertFieldsCorrespondToNewArrangement(@NotNull FxRobot robot) {
        for(int i = 0; i < arrangementsData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementsData[i]);
        }
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


    //test sorting works
}

package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import no.hiof.set.gruppe.tests.GUI.utils.ClickingUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class OrganizerController extends MainJavaFXTest{
    static{
        Locale.setDefault(Locale.US);
    }
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
            "#arrangementAddress", "#arrangementDate",
            "#arrangementParticipants", "#arrangementGorI", "#arrangementDescription"};

    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ORGANIZER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void assert_new_arrangement_is_created(@NotNull FxRobot robot) {
        ListView listView = robot.lookup("#listView").queryAs(ListView.class);
        robot.clickOn("#newArrangementBtn");
        fill_out_form(robot);
        robot.clickOn("#saveBtn");

        arrangementsData = ((Arrangement)listView.getSelectionModel().getSelectedItem()).getAllDataAsStringArr();
        assert_fields_correspond_to_new_arrangement(robot);

        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        assert_arrangement_is_altered(robot);
        assertEquals(listView.getSelectionModel().getSelectedItem(), arrangement);
        robot.clickOn("#deleteBtn");
    }

    void assert_arrangement_is_altered(@NotNull FxRobot usingRobot){
        ListView listView = usingRobot.lookup("#listView").queryAs(ListView.class);
        listView.getSelectionModel().selectLast();
        usingRobot.clickOn("#editBtn");
        fill_out_form(usingRobot);
        usingRobot.clickOn("#saveBtn");

        arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        arrangementsData = arrangement.getAllDataAsStringArr();
        assert_fields_correspond_to_new_arrangement(usingRobot);
    }


    private void assert_fields_correspond_to_new_arrangement(@NotNull FxRobot robot) {
        for(int i = 0; i < arrangementsData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementsData[i]);
        }
    }

    private void fill_out_form(@NotNull FxRobot usingRobot) {
        usingRobot.clickOn("#nameInput").write(arrangement.getName());
        usingRobot.clickOn("#participantsInput").write(String.valueOf(arrangement.getParticipants()));
        usingRobot.clickOn("#adressInput").write(arrangement.getAddress());

        String[] datePickerNodes = {"#startDateInput", "#endDateInput"};
        ClickingUtils.clickOnDatePicker(datePickerNodes[0], usingRobot, arrangement.getStartDate());
        ClickingUtils.clickOnDatePicker(datePickerNodes[1], usingRobot, arrangement.getEndDate());

        usingRobot.clickOn("#descriptionInput").write(arrangement.getDescription());
    }
}

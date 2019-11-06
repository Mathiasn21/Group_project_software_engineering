package no.hiof.set.gruppe.tests.controller;

import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

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
        usingRobot.clickOn("#nameInput").write(arrangement.getName());
        usingRobot.clickOn("#participantsInput").write(String.valueOf(arrangement.getParticipants()));
        usingRobot.clickOn("#adressInput").write(arrangement.getAddress());

        String[] datePickerNodes = {"#startDateInput", "#endDateInput"};
        clickOnDatePicker(datePickerNodes[0], usingRobot, arrangement.getStartDate());
        clickOnDatePicker(datePickerNodes[1], usingRobot, arrangement.getEndDate());

        usingRobot.clickOn("#descriptionInput").write(arrangement.getDescription());
    }

    // --------------------------------------------------//
    //                2.Helper methods                   //
    // --------------------------------------------------//
    //DO NOT REMOVE THIS!!!!!!!!!!!!!!!!!!
    private void clickOnDatePicker(@NotNull String datePickerNode, @NotNull FxRobot robot, @NotNull LocalDate dateToClick) {
        DatePicker datepicker = robot.lookup(datePickerNode).queryAs(DatePicker.class);

        //clicks on the datepicker arrow button
        Set<Node> arowArr = datepicker.lookupAll(".date-picker:hover > .arrow-button");
        Node[] nodeArr = arowArr.toArray(Node[]::new);
        robot.clickOn(nodeArr[0]);

        clickToGetCorrectMonthYear(robot, dateToClick);

        NodeQuery nodeQuery = robot.lookup(".day-cell");
        Set<Node> dateCellSet = nodeQuery.queryAll();
        Node[] nodes = dateCellSet.toArray(Node[]::new);

        for (Node n : nodes) {
            LocalDate date = ((DateCell) n).getItem();
            if (date.isEqual(dateToClick)) {
                robot.clickOn(n);
                break;
            }
        }
    }

    private void clickToGetCorrectMonthYear(@NotNull FxRobot robot, @NotNull LocalDate dateToClick) {
        //Test finding the right year arrow
        NodeQuery rightArrowNodes = robot.lookup(".date-picker-popup > * > .spinner > .button:pressed > .right-arrow");
        NodeQuery leftArrowNodes = robot.lookup(".date-picker-popup > * > .spinner > .button:pressed > .left-arrow");

        Set<Node> rightArrowSet = rightArrowNodes.queryAll();
        Set<Node> leftArrowSet = leftArrowNodes.queryAll();

        Node[] rightNodeArr = rightArrowSet.toArray(Node[]::new);
        Node[] leftNodeArr = leftArrowSet.toArray(Node[]::new);

        //testing querying the actual date and month
        NodeQuery labelNodeQ = robot.lookup(".date-picker-popup > * > .spinner > .label");
        Set<Node> labelSet = labelNodeQ.queryAll();
        Node[] labelArr = labelSet.toArray(Node[]::new);

        String thing = ((Label)labelArr[0]).getText();
        Year year = Year.parse(((Label)labelArr[1]).getText());
        System.out.println(thing);
        Month month = Month.valueOf(getEnglishMonthName(thing));//uppercase

        int i = Math.abs(dateToClick.getMonthValue() - month.getValue());
        while(i > 0){
            robot.clickOn(dateToClick.getMonthValue() - month.getValue() <= 0 ? leftNodeArr[0] : rightNodeArr[0]);
            i--;
        }

        int j = Math.abs(dateToClick.getYear() - year.getValue());
        while(j > 0){
            robot.clickOn(dateToClick.getYear() - year.getValue() <= 0 ? leftNodeArr[1] : rightNodeArr[1]);
            j--;
        }
    }

    @Nullable
    private String getEnglishMonthName(String thing) {
        String[] englishMonthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] norwegianMonthNames = {"januar", "februar", "mars", "april", "mai", "juni", "juli", "august", "september", "oktober", "november", "desember"};
        for (int i = 0; i < englishMonthNames.length; i++) {
            String str = norwegianMonthNames[i];
            if (str.toLowerCase().equals(thing.toLowerCase())) {
                return englishMonthNames[i];
            }
        }
        return null;
    }
}

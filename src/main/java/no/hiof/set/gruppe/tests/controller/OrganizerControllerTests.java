package no.hiof.set.gruppe.tests.controller;

import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import org.testfx.service.query.NodeQuery;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class OrganizerControllerTests extends MainJavaFXTest{
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
        Set<Node> arrowArr = datepicker.lookupAll(".date-picker:hover > .arrow-button");
        Node[] nodeArr = arrowArr.toArray(Node[]::new);
        robot.clickOn(nodeArr[0]);

        clickToGetCorrectMonthYear(robot, dateToClick);

        Node[] nodes = getNodesAsArr(robot, ".day-cell");
        for (Node n : nodes) {
            LocalDate date = ((DateCell) n).getItem();
            if (date.isEqual(dateToClick)) {
                robot.clickOn(n);
                break;
            }
        }
    }

    private void clickToGetCorrectMonthYear(@NotNull FxRobot robot, @NotNull LocalDate dateToClick) {
        //Query for the spinner arrows. The arrows that change the year and the month
        Node[] rightNodeArr = getNodesAsArr(robot, ".date-picker-popup > * > .spinner > .button:pressed > .right-arrow");
        Node[] leftNodeArr = getNodesAsArr(robot, ".date-picker-popup > * > .spinner > .button:pressed > .left-arrow");

        //Get month and year label nodes
        Node[] labelArr = getNodesAsArr(robot, ".date-picker-popup > * > .spinner > .label");

        Year year = Year.parse(((Label)labelArr[1]).getText());
        Month month = Month.valueOf(((Label)labelArr[0]).getText().toUpperCase());

        ClickToGetSpinnerToShowCorrectText(robot, rightNodeArr[0], leftNodeArr[0], dateToClick.getMonthValue(), month.getValue());
        ClickToGetSpinnerToShowCorrectText(robot, rightNodeArr[1], leftNodeArr[1], dateToClick.getYear(), year.getValue());
    }

    private Node[] getNodesAsArr(@NotNull FxRobot robot, String s) {
        NodeQuery leftArrowNodes = robot.lookup(s);
        Set<Node> leftArrowSet = leftArrowNodes.queryAll();
        return leftArrowSet.toArray(Node[]::new);
    }

    private void ClickToGetSpinnerToShowCorrectText(@NotNull FxRobot robot, Node rightArrowNode, Node leftArrowNode, int x, int y) {
        int i = Math.abs(x - y);
        while (i > 0) {
            robot.clickOn(x - y <= 0 ? leftArrowNode : rightArrowNode);
            i--;
        }
    }
}

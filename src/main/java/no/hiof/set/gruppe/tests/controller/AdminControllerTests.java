package no.hiof.set.gruppe.tests.controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@ExtendWith(ApplicationExtension.class)
public class AdminControllerTests extends MainJavaFXTest{

    private Scene scene;
    private Arrangement currentTestArrangement;
    @Start
    public void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ADMIN.getViewName());
        mainJavaFXTest.start(stage);
        this.scene = stage.getScene();
    }

    @Test
    void selectArrangementAndEdit(@NotNull FxRobot robot){
        selectArrangementTest(robot);
        robot.clickOn("#edit");
        editArrangementTest(robot);
    }

    //DO NOT REMOVE THIS!!!!!!!!!!!!!!!!!!
    /*
        String[] datePickerNodes = {"#startDateInput", "#endDateInput"};
        LocalDate afterDate = LocalDate.now();
        for (String datePickerNode : datePickerNodes) {
            afterDate = clickOnDatePicker(datePickerNode, robot, afterDate);
        }

    private LocalDate clickOnDatePicker(@NotNull String datePickerNode, @NotNull FxRobot robot, @NotNull LocalDate afterDate) {
        DatePicker datepicker = robot.lookup(datePickerNode).queryAs(DatePicker.class);

        //clicks on the datepicker arrow button
        Set<Node> arowArr = datepicker.lookupAll(".date-picker:hover > .arrow-button");
        Node[] nodeArr = arowArr.toArray(Node[]::new);
        robot.clickOn(nodeArr[0]);

        WaitForAsyncUtils.waitForFxEvents( );
        NodeQuery nodeQuery = robot.lookup(".day-cell");
        Set<Node> dateCellSet = nodeQuery.queryAll();
        Node[] nodes = dateCellSet.toArray(Node[]::new);

        for (Node n : nodes) {
            LocalDate date = ((DateCell) n).getItem();
            if (date.isAfter(afterDate)) {
                afterDate = date;
                robot.clickOn(n);
                break;
            }
        }
        return afterDate;
    }*/

    private void selectArrangementTest(@NotNull FxRobot robot) {
        String[] lookupFields = {
                "#arrangementName", "#arrangementSport", "#arrangementAdress",
                "#arrangementDate", "#arrangementParticipants", "#arrangementGorI", "#arrangementDescription"
        };

        String viewNode = "#arrangementListView";
        ListView listView = getListView(robot, viewNode);
        clickOnListView(robot, listView);
        currentTestArrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        assertFieldsIsClickedArrangement(robot, lookupFields, currentTestArrangement.getAllDataAsStringArr());
    }

    private void editArrangementTest(@NotNull FxRobot robot) {
        String[] lookupFields = {
                "#nameInput", "#adressInput", "#participantsInput", "#descriptionInput"
        };
        assertFieldsIsClickedArrangement2(robot, lookupFields, currentTestArrangement.getAllStringDataArrRaw());
        robot.clickOn("#saveBtn");
    }

    private void assertFieldsIsClickedArrangement(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementData[i]);
        }
    }

    private void assertFieldsIsClickedArrangement2(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(TextInputControl.class)).hasText(arrangementData[i]);
        }
    }

    private void clickOnListView(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    private ListView getListView(@NotNull FxRobot robot, String node) {
        return robot.lookup(node).queryAs(ListView.class);
    }
    //test sorting functionality
}

package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.infrastructure.repository.Repository;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.tests.GUI.utils.ClickingUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@ExtendWith(ApplicationExtension.class)
class AdminController extends MainJavaFXTest{

    private Arrangement currentTestArrangement;
    private final IRepository repository = new Repository();

    /**
     * @param stage {@link Stage}
     * @throws IOException
     */
    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ADMIN.getViewName());
        mainJavaFXTest.start(stage);
    }

    /**
     * @param robot
     * @throws IllegalDataAccess
     * @throws DataFormatException
     */
    @Test
    void selectArrangementAndEdit(@NotNull FxRobot robot) throws IllegalDataAccess, DataFormatException {
        selectArrangementTest(robot);
        robot.clickOn("#edit");
        String[] datePickerNodes = {"#startDateInput", "#endDateInput"};
        LocalDate date = LocalDate.now();
        for (String datePickerNode : datePickerNodes) {
            date = date.plusDays(1);
            ClickingUtils.clickOnDatePicker(datePickerNode, robot, date);
        }
        editArrangementTest(robot);
        deleteAddedArrangement(robot);
    }

    /**
     * @param robot {@link FxRobot}
     * @throws IllegalDataAccess
     * @throws DataFormatException
     */
    private void deleteAddedArrangement(@NotNull FxRobot robot) throws IllegalDataAccess, DataFormatException {
        ListView listView = getListView(robot, "#arrangementListView");
        Arrangement arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        robot.clickOn("#delete");
        repository.insertData(arrangement, ProtoUser.ORGANIZER);
    }

    // --------------------------------------------------//
    //                2.Assertion methods                //
    // --------------------------------------------------//

    /**
     * @param robot {@link FxRobot}
     * @param lookupFields {@link String}
     * @param arrangementData {@link String}
     */
    private void assertFieldsIsClickedArrangement(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementData[i]);
        }
    }

    /**
     * @param robot
     * @param lookupFields
     * @param arrangementData
     */
    private void assertFieldsIsClickedArrangement2(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(TextInputControl.class)).hasText(arrangementData[i]);
        }
    }


    // --------------------------------------------------//
    //                2.Helper methods                   //
    // --------------------------------------------------//
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

    /**
     * @param robot robot
     * @param listView listView
     */
    private void clickOnListView(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    /**
     * @param robot robot
     * @param node node
     * @return ListView
     */
    private ListView getListView(@NotNull FxRobot robot, String node) {
        return robot.lookup(node).queryAs(ListView.class);
    }
}
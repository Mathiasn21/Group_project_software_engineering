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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(ApplicationExtension.class)
class AdminController extends MainJavaFXTest{

    private Arrangement currentTestArrangement;
    private static final IRepository repository = new Repository();

    /**
     * @param stage {@link Stage}
     * @throws IOException IOException
     */
    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.ADMIN.getViewName());
        mainJavaFXTest.start(stage);
    }

    /**
     * @param robot FxRobot
     */
    @Test
    void select_Arrangement_AndEdit(@NotNull FxRobot robot){
        select_ArrangementTest(robot);
        robot.clickOn("#edit");
        String[] datePickerNodes = {"#startDateInput", "#endDateInput"};
        LocalDate date = LocalDate.now();
        for (String datePickerNode : datePickerNodes) {
            date = date.plusDays(1);
            ClickingUtils.clickOnDatePicker(datePickerNode, robot, date);
        }
        assert_Arrangement_isEdited(robot);
        delete_PreviouslyAdded_Arrangement(robot);
    }

    /**
     * @param robot {@link FxRobot}
     */
    private void delete_PreviouslyAdded_Arrangement(@NotNull FxRobot robot) {
        ListView listView = get_ListView(robot, "#arrangementListView");
        Arrangement arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        robot.clickOn("#delete");
        assertDoesNotThrow(() -> repository.insertData(arrangement, ProtoUser.ORGANIZER));
    }

    // --------------------------------------------------//
    //                2.Assertion methods                //
    // --------------------------------------------------//
    /**
     * @param robot {@link FxRobot}
     * @param lookupFields {@link String}
     * @param arrangementData {@link String}
     */
    private void assert_Fields_IsClicked_InArrangement(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementData[i]);
        }
    }

    /**
     * @param robot
     * @param lookupFields
     * @param arrangementData
     */
    private void assert_Fields_IsClicked_inArrangement2(@NotNull FxRobot robot, String[] lookupFields, @NotNull String[] arrangementData) {
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(TextInputControl.class)).hasText(arrangementData[i]);
        }
    }


    // --------------------------------------------------//
    //                2.Helper methods                   //
    // --------------------------------------------------//
    private void select_ArrangementTest(@NotNull FxRobot robot) {
        String[] lookupFields = {
                "#arrangementName", "#arrangementSport", "#arrangementAdress",
                "#arrangementDate", "#arrangementParticipants", "#arrangementGorI", "#arrangementDescription"
        };

        String viewNode = "#arrangementListView";
        ListView listView = get_ListView(robot, viewNode);
        assert_ListView_isClicked(robot, listView);
        currentTestArrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
        assert_Fields_IsClicked_InArrangement(robot, lookupFields, currentTestArrangement.getAllDataAsStringArr());
    }

    private void assert_Arrangement_isEdited(@NotNull FxRobot robot) {
        String[] lookupFields = {
                "#nameInput", "#adressInput", "#participantsInput", "#descriptionInput"
        };
        assert_Fields_IsClicked_inArrangement2(robot, lookupFields, currentTestArrangement.getAllStringDataArrRaw());
        robot.clickOn("#saveBtn");
    }

    /**
     * @param robot robot
     * @param listView listView
     */
    private void assert_ListView_isClicked(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    /**
     * @param robot robot
     * @param node node
     * @return ListView
     */
    private ListView get_ListView(@NotNull FxRobot robot, String node) {
        return robot.lookup(node).queryAs(ListView.class);
    }
}
package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.Node;
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

import java.io.IOException;
import java.util.Set;

@ExtendWith(ApplicationExtension.class)
class UserControllerTests extends MainJavaFXTest{
    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.USER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void addAndRemoveUserToFromArrangement(@NotNull FxRobot robot){
        Arrangement arrangement;
        String[] nodes = {"#availableArrangementsListView", "#myArrangementsView"};
        String[] nodeBtns = {"#joinBtn", "#leaveBtn"};
        for(int i = 0; i < nodes.length; i++){
            ListView listView = getListView(robot, nodes[i]);
            clickOnListView(robot, listView);
            arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
            assertFieldsIsClickedArrangement(robot, arrangement);
            robot.clickOn(nodeBtns[i]);
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

    private void assertFieldsIsClickedArrangement(@NotNull FxRobot robot, @NotNull Arrangement arrangement) {
        String[] arrangementData = arrangement.getAllDataAsStringArr();
        String[] lookupFields = {
                "#arrangementTitle", "#arrangementSport", "#arrangementAddress",
                "#arrangementDate", "#arrangementParticipants", "#arrangementGroup", "#arrangementDescription"
        };
        for(int i = 0; i < arrangementData.length; i++){
            Assertions.assertThat(robot.lookup(lookupFields[i]).queryAs(Text.class)).hasText(arrangementData[i]);
        }
    }

    //test sorting functionality
}

package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
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
class UserController extends MainJavaFXTest{
    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView(ProtoUser.USER.getViewName());
        mainJavaFXTest.start(stage);
    }

    @Test
    void add_and_remove_user_on_arrangement(@NotNull FxRobot robot){
        Arrangement arrangement;
        String[] nodes = {"#availableArrangementsListView", "#myArrangementsView"};
        String[] nodeBtns = {"#joinBtn", "#leaveBtn"};
        for(int i = 0; i < nodes.length; i++){
            ListView listView = get_ListView(robot, nodes[i]);
            click_on_listView(robot, listView);
            arrangement = (Arrangement) listView.getSelectionModel().getSelectedItem();
            assert_fields_is_clicked_arrangement(robot, arrangement);
            robot.clickOn(nodeBtns[i]);
        }
    }

    /**
     * @param robot {@link FxRobot}
     * @param listView {@link ListView}
     */
    private void click_on_listView(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    /**
     * @param robot {@link FxRobot}
     * @param node {@link String}
     * @return
     */
    private ListView get_ListView(@NotNull FxRobot robot, String node) {
        return robot.lookup(node).queryAs(ListView.class);
    }

    /**
     * @param robot {@link FxRobot}
     * @param arrangement {@link Arrangement}
     */
    private void assert_fields_is_clicked_arrangement(@NotNull FxRobot robot, @NotNull Arrangement arrangement) {
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

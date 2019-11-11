package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.Group;
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
class GroupControllerTests extends MainJavaFXTest{

    @Start
    void start(@NotNull Stage stage) throws IOException {
        MainJavaFXTest mainJavaFXTest = new MainJavaFXTest();
        mainJavaFXTest.setStartView("Groups.fxml");
        mainJavaFXTest.start(stage);
    }

    @Test
    void selectAndEditGroup(FxRobot robot){
        ListView groupListView = getNode(robot, "#groupsListview", ListView.class);
        clickOnListView(robot, groupListView);
        Group group = (Group)groupListView.getSelectionModel().getSelectedItem();
        Assertions.assertThat(getNode(robot, "#groupName", Text.class)).hasText(group.getName());
        Assertions.assertThat(getNode(robot, "#members", Text.class)).hasText(group.get);
        robot.clickOn("#editBtn");
    }

    @Test
    void selectAndCreateNewGroup(FxRobot usingRobot){


    }

    @Test
    void selectAndCancelCreateNewGroup(FxRobot usingRobot){


    }


    private void clickOnListView(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    private <T extends Node> T getNode(FxRobot robot , String node, Class<T> queryAs){ return robot.lookup(node).queryAs(queryAs); }
}

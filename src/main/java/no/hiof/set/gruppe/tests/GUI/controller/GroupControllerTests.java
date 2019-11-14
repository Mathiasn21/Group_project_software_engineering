package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class GroupControllerTests extends MainJavaFXTest{

    /**
     * @param stage {@link Stage}
     * @throws IOException
     */
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
        Assertions.assertThat(getNode(robot, "#members", Text.class)).hasText(group.getMembersAsPrettyString());
        robot.clickOn("#editBtn");

        Assertions.assertThat(getNode(robot, "#inputName", TextField.class)).hasText(group.getName());
        ListView availableMembers = getNode(robot, "#availableMembers", ListView.class);
        clickOnListView(robot, availableMembers);

        DummyUsers member = (DummyUsers) availableMembers.getSelectionModel().getSelectedItem();
        robot.clickOn("#addMember");
        assertFalse(availableMembers.getItems().contains(member));

        ListView chosenMembers = getNode(robot, "#chosenMembers", ListView.class);
        assertTrue(chosenMembers.getItems().contains(member));
        clickOnListView(robot, chosenMembers);
        robot.clickOn("#removeMember");

        robot.clickOn("#save");
    }

    /**
     * @param robot {@link FxRobot}
     * @param listView {@link ListView}
     */
    private void clickOnListView(@NotNull FxRobot robot, @NotNull ListView listView) {
        Set<Node> nodeList = listView.lookupAll(".list-cell");
        Node[] arr = nodeList.toArray(Node[]::new);
        robot.clickOn(arr[0]);
    }

    /**
     * @param robot {@link FxRobot}
     * @param node {@link String}
     * @param queryAs {@link Class<T>}
     * @param <T> {@link Node}
     * @return <T
     */
    private <T extends Node> T getNode(FxRobot robot , String node, Class<T> queryAs){ return robot.lookup(node).queryAs(queryAs); }
}

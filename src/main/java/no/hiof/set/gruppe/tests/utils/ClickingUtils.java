package no.hiof.set.gruppe.tests.utils;

import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.testfx.api.FxRobot;
import org.testfx.service.query.NodeQuery;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

public class ClickingUtils {
    public static void clickOnDatePicker(@NotNull String datePickerNode, @NotNull FxRobot robot, @NotNull LocalDate dateToClick) {
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

    private static void clickToGetCorrectMonthYear(@NotNull FxRobot robot, @NotNull LocalDate dateToClick) {
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

    private static Node[] getNodesAsArr(@NotNull FxRobot robot, String s) {
        NodeQuery leftArrowNodes = robot.lookup(s);
        Set<Node> leftArrowSet = leftArrowNodes.queryAll();
        return leftArrowSet.toArray(Node[]::new);
    }

    private static void ClickToGetSpinnerToShowCorrectText(@NotNull FxRobot robot, Node rightArrowNode, Node leftArrowNode, int x, int y) {
        int i = Math.abs(x - y);
        while (i > 0) {
            robot.clickOn(x - y <= 0 ? leftArrowNode : rightArrowNode);
            i--;
        }
    }
}
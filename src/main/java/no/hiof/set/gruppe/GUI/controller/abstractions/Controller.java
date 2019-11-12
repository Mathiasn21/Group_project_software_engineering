package no.hiof.set.gruppe.GUI.controller.abstractions;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.model.IGetAllDataStringArr;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * This class is the main setup for all other controllers and their logic.
 * Meaning all other controllers must inherit and implement functionality described here.
 */
public abstract class Controller implements IController, Initializable {
    private MainJavaFX mainController;

    /**
     * Guaranteed communication with the main controller.
     * @param mainController {@link MainJavaFX}
     */
    @Override
    public void setMainController(MainJavaFX mainController){
        this.mainController = mainController;
    }

    MainJavaFX getMainController() { return mainController; }

    @Override
    public void createNewView(Controller controller) {
        boolean errorOccured = true;
        ErrorExceptionHandler err = null;
        Throwable thrown = null;

        try {
            mainController.setupWindow(controller);
            errorOccured = false;
        } catch (IOException e) {
            err = ErrorExceptionHandler.ERROR_LOAD_RESOURCE;
            thrown = e;

        }finally {
            try {
                if (errorOccured && (err != null)) ErrorExceptionHandler.createLogWithDetails(err, thrown);
            } catch (Exception e) {
                Controller.createAlert(ErrorExceptionHandler.ERROR_LOGGING_ERROR);
            }
        }
    }

    /**
     * Creates a alert box for the user, including the given error.
     * @param errMsg ErrorExceptionHandler
     */
    public static void createAlert(@NotNull ErrorExceptionHandler errMsg){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("ERROR");
        alert.setContentText(errMsg.CODE + " " + errMsg.ERROR_MSG);
        alert.showAndWait();
    }

    protected void clearFields(Text... textNodes){ for(Text text : textNodes)text.setText(""); }

    protected static void setFieldsWithDataFromObject(IGetAllDataStringArr object, Text[] nodes){
        if(object == null || nodes == null )return;
        String[] data = object.getAllDataAsStringArr();
        for(int i = 0; i < nodes.length; i++)nodes[i].setText(data[i]);
    }

    protected void closeWindow(@NotNull Button b) { ((Stage)b.getScene().getWindow()).close(); }

    protected void colorizeText(boolean tf, Text...t){
        Color color = tf ? Color.BLACK : Color.GREY;
        for (Text text : t)text.setFill(color);
    }
}

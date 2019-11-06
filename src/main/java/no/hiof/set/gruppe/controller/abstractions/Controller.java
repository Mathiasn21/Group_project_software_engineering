package no.hiof.set.gruppe.controller.abstractions;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.model.Arrangement;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the main setup for all other controllers and their logic.
 * Meaning all other controllers must inherit and implement functionality described here.
 */
public abstract class Controller implements IControllerDataTransfer, Initializable {
    private MainJavaFX mainController;

    /**
     * Guaranteed communication with the main controller.
     * @param mainController {@link MainJavaFX}
     */
    @Override
    public void setMainController(MainJavaFX mainController){
        this.mainController = mainController;
    }
    @Override
    public void createNewView(Controller controller) {
        boolean errorOccured = true;
        ErrorExceptionHandler err = null;
        Throwable thrown = null;

        //Handling and logging error + exceptions
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

    protected void createNewView(IControllerDataTransfer controller, Object object) {
        mainController.setupWindow(controller, object);
    }

    protected MainJavaFX getMainController() {
        return mainController;
    }

    public boolean hasNewObject(){
        return false;
    }

    @Override
    public void updateView(){}

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

    protected ArrayList<Text> viewFields(Text name, Text sport, Text adress, Text date, Text participants, Text groups, Text description){
        Text[] t = {name, sport, adress, date, participants, groups, description};
        return new ArrayList<>(Arrays.asList(t));
    }

    protected ArrayList<String>arrangementData(@NotNull Arrangement a){
        String[] s = {a.getName(), a.getSport(), a.getAddress(), dateString(a), Integer.toString(a.getParticipants()), groupsOrIndividuals(a), a.getDescription()};
        return new ArrayList<>(Arrays.asList(s));
    }

    @NotNull
    private String groupsOrIndividuals(@NotNull Arrangement arrangement){
        return arrangement.isGroup() ? "Lagkonkurranse" : "Individuell konkurranse";
    }

    @NotNull
    private String dateString(@NotNull Arrangement a){
        return a.getStartDate().toString() + " til " + a.getEndDate().toString();
    }

    protected void closeWindow(@NotNull Button b) {
        ((Stage)b.getScene().getWindow()).close();
    }

    protected void colorizeText(boolean tf, Text...t){
        if(tf){
            for (Text text : t)
                text.setFill(Color.BLACK);
        }
        else{
            for (Text text : t)
                text.setFill(Color.GREY);
        }
    }
}

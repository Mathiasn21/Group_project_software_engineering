package no.hiof.set.gruppe.controller.abstractions;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.MainJavaFX;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

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
            err = ErrorExceptionHandler.ERROR_WRONG_DATA_OBJECT;
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

    /**
     * Default logic for closing of a view
     */
    @Override
    public void onCloseStoreInformation(){}
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
}

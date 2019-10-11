package no.hiof.set.gruppe.controller;

import javafx.fxml.Initializable;
import no.hiof.set.gruppe.MainJavaFX;
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
        try {
            mainController.setupWindow(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createNewView(IControllerDataTransfer<Object> controller, Object object) {
        mainController.setupWindow(controller, object);
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

}

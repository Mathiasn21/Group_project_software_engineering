package no.hiof.set.gruppe.GUI.controller.abstractions;

import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.MainJavaFX;

/**
 * A contract that secures interaction with the {@link MainJavaFX} Controller.
 * As well as other behaviour pertaining to a controller.
 */
public interface IController {
    /**
     * @param mainController {@link MainJavaFX}
     */
    void setMainController(MainJavaFX mainController);

    /**
     * @return {@link ViewInformation}
     */
    ViewInformation getViewInformation();

    /**
     * @param controller {@link Controller}
     */
    void createNewView(Controller controller);

}

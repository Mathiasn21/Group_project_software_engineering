package no.hiof.set.gruppe.GUI.controller.abstractions;

import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.GUI.controller.model.ViewInformation;

import java.io.IOException;

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
     * @param controller {@link Controller}
     */
    void createNewView(Controller controller);

    /**
     * @return {@link ViewInformation}
     */
    ViewInformation getViewInformation();

    void setTextColors(boolean tf);
}

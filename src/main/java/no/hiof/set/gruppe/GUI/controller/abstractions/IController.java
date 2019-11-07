package no.hiof.set.gruppe.GUI.controller.abstractions;

import no.hiof.set.gruppe.GUI.MainJavaFX;
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
     * @throws IOException exception {@link IOException}
     */
    void createNewView(Controller controller) throws IOException;

    /**
     * @return {@link ViewInformation}
     */
    ViewInformation getViewInformation();

    void setTextColors(boolean tf);
}

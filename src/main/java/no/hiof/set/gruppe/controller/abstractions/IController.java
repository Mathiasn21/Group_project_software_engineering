package no.hiof.set.gruppe.controller.abstractions;

import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.model.ViewInformation;

import java.io.IOException;

/**
 * A contract that secures interaction with the {@link MainJavaFX} Controller.
 * As well as other behaviour pertaining to a controller.
 */
public interface IController {
    /**
     * @return String
     */
    String getTitle();

    /**
     * @return String
     */
    String getName();

    /**
     * @param mainController {@link MainJavaFX}
     */
    void setMainController(MainJavaFX mainController);

    /**
     * @param controller {@link Controller}
     * @throws IOException exception {@link IOException}
     */
    void createNewView(Controller controller) throws IOException;
    void onCloseStoreInformation();

    /**
     * @return {@link ViewInformation}
     */
    ViewInformation getViewInformation();
}

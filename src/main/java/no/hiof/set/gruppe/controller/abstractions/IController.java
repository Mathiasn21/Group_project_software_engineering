package no.hiof.set.gruppe.controller.abstractions;

import no.hiof.set.gruppe.MainJavaFX;
import java.io.IOException;

/**
 * A contract that secures interaction with the {@link MainJavaFX} Controller.
 * As well as other behaviour pertaining to a controller.
 */
public interface IController {
    String getTitle();
    String getName();
    void setMainController(MainJavaFX mainController);
    void createNewView(Controller controller) throws IOException;
    void onCloseStoreInformation();
}

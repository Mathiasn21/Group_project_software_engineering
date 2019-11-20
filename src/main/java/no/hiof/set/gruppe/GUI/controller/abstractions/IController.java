package no.hiof.set.gruppe.GUI.controller.abstractions;

import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.core.interfaces.IRepository;

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
     * @param repository {@link IRepository}
     */
    void setRepository(IRepository repository);

    /**
     * @return IRepository {@link IRepository}
     */
    IRepository getRepository();

    /**
     * @return {@link ViewInformation}
     */
    ViewInformation getViewInformation();

    /**
     * @param controller {@link Controller}
     */
    void createNewView(Controller controller);
}

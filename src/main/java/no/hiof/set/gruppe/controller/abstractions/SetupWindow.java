package no.hiof.set.gruppe.controller.abstractions;

import java.io.IOException;

/**
 * Describes the contract in order to setup new windows.
 */
public interface SetupWindow {
    /**
     * @param controller {@link IController}
     * @throws IOException IOException {@link IOException}
     */
    void setupWindow(IController controller)throws IOException;
    /**
     * @param controller {@link IControllerDataTransfer}
     * @param object Object
     */
    void setupWindow(IControllerDataTransfer controller, Object object);
}

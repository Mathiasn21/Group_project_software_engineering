package no.hiof.set.gruppe.controller;

import java.io.IOException;

/**
 * Describes the contract in order to setup new windows.
 */
public interface SetupWindow {
    void setupWindow(IController controller)throws IOException;
    void setupWindow(IControllerDataTransfer controller, Object object);
}

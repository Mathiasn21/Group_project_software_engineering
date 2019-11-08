package no.hiof.set.gruppe.GUI.controller.abstractions;

import no.hiof.set.gruppe.exceptions.DataFormatException;

/**
 * Secures that transfer of data objects between controllers is done.
 */
public interface IControllerDataTransfer extends IController{
    /**
     * @return Object
     */
    Object getDataObject();
    /**
     * @return boolean
     */
    boolean hasNewObject();
    /**
     * @param controller Object
     * @throws DataFormatException DataFormatException {@link DataFormatException}
     */
    void setDataFields(Object controller) throws DataFormatException;
    void updateView();
}

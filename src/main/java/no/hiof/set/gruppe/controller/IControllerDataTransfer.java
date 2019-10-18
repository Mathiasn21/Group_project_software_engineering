package no.hiof.set.gruppe.controller;

import no.hiof.set.gruppe.Exceptions.DataFormatException;

public interface IControllerDataTransfer extends IController{
    Object getDataObject();
    boolean hasNewObject();
    void setDataFields(Object controller) throws DataFormatException;
    void updateView();
}

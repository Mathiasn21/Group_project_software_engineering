package no.hiof.set.gruppe.controller;

import no.hiof.set.gruppe.Exceptions.DataFormatException;

public interface IControllerDataTransfer<T> extends IController{
    T getDataObject();
    boolean hasNewObject();
    void setDataFields(T object) throws DataFormatException;
    void updateView();
}

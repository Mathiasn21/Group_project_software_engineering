package no.hiof.set.gruppe.core.interfaces;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;

import java.io.IOException;
import java.util.List;

public interface IHandleData {
    <T> List<T> queryAllDataGivenType(Class<T> tClassArr) throws IOException, DataFormatException;
    <T extends IBaseEntity> void storeDataGivenType(Class<T> aClass, List<? extends IBaseEntity> list)throws DataFormatException;
}
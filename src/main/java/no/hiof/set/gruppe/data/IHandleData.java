package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import java.io.IOException;
import java.util.List;

public interface IHandleData {
    <T> void storeDataGivenType(Class<T[]> tClass, T[] tArray) throws DataFormatException;
    <T> List<T> queryAllDataGivenType(Class<T[]> tClassArr) throws IOException, DataFormatException;
}
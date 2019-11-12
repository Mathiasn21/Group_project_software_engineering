package no.hiof.set.gruppe.GUI.model;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapper<T> {
    private static List<ObjectMapper> objectMappers = new ArrayList<>();
    private final Class<T> tClass;
    final String fileName;

    public ObjectMapper(Class<T> tClass, String fileName){
        this.fileName = fileName;
        this.tClass = tClass;
    }

    private Class<T> getTypeClass() {return tClass;}

    static void mutateObjectMapperList(ObjectMapper objectMapToFiles){objectMappers.add(objectMapToFiles);}

    static <T> ObjectMapper getCorrespondingMapperGivenType(Class<T[]> t) throws DataFormatException {
        for(ObjectMapper objectMapper : objectMappers)
            if (objectMapper.getTypeClass() == t.getComponentType()) return objectMapper;
        throw new DataFormatException();
    }
}

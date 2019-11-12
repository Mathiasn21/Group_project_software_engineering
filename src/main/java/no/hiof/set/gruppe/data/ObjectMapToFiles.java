package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import java.util.ArrayList;
import java.util.List;

class ObjectMapToFiles<T> {
    private static List<ObjectMapToFiles> objectMappers = new ArrayList<>();
    private final Class<T> tClass;
    final String fileName;

    ObjectMapToFiles(Class<T> tClass, String fileName){
        this.fileName = fileName;
        this.tClass = tClass;
    }

    private Class<T> getTypeClass() {return tClass;}

    static void mutateObjectMapperList(ObjectMapToFiles objectMapToFiles){objectMappers.add(objectMapToFiles);}

    static <T> ObjectMapToFiles getCorrespondingMapperGivenType(Class<T[]> t) throws DataFormatException {
        for(ObjectMapToFiles objectMapper : objectMappers)
            if (objectMapper.getTypeClass() == t.getComponentType()) return objectMapper;
        throw new DataFormatException();
    }
}
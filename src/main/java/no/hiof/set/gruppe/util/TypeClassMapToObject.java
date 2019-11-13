package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeClassMapToObject<T, V> {
    private static final List<TypeClassMapToObject> objectMappers = new ArrayList<>();
    private final Class<T> tClass;
    private final V object;

    public TypeClassMapToObject(Class<T> tClass, V object){
        this.object = object;
        this.tClass = tClass;
    }

    private Class<T> getTypeClass() {return tClass;}

    public V getObject() {return object;}

    public static void mutateObjectMapperList(TypeClassMapToObject ...typeClassMapToObject){objectMappers.addAll(Arrays.asList(typeClassMapToObject));}

    public static <T> TypeClassMapToObject getCorrespondingMapperGivenType(Class<T[]> t) throws DataFormatException {
        for(TypeClassMapToObject objectMapper : objectMappers)
            if (objectMapper.getTypeClass() == t.getComponentType()) return objectMapper;
        throw new DataFormatException();
    }
}
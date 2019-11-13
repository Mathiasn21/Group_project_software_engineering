package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.core.exceptions.DataFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TypeClassMapToFiles<T, V>{
    private static final List<TypeClassMapToFiles> objectMappers = new ArrayList<>();
    private final Class<T> tClass;
    private final V object;

    TypeClassMapToFiles(Class<T> tClass, V object){
        this.object = object;
        this.tClass = tClass;
    }

    private Class<T> getTypeClass() {return tClass;}

    V getObject() {return object;}

    static void mutateObjectMapperList(TypeClassMapToFiles... typeClassMapToObject){objectMappers.addAll(Arrays.asList(typeClassMapToObject));}

    static <T> TypeClassMapToFiles getCorrespondingMapperGivenType(Class<T> t) throws DataFormatException {
        for(TypeClassMapToFiles objectMapper : objectMappers)
            if (objectMapper.getTypeClass() == t.getComponentType()) return objectMapper;
        throw new DataFormatException();
    }}
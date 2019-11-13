package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.util.TypeClassMapToObject;

class TypeClassMapToFiles<T, V> extends TypeClassMapToObject {
    TypeClassMapToFiles(Class<T> tClass, V object){super(tClass, object);}
}
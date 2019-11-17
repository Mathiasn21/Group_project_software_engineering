package no.hiof.set.gruppe.core.infrastructure.factory;

import java.util.List;

public interface IFactory {
    <T> boolean canGenerateFromClass(Class<T> tClass);
    <T> T generateType(Class<T> tClass);
    <T> List<T> generateManyTypes(Class<T> tClass, int number);
}

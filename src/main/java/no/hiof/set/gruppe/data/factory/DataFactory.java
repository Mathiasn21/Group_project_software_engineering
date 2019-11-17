package no.hiof.set.gruppe.data.factory;

import com.google.api.client.util.ArrayMap;
import no.hiof.set.gruppe.data.factory.generators.GenArrangements;
import no.hiof.set.gruppe.data.factory.generators.GenData;
import no.hiof.set.gruppe.data.factory.generators.GenGroups;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.user.UserConnectedArrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataFactory implements IFactory{
    private static final Class<?>[] knownClasskeys = {Arrangement.class, UserConnectedArrangement.class, Group.class};
    private static final GenData[] knownClassGenerators = {new GenArrangements(), new GenGroups()};
    private static final Map<Class<?>, GenData<?>> classMappedToClass = new ArrayMap<>();
    static {
        for (int i = 0; i<knownClasskeys.length; i++)classMappedToClass.put(knownClasskeys[i], knownClassGenerators[i]);
    }

    @Override
    public <T> boolean canGenerateFromClass(Class<T> tClass) {return classMappedToClass.containsKey(tClass);}

    @Override
    @SuppressWarnings("unchecked")//Only predefined return values, it is therefore safe to cast
    public <T> T generateType(Class<T> tClass) {
        if(!canGenerateFromClass(tClass))return null;
        return (T) classMappedToClass.get(tClass).createData();
    }

    @Override
    @SuppressWarnings("unchecked")//Only predefined return values, it is therefore safe to cast
    public <T> List<T> generateManyTypes(Class<T> tClass, int number) {
        if(!canGenerateFromClass(tClass))return null;
        List<T> data = new ArrayList<>();
        GenData generator = classMappedToClass.get(tClass);

        for(int i = 0; i < number; i++) data.add((T) generator.createData());
        return data;
    }
}

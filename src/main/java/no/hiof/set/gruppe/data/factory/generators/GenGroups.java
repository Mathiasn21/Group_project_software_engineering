package no.hiof.set.gruppe.data.factory.generators;

import no.hiof.set.gruppe.model.Group;

public class GenGroups implements GenData {
    @Override
    public Group createData() {
        return new Group();
    }
}

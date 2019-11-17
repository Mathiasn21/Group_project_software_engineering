package no.hiof.set.gruppe.data.factory.generators;

import no.hiof.set.gruppe.model.Arrangement;

public class GenArrangements  implements GenData {
    @Override
    public Arrangement createData() {
        return new Arrangement();
    }
}

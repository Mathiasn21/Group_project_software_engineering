package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.model.Arrangement;
import java.util.Collection;

public interface IDataHandler {
    void storeArrangementsData(Collection<Arrangement> arrangement);
}

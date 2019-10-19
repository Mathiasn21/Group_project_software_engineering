package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;

import java.util.List;

public interface IDataHandler {
    void storeArrangementsData(List<Arrangement> arrangement, User user);
}

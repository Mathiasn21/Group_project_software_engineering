package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;

public interface IValidation {
    <V extends Arrangement>boolean isValidArrangement(V arrangement);
    <B extends User>boolean isValidUser(B user);
}

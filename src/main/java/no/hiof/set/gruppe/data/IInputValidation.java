package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.model.Arrangement;

public interface IInputValidation<T extends String> {
    <V extends Arrangement>boolean isValidArrangement(V arrangement);
}

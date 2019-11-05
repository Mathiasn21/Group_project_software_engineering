package no.hiof.set.gruppe.model.user;

import org.jetbrains.annotations.NotNull;

public interface ILoginInformation {
    @NotNull
    String getUserID();

    @NotNull
    String getPassHash();
}

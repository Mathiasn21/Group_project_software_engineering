package no.hiof.set.gruppe.core.interfaces;

import org.jetbrains.annotations.NotNull;

public interface ILoginInformation {
    @NotNull
    String getUserID();

    @NotNull
    String getPassHash();
}

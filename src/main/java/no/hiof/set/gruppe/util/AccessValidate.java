package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing methods for testing user
 * access rights on given object.
 */
public final class AccessValidate {

    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanModifyArrangement(@NotNull Arrangement arrangement, @NotNull ProtoUser protoUser){
        return (protoUser == ProtoUser.ORGANIZER && Repository.getUserArrangements(protoUser).contains(arrangement)) || protoUser == ProtoUser.ADMIN;
    }

    /**
     * @param protoUser {@link ProtoUser}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanCreateArrangement(@NotNull ProtoUser protoUser){
        return protoUser == ProtoUser.ORGANIZER || protoUser == ProtoUser.ADMIN;
    }
}

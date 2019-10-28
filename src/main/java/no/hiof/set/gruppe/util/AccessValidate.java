package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing methods for testing user
 * access rights on given object.
 */
public final class AccessValidate {

    /**
     * @param arrangement {@link Arrangement}
     * @param user {@link User}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanModifyArrangement(@NotNull Arrangement arrangement, @NotNull User user){
        return (user == User.ORGANIZER && Repository.getUserArrangements(user).contains(arrangement)) || user == User.ADMIN;
    }

    /**
     * @param user {@link User}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanCreateArrangement(@NotNull User user){
        return user == User.ORGANIZER || user == User.ADMIN;
    }
}

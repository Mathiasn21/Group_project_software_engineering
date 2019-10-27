package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AccessValidate {

    @Contract(pure = true)
    public static boolean userCanModifyArrangement(@NotNull Arrangement arrangement, @NotNull User user){
        return (user == User.ORGANIZER && DataHandler.getUserArrangements(user).contains(arrangement)) || user == User.ADMIN;
    }

    @Contract(pure = true)
    public static boolean userCanCreateArrangement(@NotNull User user){
        return user == User.ORGANIZER || user == User.ADMIN;
    }
}

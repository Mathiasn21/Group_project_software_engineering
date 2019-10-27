package no.hiof.set.gruppe.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Information about the view. Mainly location and title
 */
public final class ViewInformation {
    public final String viewName;
    public final String viewTitle;

    /**
     * @param viewName String
     * @param viewTitle String
     */
    @Contract(pure = true)
    public ViewInformation(@NotNull String viewName, @NotNull String viewTitle){
        this.viewName = viewName;
        this.viewTitle = viewTitle;
    }
}

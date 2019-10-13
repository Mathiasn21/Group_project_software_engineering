package no.hiof.set.gruppe.model;

public enum User implements ILoginValidation{


    ADMIN("Admin", "Password1", "Admin.fxml"),
    USER("User", "Password2", "User.fxml"),
    ORGANIZER("Organizer", "Password3", "Organizer.fxml");

    String user;
    String password;
    String viewName;
    User(String user, String password, String viewName){
        this.user = user;
        this.password = password;
        this.viewName = viewName;
    }

    public String getName() {return user;}
    public String getPass() {return password;}
    public String getViewName() {return viewName;}

    public static boolean isValidUser(String userName, String pass) {
        User[] users = values();
        for (User user : users) if(user.password.equals(pass) && user.user.equals(userName))return true;
        return false;
    }
    public static User getUser(String userName){
        for(User u: values()) if(u.user.equals(userName))return u;
        return null;
    }
}

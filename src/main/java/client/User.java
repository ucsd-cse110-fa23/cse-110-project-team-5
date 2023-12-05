package client;

import java.util.prefs.Preferences;

public class User {
    static String username;
    static String password;
    private static final Preferences prefs = Preferences.userNodeForPackage(User.class);

    User(String username, String password){
        User.username = username;
        User.password = password;
    }

    public static String getUsername(){
        return username;
    }

    public static String getPassword(){
        return password;
    }

    // Save user login state
    public static void saveLoginState(boolean rememberMe) {
        prefs.putBoolean("REMEMBER_ME", rememberMe);
        if (rememberMe) {
            prefs.put("USERNAME", username);
            prefs.put("PASSWORD", password);
        } else {
            prefs.remove("USERNAME");
            prefs.remove("PASSWORD");
        }
    }

    // Check if the user has chosen to be remembered
    public static boolean isRemembered() {
        return prefs.getBoolean("REMEMBER_ME", false);
    }

    // Retrieve saved username
    public static String getSavedUsername() {
        return prefs.get("USERNAME", "");
    }

    // Retrieve saved password (not recommended for real applications due to security concerns)
    public static String getSavedPassword() {
        return prefs.get("PASSWORD", "");
    }
}

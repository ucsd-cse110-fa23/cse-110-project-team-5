package mock;

import java.util.prefs.Preferences;

import client.User;

public class MockUser {
    String mockusername;
    String mockpassword;
    private static final Preferences prefs = Preferences.userNodeForPackage(User.class);

    public MockUser(String mockusername, String mockpassword){
        this.mockusername = mockusername;
        this.mockpassword = mockpassword;
    }

    public String getUsername(){
        return mockusername;
    }

    public String getPassword(){
        return mockpassword;
    }

    // Save user login state
    public void saveLoginState(boolean rememberMe) {
        prefs.putBoolean("REMEMBER_ME", rememberMe);
        if (rememberMe) {
            prefs.put("USERNAME", mockusername);
            prefs.put("PASSWORD", mockpassword);
        } else {
            prefs.remove("USERNAME");
            prefs.remove("PASSWORD");
        }
    }

    // Check if the user has chosen to be remembered
    public boolean isRemembered() {
        return prefs.getBoolean("REMEMBER_ME", false);
    }

    // Retrieve saved username
    public String getSavedUsername() {
        return prefs.get("USERNAME", "");
    }

    // Retrieve saved password (not recommended for real applications due to security concerns)
    public String getSavedPassword() {
        return prefs.get("PASSWORD", "");
    }
}

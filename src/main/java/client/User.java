package client;

public class User {
    static String username;
    static String password;

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
}

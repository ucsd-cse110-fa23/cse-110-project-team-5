package client;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

// Login Screen class
class LoginScreen extends BorderPane {
    // Declare instance variables
    private TextField usernameField;
    private PasswordField passwordField;
    private CheckBox rememberMeCheckBox;
    private Button loginButton;
    private Button createAccountButton;
    private Button registerButton;
    private Text registrationText;
    private Text denyLoginText;
    private Text usernameTakenText;
    private AppFrame appFrame;
    private Model model;
    static User user;
    private Button backToLoginButton;
    private Text invalidFieldText;

    // Constructor for LoginScreen
    LoginScreen(AppFrame appFrame) {
        this.appFrame = appFrame;
        model = new Model();
        // Initialize UI components
        Text loginText = new Text("Log In");
        loginText.setFont(Font.font("Tahoma", FontWeight.BOLD, 33));

        usernameField = new TextField();
        usernameField.setMaxWidth(300);
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setMaxWidth(300);
        passwordField.setPromptText("Password");

        rememberMeCheckBox = new CheckBox("Remember me");

        loginButton = new Button("Log in");
        createAccountButton = new Button("Create an account");
        registerButton = new Button("Register"); // New button for registration
        registerButton.setVisible(false);

        backToLoginButton = new Button("Back to Log in");
        backToLoginButton.setVisible(false); // Initially invisi

        registrationText = new Text("Your account has been registered!");
        registrationText.setStyle("-fx-fill: red;");
        registrationText.setVisible(false);

        denyLoginText = new Text("The login details you entered are incorrect. Try again...");
        denyLoginText.setStyle("-fx-fill: red;");
        denyLoginText.setVisible(false);

        usernameTakenText = new Text("This username is taken. Try again...");
        usernameTakenText.setStyle("-fx-fill: red;");
        usernameTakenText.setVisible(false);

        invalidFieldText = new Text("Please make sure the username and password fields have been filled. Try again...");
        invalidFieldText.setStyle("-fx-fill: red;");
        invalidFieldText.setVisible(false);


        // Configure layout of the BorderPane
        VBox logBox = new VBox(loginText); // spacing between components
        VBox fieldBox = new VBox(usernameField, passwordField);
        VBox bottomBox = new VBox(rememberMeCheckBox, 
        loginButton, createAccountButton, registerButton, registrationText, denyLoginText, usernameTakenText, backToLoginButton, invalidFieldText);

        logBox.setAlignment(Pos.CENTER);
        logBox.setPrefHeight(250);
        fieldBox.setAlignment(Pos.CENTER);
        fieldBox.setPrefHeight(50);
        fieldBox.setSpacing(10);
        fieldBox.setPadding(new Insets(0, 0, 10, 0)); 
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPrefHeight(50);
        bottomBox.setSpacing(10);

        VBox vBox = new VBox(logBox, fieldBox, bottomBox);
        vBox.setStyle("-fx-background-color: #A4C3B2;");
        this.setCenter(vBox);

        // Initialize and configure button
        addListeners(); // Set up event listeners for buttons
    }

    // Method to add event listeners to buttons
    public void addListeners() {
        // Add button functionality
        loginButton.setOnAction(e -> {
            // Implement login logic here
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean rememberMe = rememberMeCheckBox.isSelected();
            user = new User(username, password);
            String loginRequest = model.sendLoginRequest(username, password);
            if (!(loginRequest.equals("loginerror")) && loginRequest.equals(password)) {
                denyLoginText.setVisible(false);
                User.saveLoginState(rememberMe);
                appFrame.showRecipeList();
                registrationText.setVisible(false);
            } else {
                denyLoginText.setVisible(true);
            }
            
            // Perform login validation or authentication here
            // You can call a method in your main application class to handle login logic
            // For now, let's just print the entered values
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("Remember Me: " + rememberMe);
        });

        createAccountButton.setOnAction(e -> {
            // Implement create account logic here
            // You can open a new window or navigate to another scene for account creation
            switchToRegistrationMode();
            System.out.println("Create an account button clicked");
            denyLoginText.setVisible(false);
        });

        registerButton.setOnAction(e -> {
           
            // Implement registration logic here
            // You can open a new window or navigate to another scene for registration
            //registrationText.setVisible(true);
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println(username);
            System.out.println(password);
            
            if ((password.length() == 0) || username.length() == 0) {
                invalidFieldText.setVisible(true);
            } 
            else if (model.sendSignupRequest(username, password).equals("registererror")) { 
                usernameTakenText.setVisible(true);
                registrationText.setVisible(false);
                invalidFieldText.setVisible(false);
                System.out.println("registererror");
            }
            else {
                usernameTakenText.setVisible(false);
                registrationText.setVisible(true);
                invalidFieldText.setVisible(false);
                // Reset to original Log In screen
                resetToOriginalState();
            }
            System.out.println("Register button clicked");
        });

        backToLoginButton.setOnAction(e -> {
            resetToOriginalState();
            usernameTakenText.setVisible(false);
            registrationText.setVisible(false);
            invalidFieldText.setVisible(false);
        });
    }

    // Method to update UI when switching to registration mode
    public void switchToRegistrationMode() {
        ((Text) ((VBox) ((VBox) this.getCenter()).getChildren().get(0)).getChildren().get(0)).setText("Sign Up");
        rememberMeCheckBox.setVisible(false);
        loginButton.setVisible(false);
        createAccountButton.setVisible(false);
        registerButton.setVisible(true);
        usernameTakenText.setVisible(false);
        registrationText.setVisible(false);
        backToLoginButton.setVisible(true);
        rememberMeCheckBox.setManaged(false);
        loginButton.setManaged(false);
        createAccountButton.setManaged(false);
        registerButton.setManaged(true);
        usernameTakenText.setManaged(false);
        registrationText.setManaged(false);
        backToLoginButton.setManaged(true);
    }

    private void resetToOriginalState() {
        ((Text) ((VBox) ((VBox) this.getCenter()).getChildren().get(0)).getChildren().get(0)).setText("Log In");
        rememberMeCheckBox.setVisible(true);
        loginButton.setVisible(true);
        createAccountButton.setVisible(true);
        registerButton.setVisible(false);
        backToLoginButton.setVisible(false);
        rememberMeCheckBox.setManaged(true);
        loginButton.setManaged(true);
        createAccountButton.setManaged(true);
        registerButton.setManaged(false);
        backToLoginButton.setManaged(false);
    }

   

    
}

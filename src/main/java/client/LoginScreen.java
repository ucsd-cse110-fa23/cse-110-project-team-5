package client;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    // Constructor for LoginScreen
    LoginScreen(AppFrame appFrame) {
        this.appFrame = appFrame;
        model = new Model();
        // Initialize UI components
        Text loginText = new Text("Log In");
        loginText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        rememberMeCheckBox = new CheckBox("Remember me");

        loginButton = new Button("Log in");
        createAccountButton = new Button("Create an account");
        registerButton = new Button("Register"); // New button for registration
        registerButton.setVisible(false);

        registrationText = new Text("Your account has been registered!");
        registrationText.setStyle("-fx-fill: red;");
        registrationText.setVisible(false);

        denyLoginText = new Text("The login details you entered are incorrect. Try again...");
        denyLoginText.setStyle("-fx-fill: red;");
        denyLoginText.setVisible(false);

        usernameTakenText = new Text("This username is taken. Try again...");
        usernameTakenText.setStyle("-fx-fill: red;");
        usernameTakenText.setVisible(false);


        // Configure layout of the BorderPane
        VBox vbox = new VBox(10); // spacing between components
        vbox.getChildren().addAll(loginText, usernameField, passwordField, rememberMeCheckBox, 
        loginButton, createAccountButton, registerButton, registrationText, denyLoginText, usernameTakenText);
        vbox.setAlignment(Pos.CENTER);

        this.setCenter(vbox);

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
            if (!(model.sendLoginRequest(username, password).equals("error"))) {
                appFrame.showRecipeList();
            } else {
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> {
                denyLoginText.setVisible(false);
            });
            pause.play();
            denyLoginText.setVisible(false);
            }
            UserRecipes userTest = new UserRecipes();
            System.out.println(userTest.getTitle(userTest.getResponse()));
            System.out.println(userTest.getTag(userTest.getResponse()));
            System.out.println(userTest.getDetails(userTest.getResponse()));
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
        });

        registerButton.setOnAction(e -> {
           
            // Implement registration logic here
            // You can open a new window or navigate to another scene for registration
            registrationText.setVisible(true);
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println(username);
            System.out.println(password);
            if (model.sendSignupRequest(username, password).equals("error")) {
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> {
                usernameTakenText.setVisible(false);
            });
            pause.play();
            usernameTakenText.setVisible(false);
            } else {
                // Use PauseTransition to hide the message after 0.5 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                registrationText.setVisible(false);
                // Reset to original Log In screen
                resetToOriginalState();
            });
            pause.play();
            registerButton.setVisible(false);
            }
            
            System.out.println("Register button clicked");
        });
    }

    // Method to update UI when switching to registration mode
    public void switchToRegistrationMode() {
        ((Text) ((VBox) this.getCenter()).getChildren().get(0)).setText("Sign Up");
        rememberMeCheckBox.setVisible(false);
        loginButton.setVisible(false);
        createAccountButton.setVisible(false);
        registerButton.setVisible(true);
    }

    private void resetToOriginalState() {
        ((Text) ((VBox) this.getCenter()).getChildren().get(0)).setText("Log In");
        rememberMeCheckBox.setVisible(true);
        loginButton.setVisible(true);
        createAccountButton.setVisible(true);
        registerButton.setVisible(false);
    }

    
}

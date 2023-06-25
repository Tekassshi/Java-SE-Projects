package guicontrollers;

import guicontrollers.abstractions.LanguageChanger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import util.AuthorizationManager;
import util.ConnectionManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static guicontrollers.SessionController.loadErrPortChoosingField;

public class AuthorizationController extends LanguageChanger implements Initializable {
    private AuthorizationManager authorizationManager = UserSessionManager.getAuthorizationManager();

    @FXML
    private MenuButton languageMenu;

    @FXML
    private Button logInButton;

    @FXML
    private Text SignUpPromt;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passHiddenField;

    @FXML
    private TextField passVisibleField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text errorMsg;

    @FXML
    private Button showPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultDesign();
        super.setMenuButton(languageMenu);
        changeLanguage();

        passHiddenField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                try {
                    performLogIn();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        usernameField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                passHiddenField.requestFocus();
            }
        });

        usernameField.requestFocus();
    }

    @FXML
    public void logInAction(ActionEvent e) throws IOException {
        performLogIn();
    }

    private void performLogIn() throws IOException {
        setDefaultDesign();

        String username = usernameField.getText();
        String password = passHiddenField.getText();

        Scene authLoadingScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/authorizationLoadingScene.fxml"));
        SessionController.setScene(authLoadingScene);

        Task<Boolean> authTask = new Task<>() {
            @Override
            protected Boolean call() throws IOException {
                return authorizationManager.logIn(username, password);
            }
        };

        authTask.setOnSucceeded(event -> {
            boolean res = authTask.getValue();
            if (res) {
                try {
                    System.out.println("Task succeed. Welcome page loading.");
                    loadWelcomePage(username);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                setWrongAuthDesign();
                SessionController.setScene(errorMsg.getScene());
            }
        });

        new Thread(authTask).start();
    }

    @Override
    public void changeLanguage() {
        languageMenu.setText(UserSessionManager.getCurrentBundle().getString("lang"));
        SignUpPromt.setText(UserSessionManager.getCurrentBundle().getString("signUpMsg"));
        signUpButton.setText(UserSessionManager.getCurrentBundle().getString("Sign Up"));
        passHiddenField.setPromptText(UserSessionManager.getCurrentBundle().getString("Password"));
        usernameField.setPromptText(UserSessionManager.getCurrentBundle().getString("Username"));
        logInButton.setText(UserSessionManager.getCurrentBundle().getString("logIn"));
    }

    private void loadWelcomePage(String user) throws IOException{
        Scene welcomeScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/welcomePageScene.fxml"));
        Parent root = welcomeScene.getRoot();
        Text username = (Text) root.lookup("#username");

        username.setText(user);
        SessionController.setScene(welcomeScene);
    }

    public void setDefaultDesign(){
        errorMsg.setVisible(false);
        usernameField.setStyle("-fx-background-color: #e5e5e5");
        passVisibleField.setStyle("-fx-background-color: #e5e5e5");
        passHiddenField.setStyle("-fx-background-color: #e5e5e5");
        showPass.setStyle("-fx-background-color: #e5e5e5");
    }

    public void setWrongInputDesign(){
        usernameField.setStyle("-fx-background-color: #ffe6e6");
        passVisibleField.setStyle("-fx-background-color: #ffe6e6");
        passHiddenField.setStyle("-fx-background-color: #ffe6e6");
        showPass.setStyle("-fx-background-color: #ffe6e6");

        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong username or password format " +
                "(From 5 to 15 symbols and they " +
                "should contains only english letters or digits.)"));
        errorMsg.setVisible(true);
    }

    public void setWrongAuthDesign(){
        usernameField.setStyle("-fx-background-color: #ffe6e6");
        passVisibleField.setStyle("-fx-background-color: #ffe6e6");
        passHiddenField.setStyle("-fx-background-color: #ffe6e6");
        showPass.setStyle("-fx-background-color: #ffe6e6");

        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong username or password!"));
        errorMsg.setVisible(true);
    }

    @FXML
    public void signUpAction(ActionEvent e) throws Exception {
        Scene scene = FXMLLoader.load(getClass().getResource("/GUI/scenes/signUpScene.fxml"));
        SessionController.setScene(scene);
    }

    @FXML
    public void unmaskPassword(MouseEvent e){
        passVisibleField.setText(passHiddenField.getText());
        passHiddenField.setVisible(false);
    }

    @FXML
    public void maskPassword(MouseEvent e){
        passHiddenField.setVisible(true);
    }
}
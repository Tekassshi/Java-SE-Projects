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
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import util.AuthorizationManager;
import util.ConnectionManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends LanguageChanger implements Initializable {
    private AuthorizationManager authorizationManager = UserSessionManager.getAuthorizationManager();

    @FXML
    private Button signUpButton;

    @FXML
    private Text errorMsg;

    @FXML
    private MenuButton languageMenu;

    @FXML
    private PasswordField passHiddenField;

    @FXML
    private TextField passVisibleField;

    @FXML
    private Text promtText;

    @FXML
    private PasswordField repPasswordField;

    @FXML
    private Button showPass;

    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultDesign();
        super.setMenuButton(languageMenu);
        changeLanguage();

        usernameField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                passHiddenField.requestFocus();
            }
        });

        passHiddenField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                repPasswordField.requestFocus();
            }
        });

        repPasswordField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                try {
                    performSignUp();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void signUpAction(ActionEvent e) throws IOException {
        performSignUp();
    }

    private void performSignUp() throws IOException {
        setDefaultDesign();

        String username = usernameField.getText();
        String password1 = passHiddenField.getText();
        String password2 = repPasswordField.getText();

        if (!password1.equals(password2)){
            setPassMismatchDesign();
            return;
        }

        Scene authLoadingScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/authorizationLoadingScene.fxml"));
        SessionController.setScene(authLoadingScene);

        Task<Boolean> authTask = new Task<>() {
            @Override
            protected Boolean call() throws IOException {
                return authorizationManager.signUp(username, password1);
            }
        };

        authTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                boolean res = authTask.getValue();
                if (res) {
                    try {
                        loadWelcomePage(username);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    setBadUsernameDesign();
                    SessionController.setScene(errorMsg.getScene());
                }
            }
        });

        new Thread(authTask).start();
    }

    private void loadErrPortChoosingField() throws IOException {
        Scene scene = FXMLLoader.load(getClass().getResource("/GUI/scenes/portChoiceScene.fxml"));
        Parent root = scene.getRoot();
        Text errorMsg = (Text) root.lookup("#errorMsg");
        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Server error. Please, try later."));
        errorMsg.setVisible(true);
        SessionController.setScene(scene);
    }

    private void loadWelcomePage(String user) throws IOException{
        Scene welcomeScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/welcomePageScene.fxml"));
        Parent root = welcomeScene.getRoot();
        Text username = (Text) root.lookup("#username");
        Text welcomeText = (Text) root.lookup("#welcomeMsg");
        Text succRegText = (Text) root.lookup("#succRegText");
        succRegText.setText(UserSessionManager.getCurrentBundle().getString("RegSucc"));
        succRegText.setVisible(true);

        welcomeText.setText("Welcome, ");
        username.setText(user);
        SessionController.setScene(welcomeScene);
    }

    public void setDefaultDesign(){
        errorMsg.setVisible(false);
        showPass.setStyle("-fx-background-color: #e5e5e5");
        usernameField.setStyle("-fx-background-color: #e5e5e5");
        passVisibleField.setStyle("-fx-background-color: #e5e5e5");
        passHiddenField.setStyle("-fx-background-color: #e5e5e5");
        repPasswordField.setStyle("-fx-background-color: #e5e5e5");
    }

    public void setWrongInputDesign(){
        usernameField.setStyle("-fx-background-color: #ffe6e6");
        passVisibleField.setStyle("-fx-background-color: #ffe6e6");
        passHiddenField.setStyle("-fx-background-color: #ffe6e6");
        showPass.setStyle("-fx-background-color: #ffe6e6");

        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong username or password " +
                "format (From 5 to 15 symbols and they " +
                "should contains only english letters or digits.)"));
        errorMsg.setVisible(true);
    }

    public void setBadUsernameDesign(){
        usernameField.setStyle("-fx-background-color: #ffe6e6");
        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("This username is already exists"));
        errorMsg.setVisible(true);
    }

    public void setPassMismatchDesign(){
        passVisibleField.setStyle("-fx-background-color: #ffe6e6");
        passHiddenField.setStyle("-fx-background-color: #ffe6e6");
        repPasswordField.setStyle("-fx-background-color: #ffe6e6");
        showPass.setStyle("-fx-background-color: #ffe6e6");

        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Passwords are different!"));
        errorMsg.setVisible(true);
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

    @FXML
    public void rollbackToAuth(ActionEvent e) throws Exception{
        Scene scene = FXMLLoader.load(getClass().getResource("/GUI/scenes/authorizationScene.fxml"));
        SessionController.setScene(scene);
    }

    @Override
    protected void changeLanguage() {
        languageMenu.setText(UserSessionManager.getCurrentBundle().getString("lang"));
        usernameField.setPromptText(UserSessionManager.getCurrentBundle().getString("Username"));
        passHiddenField.setPromptText(UserSessionManager.getCurrentBundle().getString("Password"));
        repPasswordField.setPromptText(UserSessionManager.getCurrentBundle().getString("Repeat password"));
        signUpButton.setText(UserSessionManager.getCurrentBundle().getString("Sign Up"));
        promtText.setText(UserSessionManager.getCurrentBundle().getString("Creating a new account"));
    }
}

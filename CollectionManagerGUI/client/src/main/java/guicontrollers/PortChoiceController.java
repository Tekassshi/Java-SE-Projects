package guicontrollers;

import guicontrollers.abstractions.LanguageChanger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import managers.InputManager;
import util.AuthorizationManager;
import util.ConnectionManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PortChoiceController extends LanguageChanger implements Initializable {
    private AuthorizationManager authorizationManager = UserSessionManager.getAuthorizationManager();
    private ConnectionManager connectionManager = UserSessionManager.getConnectionManager();

    @FXML
    private Button connectButton;

    @FXML
    private MenuButton languageMenu;

    @FXML
    private Text errorMsg;

    @FXML
    private AnchorPane majorPane;

    @FXML
    private TextField portField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setMenuButton(languageMenu);
        changeLanguage();

        errorMsg.setVisible(false);
        portField.setStyle("-fx-background-color: #e5e5e5");

        portField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                try {
                    performConnecting();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void tryConnect(ActionEvent e) throws IOException {
        performConnecting();
    }

    private void performConnecting() throws IOException {
        errorMsg.setVisible(false);
        portField.setStyle("-fx-background-color: #e5e5e5");
        int port;

        try {
            port = InputManager.readPort(portField.getText());
        }
        catch (IOException | NumberFormatException ex) {
            errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong port value!"));
            errorMsg.setVisible(true);
            portField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        Scene authScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/authorizationLoadingScene.fxml"));
        SessionController.setScene(authScene);

        Task<Boolean> connectTask = new Task<>() {
            @Override
            protected Boolean call() {
                return connectionManager.tryConnect(port);
            }
        };

        connectTask.setOnSucceeded(new EventHandler<>() {
            @Override
            public void handle(WorkerStateEvent event) {
                boolean connectionResult = connectTask.getValue();

                if (connectionResult) {
                    Scene next;
                    try {
                        next = FXMLLoader.load(getClass().getResource("/GUI/scenes/authorizationScene.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    SessionController.setScene(next);
                }
                else {
                    SessionController.setScene(errorMsg.getScene());
                    errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Server error. Please, " +
                            "try later."));
                    errorMsg.setVisible(true);
                }
            }
        });
        new Thread(connectTask).start();
    }

    @Override
    protected void changeLanguage() {
        languageMenu.setText(UserSessionManager.getCurrentBundle().getString("lang"));
        connectButton.setText(UserSessionManager.getCurrentBundle().getString("Connect"));
        portField.setPromptText(UserSessionManager.getCurrentBundle().getString("Port"));
    }
}
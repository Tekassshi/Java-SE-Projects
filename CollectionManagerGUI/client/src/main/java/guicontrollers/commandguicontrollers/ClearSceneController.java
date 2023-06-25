package guicontrollers.commandguicontrollers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import commands.Clear;
import guicontrollers.SessionController;
import guicontrollers.abstractions.LanguageChanger;
import interfaces.Command;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static guicontrollers.SessionController.loadErrPortChoosingField;

public class ClearSceneController extends LanguageChanger implements Initializable {

    @FXML
    private Text question;

    @FXML
    private Button sendCommandBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeLanguage();
        sendCommandBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    sendCommand();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void sendCommand() throws IOException {
        Command command = new Clear(null);

        Stage currentStage = (Stage) sendCommandBtn.getScene().getWindow();
        currentStage.close();
        Stage resStage = SessionController.openResWindow();

        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws IOException {
                String res = UserSessionManager.getCommandManager().processUserCommand(command);
                return UserSessionManager.getCurrentBundle().getString(res);
            }
        };

        commandTask.setOnSucceeded(event -> {
            Parent root = resStage.getScene().getRoot();
            TextArea response = (TextArea) root.lookup("#textArea");
            VBox vBox = (VBox) root.lookup("#vBox");
            vBox.setVisible(true);
            vBox.setDisable(false);
            response.setText(commandTask.getValue());
            response.setEditable(false);
        });

        new Thread(commandTask).start();
    }

    @Override
    protected void changeLanguage() {
        sendCommandBtn.setText(UserSessionManager.getCurrentBundle().getString("Clear"));
        question.setText(UserSessionManager.getCurrentBundle().getString(question.getText()));
    }
}

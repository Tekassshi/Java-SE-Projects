package guicontrollers.commandguicontrollers;

import commands.Clear;
import guicontrollers.SessionController;
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
import javafx.stage.Stage;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClearSceneController implements Initializable {

    @FXML
    private Button sendCommandBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                return UserSessionManager.getCommandManager().processUserCommand(command);
            }
        };

        commandTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Parent root = resStage.getScene().getRoot();
                TextArea response = (TextArea) root.lookup("#textArea");
                VBox vBox = (VBox) root.lookup("#vBox");
                vBox.setVisible(true);
                vBox.setDisable(false);
                response.setText(commandTask.getValue());
                response.setEditable(false);
            }
        });

        commandTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                commandTask.getException().printStackTrace();
            }
        });

        new Thread(commandTask).start();
    }
}

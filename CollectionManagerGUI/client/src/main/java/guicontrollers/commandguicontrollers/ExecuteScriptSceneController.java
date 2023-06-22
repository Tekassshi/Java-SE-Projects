package guicontrollers.commandguicontrollers;

import commands.ExecuteScript;
import guicontrollers.SessionController;
import guicontrollers.abstractions.LanguageChanger;
import interfaces.CommandWithArg;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.UserSessionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class ExecuteScriptSceneController extends LanguageChanger implements Initializable {
    @FXML
    private Text errorMsg;

    @FXML
    private Text label;

    @FXML
    private TextField scriptPathField;

    @FXML
    private Button sendCommandBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMsg.setVisible(false);
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

        scriptPathField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
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
        setDefaultDesign();
        CommandWithArg command = new ExecuteScript(null);

        try {
            command.setArg(scriptPathField.getText());
        }
        catch (FileNotFoundException e) {
            errorMsg.setText(UserSessionManager.getCurrentBundle().getString("File not found."));
            errorMsg.setVisible(true);
            scriptPathField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        Stage currentStage = (Stage) errorMsg.getScene().getWindow();
        currentStage.close();
        Stage resStage = SessionController.openResWindow();
        Parent root = resStage.getScene().getRoot();

        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws IOException {
                return UserSessionManager.getCommandManager().processUserCommand((command));
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
                Throwable exception = commandTask.getException();

                if (exception instanceof FileNotFoundException) {
                    errorMsg.setText(UserSessionManager.getCurrentBundle().getString("File doesn't exist!"));
                    errorMsg.setVisible(true);
                    scriptPathField.setStyle("-fx-background-color: #ffe6e6");
                }
                else if (exception instanceof IllegalArgumentException ||
                        exception instanceof IOException ||
                        exception instanceof InputMismatchException ||
                        exception instanceof NullPointerException) {

                        errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong data in script. " +
                                "Process will be terminated."));
                        errorMsg.setVisible(true);
                        scriptPathField.setStyle("-fx-background-color: #ffe6e6");
                }
                else if (exception instanceof RuntimeException) {
                    errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Recursion detected. " +
                            "Process will be terminated."));
                    errorMsg.setVisible(true);
                    scriptPathField.setStyle("-fx-background-color: #ffe6e6");
                }

                resStage.close();
                currentStage.show();
            }
        });

        new Thread(commandTask).start();
    }

    private void setDefaultDesign(){
        scriptPathField.setStyle("-fx-background-color: #e5e5e5");
    }

    @Override
    protected void changeLanguage() {
        sendCommandBtn.setText(UserSessionManager.getCurrentBundle().getString("Execute"));
        label.setText(UserSessionManager.getCurrentBundle().getString(label.getText()));
        scriptPathField.setPromptText(UserSessionManager.getCurrentBundle().getString(scriptPathField.getPromptText()));
    }
}

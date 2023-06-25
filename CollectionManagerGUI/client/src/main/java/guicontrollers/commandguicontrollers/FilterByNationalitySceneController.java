package guicontrollers.commandguicontrollers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import commands.FilterByNationality;
import commands.RemoveAllByNationality;
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
import managers.InputManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static guicontrollers.SessionController.loadErrPortChoosingField;

public class FilterByNationalitySceneController extends LanguageChanger implements Initializable {

    @FXML
    private Text errorMsg;

    @FXML
    private Text label;

    @FXML
    private TextField nationalityField;

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

        nationalityField.setOnKeyPressed(key -> {
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
        CommandWithArg command = new FilterByNationality(null);

        try {
            String nationality = String.valueOf(InputManager.readNationality(nationalityField.getText()));
            command.setArg(nationality);
        }
        catch (IllegalArgumentException e){
            errorMsg.setText(UserSessionManager.getCurrentBundle().getString("Wrong nationality value! " +
                    "(you can choose one of the values \"RUSSIA, FRANCE, THAILAND," +
                    " NORTH_KOREA\")"));
            errorMsg.setVisible(true);
            nationalityField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        Stage currentStage = (Stage) nationalityField.getScene().getWindow();
        currentStage.close();
        Stage resStage = SessionController.openResWindow();

        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws IOException {
                return UserSessionManager.getCommandManager().processUserCommand(command);
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

    private void setDefaultDesign(){
        nationalityField.setStyle("-fx-background-color: #e5e5e5");
    }

    @Override
    protected void changeLanguage() {
        sendCommandBtn.setText(UserSessionManager.getCurrentBundle().getString("Filter"));
        label.setText(UserSessionManager.getCurrentBundle().getString("Filter by nationality"));
        nationalityField.setPromptText(UserSessionManager.getCurrentBundle().getString("Nationality"));
    }
}

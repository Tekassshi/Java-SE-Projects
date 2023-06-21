package guicontrollers.commandguicontrollers;

import commands.AddIfMin;
import data.*;
import guicontrollers.SessionController;
import interfaces.AssemblableCommand;
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

public class AddIfMinSceneController implements Initializable {
    @FXML
    private Text errorMsg;

    @FXML
    private TextField eyeColorField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nationalityField;

    @FXML
    private Button sendCommandBtn;

    @FXML
    private TextField weightField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField xLoocField;

    @FXML
    private TextField yCoordField;

    @FXML
    private TextField yLoocField;

    @FXML
    private TextField zLoocField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMsg.setVisible(false);

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

        nameField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                heightField.requestFocus();
            }
        });

        heightField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                weightField.requestFocus();
            }
        });

        weightField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                eyeColorField.requestFocus();
            }
        });

        eyeColorField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                nationalityField.requestFocus();
            }
        });

        nationalityField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                xCoordField.requestFocus();
            }
        });

        xCoordField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                yCoordField.requestFocus();
            }
        });

        yCoordField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                xLoocField.requestFocus();
            }
        });

        xLoocField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                yLoocField.requestFocus();
            }
        });

        yLoocField.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                zLoocField.requestFocus();
            }
        });

        zLoocField.setOnKeyPressed(key -> {
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
        PersonParamsContainer personParamsContainer = new PersonParamsContainer();

        try {
            String name = InputManager.readName(nameField.getText());
            personParamsContainer.setName(name);
        }
        catch (IllegalArgumentException e) {
            errorMsg.setText(" Wrong name format! Name should contain at least 1 symbol and " +
                    "only letters supported");
            errorMsg.setVisible(true);
            nameField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Coordinates coordinates = InputManager.readCoordinates(xCoordField.getText(), yCoordField.getText());
            personParamsContainer.setxCoord(String.valueOf(coordinates.getX()));
            personParamsContainer.setyCoord(String.valueOf(coordinates.getY()));
        }
        catch (NumberFormatException | IOException e) {
            errorMsg.setText("Wrong coordinates format! (\"X\" should be integer number, " +
                    "that > -783, \"Y\" should be long integer number.)");
            errorMsg.setVisible(true);
            xCoordField.setStyle("-fx-background-color: #ffe6e6");
            yCoordField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Integer height = InputManager.readHeight(heightField.getText());
            personParamsContainer.setHeight(String.valueOf(height));
        }
        catch (NumberFormatException | IOException e) {
            errorMsg.setText("Wrong height format! (height should be integer > 0, and " +
                    "only digits supported)");
            errorMsg.setVisible(true);
            heightField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Float weight = InputManager.readWeight(weightField.getText());
            personParamsContainer.setWeight(String.valueOf(weight));
        }
        catch (NumberFormatException | IOException e) {
            errorMsg.setText("Wrong weight format! (weight should be decimal value > 0)");
            errorMsg.setVisible(true);
            weightField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Color eyeColor = InputManager.readEyeColor(eyeColorField.getText());
            personParamsContainer.setEyeColor(String.valueOf(eyeColor));
        }
        catch (IllegalArgumentException | IOException e) {
            errorMsg.setText("Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")");
            errorMsg.setVisible(true);
            eyeColorField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Country nationality = InputManager.readNationality(nationalityField.getText());
            personParamsContainer.setNationality(nationality.toString());
        }
        catch (IllegalArgumentException | IOException e) {
            errorMsg.setText(" Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, " +
                    "NORTH_KOREA\")");
            errorMsg.setVisible(true);
            nationalityField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        try {
            Location location = InputManager.readLocation(xLoocField.getText(), yLoocField.getText(),
                    zLoocField.getText());
            personParamsContainer.setxLooc(String.valueOf(location.getX()));
            personParamsContainer.setyLooc(String.valueOf(location.getY()));
            personParamsContainer.setzLooc(String.valueOf(location.getZ()));
        }
        catch (NumberFormatException | IOException e) {
            errorMsg.setText("Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, " +
                    "\"Z\" should be Double number.");
            errorMsg.setVisible(true);
            xLoocField.setStyle("-fx-background-color: #ffe6e6");
            yLoocField.setStyle("-fx-background-color: #ffe6e6");
            zLoocField.setStyle("-fx-background-color: #ffe6e6");
            return;
        }

        AssemblableCommand addIfMin = new AddIfMin(null);
        addIfMin.buildObject(personParamsContainer);

        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();
        Stage resStage = SessionController.openResWindow();

        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws IOException {
                return UserSessionManager.getCommandManager().processUserCommand((Command) addIfMin);
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

    private void setDefaultDesign(){
        errorMsg.setVisible(false);
        eyeColorField.setStyle("-fx-background-color: #e5e5e5");
        heightField.setStyle("-fx-background-color: #e5e5e5");
        weightField.setStyle("-fx-background-color: #e5e5e5");
        nameField.setStyle("-fx-background-color: #e5e5e5");
        nationalityField.setStyle("-fx-background-color: #e5e5e5");
        xCoordField.setStyle("-fx-background-color: #e5e5e5");
        yCoordField.setStyle("-fx-background-color: #e5e5e5");
        xLoocField.setStyle("-fx-background-color: #e5e5e5");
        yLoocField.setStyle("-fx-background-color: #e5e5e5");
        zLoocField.setStyle("-fx-background-color: #e5e5e5");
    }
}

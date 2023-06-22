package guicontrollers.commandguicontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import util.UserSessionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerResponseController implements Initializable {
    @FXML
    private TextArea textArea;

    @FXML
    private TitledPane titledPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setEditable(false);
        titledPane.setText(UserSessionManager.getCurrentBundle().getString("Server response"));
    }
}
package guicontrollers.commandguicontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import util.UserSessionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoSceneController implements Initializable {

    @FXML
    private Label infoRes;

    @FXML
    private Text title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(UserSessionManager.getCurrentBundle().getString("infoTitle"));
    }
}

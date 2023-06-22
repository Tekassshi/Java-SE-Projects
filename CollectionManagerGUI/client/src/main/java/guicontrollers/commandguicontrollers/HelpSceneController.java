package guicontrollers.commandguicontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import util.UserSessionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpSceneController implements Initializable {

    @FXML
    private Label reference;

    @FXML
    private Label referenceText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        referenceText.setText(UserSessionManager.getCurrentBundle().getString("helpLabel"));
        reference.setText(UserSessionManager.getCurrentBundle().getString("helpMsg"));
    }
}

package guicontrollers;

import guicontrollers.abstractions.LanguageChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController extends LanguageChanger implements Initializable{

    @FXML
    private Button nextButton;

    @FXML
    private Text username;

    @FXML
    private Text welcomeMsg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeLanguage();

        welcomeMsg.getScene().setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                try {
                    loadMainControlPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void nextPage(ActionEvent event) throws IOException {
        loadMainControlPage();
    }

    private void loadMainControlPage() throws IOException {
        Stage stage = SessionController.getStage();
        VBox vBox = FXMLLoader.load(getClass().getResource("/GUI/scenes/mainControlScene.fxml"));

        Scene mainControlScene = new Scene(vBox);
        Parent root = mainControlScene.getRoot();
        Text username = (Text) root.lookup("#username");
        username.setText(this.username.getText());

        stage.setResizable(true);
        stage.setMinWidth(690);
        stage.setMinHeight(450);

        stage.setScene(mainControlScene);
    }

    @Override
    protected void changeLanguage() {
        welcomeMsg.setText(UserSessionManager.getCurrentBundle().getString("Welcome, "));
        nextButton.setText(UserSessionManager.getCurrentBundle().getString("Next"));
    }
}

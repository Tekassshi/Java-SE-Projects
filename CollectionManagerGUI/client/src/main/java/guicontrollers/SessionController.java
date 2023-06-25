package guicontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.UserSessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionController {
    static private Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        SessionController.stage = stage;
    }

    public static void setScene(Scene scene){
        stage.setScene(scene);
    }

    public static Stage openResWindow() throws IOException {
        StackPane stackPane = FXMLLoader
                .load(SessionController.class.getResource("/GUI/scenes/commandScenes/responseScene.fxml"));
        Scene scene = new Scene(stackPane);

        Stage stage1 = new Stage();
        stage1.setMinWidth(620);
        stage1.setMinHeight(400);
        stage1.setScene(scene);

        stage1.initOwner(getStage());
        stage1.initModality(Modality.WINDOW_MODAL);
        stage1.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage1.show();
        return stage1;
    }

    public static void loadErrPortChoosingField(String errorCode) throws IOException {
        Stage stage = getStage();
        Window currentWindow = stage.getScene().getWindow();

        List<Window> list = Stage.getWindows().stream().toList();
        list.forEach(window -> {
            if (window != currentWindow)
                ((Stage) window).close();
        });

        Scene scene = FXMLLoader.load(SessionController.class.getResource("/GUI/scenes/portChoiceScene.fxml"));
        Parent root = scene.getRoot();
        Text errorMsg = (Text) root.lookup("#errorMsg");
        errorMsg.setText(UserSessionManager.getCurrentBundle().getString(errorCode));
        errorMsg.setVisible(true);
        stage.setResizable(false);
        SessionController.setScene(scene);
    }
}
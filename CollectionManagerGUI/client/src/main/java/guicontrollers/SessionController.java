package guicontrollers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.CollectionUpdater;

import java.io.IOException;

public class SessionController {
    static private Stage stage;
    static public CollectionUpdater collectionUpdater;

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
}

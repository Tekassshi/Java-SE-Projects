package guicontrollers;

import javafx.scene.Scene;
import javafx.stage.Stage;

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
}

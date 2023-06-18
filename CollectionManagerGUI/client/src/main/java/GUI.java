import guicontrollers.SessionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SessionController.setStage(stage);
        stage.setTitle("Collection Manager GUI");
        stage.setResizable(false);
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        Scene scene = FXMLLoader.load(getClass().getResource("/GUI/scenes/portChoiceScene.fxml"));

        stage.setScene(scene);
        stage.show();
    }
}
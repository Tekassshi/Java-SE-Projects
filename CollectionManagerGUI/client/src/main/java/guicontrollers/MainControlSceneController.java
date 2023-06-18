package guicontrollers;

import commands.Info;
import interfaces.Command;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.CommandManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainControlSceneController implements Initializable {

    private CommandManager commandManager = UserSessionManager.getCommandManager();

    @FXML
    private Button animBtn;

    @FXML
    private MenuButton commandBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private ChoiceBox languageBox;

    @FXML
    private TableView tableView;

    @FXML
    private Text username;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MenuItem menuItem1 = new MenuItem("Help");
        MenuItem menuItem2 = new MenuItem("Info");
        MenuItem menuItem3 = new MenuItem("Add");
        MenuItem menuItem4 = new MenuItem("Update Id");
        MenuItem menuItem5 = new MenuItem("Remove By Id");
        MenuItem menuItem6 = new MenuItem("Clear");
        MenuItem menuItem7 = new MenuItem("Execute Script");
        MenuItem menuItem8 = new MenuItem("Head");
        MenuItem menuItem9 = new MenuItem("Add if min");
        MenuItem menuItem10 = new MenuItem("Remove greater");
        MenuItem menuItem11 = new MenuItem("Remove all by nationality");
        MenuItem menuItem12 = new MenuItem("Filter by nationality");
        MenuItem menuItem13 = new MenuItem("Print field descending height");


        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openHelpScene();
            }
        });

        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openInfoScene();
                } catch (IOException e) {
                    throw new RuntimeException(e); // Server error
                }
            }
        });

        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openAddScene();
                } catch (IOException e) {
                    throw new RuntimeException(e); // Server error
                }
            }
        });

        commandBtn.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7,
                menuItem8, menuItem9, menuItem10, menuItem11, menuItem12, menuItem13);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void openHelpScene() {
        Stage stage = new Stage();
        GridPane gridPane;
        try {
            gridPane = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/help.fxml"));
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene helpScene = new Scene(gridPane);
        stage.setScene(helpScene);

        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);

        stage.show();
    }

    private void openInfoScene() throws IOException {
        Command info = new Info(null);
        String res = commandManager.processUserCommand(info);

        Stage stage = new Stage();
        GridPane gridPane;
        try {
            gridPane = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/info.fxml"));
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene infoScene = new Scene(gridPane);

        Parent root = infoScene.getRoot();
        Label infoRes = (Label) root.lookup("#infoRes");
        infoRes.setText(res);
        stage.setScene(infoScene);
        stage.setResizable(false);

        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void openAddScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/add.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }
}

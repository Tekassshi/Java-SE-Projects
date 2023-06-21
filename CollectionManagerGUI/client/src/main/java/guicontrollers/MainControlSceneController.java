package guicontrollers;

import commands.Head;
import commands.Info;
import commands.PrintFieldDescendingHeight;
import commands.Show;
import data.*;
import guicontrollers.abstractions.LanguageChanger;
import interfaces.Command;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.CollectionUpdater;
import util.CollectionWrapper;
import util.CommandManager;
import util.UserSessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainControlSceneController extends LanguageChanger implements Initializable {

    private CommandManager commandManager = UserSessionManager.getCommandManager();
    private Property<ObservableList<TableViewPerson>> personTableProperty =
            new SimpleObjectProperty<>(CollectionWrapper.getCollection());

    @FXML
    private MenuButton languageMenu;

    @FXML
    private TextField filterField;

    @FXML
    private Button animBtn;

    @FXML
    private MenuButton commandBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private TableView<TableViewPerson> tableView;

    @FXML
    private Text username;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();

        TableColumn<TableViewPerson, Long> idColumn = new TableColumn<>("ID");
        TableColumn<TableViewPerson, String> nameColumn = new TableColumn<>("Name");
        TableColumn<TableViewPerson, Long> coordXColumn = new TableColumn<>("Coord X");
        TableColumn<TableViewPerson, Long> coordYColumn = new TableColumn<>("Coord Y");
        TableColumn<TableViewPerson, ZonedDateTime> dateColumn = new TableColumn<>("Creation Date");
        TableColumn<TableViewPerson, Integer> heightColumn = new TableColumn<>("Height");
        TableColumn<TableViewPerson, Float> weightColumn = new TableColumn<>("Weight");
        TableColumn<TableViewPerson, Color> eyeColorColumn = new TableColumn<>("Eye color");
        TableColumn<TableViewPerson, Country> nationalityColumn = new TableColumn<>("Nationality");
        TableColumn<TableViewPerson, Integer> locationXColumn = new TableColumn<>("Location X");
        TableColumn<TableViewPerson, Float> locationYColumn = new TableColumn<>("Location Y");
        TableColumn<TableViewPerson, Double> locationZColumn = new TableColumn<>("Location Z");
        TableColumn<TableViewPerson, String> usernameColumn = new TableColumn<>("Owner");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordXColumn.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
        coordYColumn.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        eyeColorColumn.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locationXColumn.setCellValueFactory(new PropertyValueFactory<>("xLooc"));
        locationYColumn.setCellValueFactory(new PropertyValueFactory<>("yLooc"));
        locationZColumn.setCellValueFactory(new PropertyValueFactory<>("zLooc"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        setZonedDateTimeCellFactory(dateColumn);
        setColorCellFactory(eyeColorColumn);
        setCountryCellFactory(nationalityColumn);
        setLongCellFactory(coordXColumn);
        setLongCellFactory(coordYColumn);
        setIntegerCellFactory(heightColumn);
        setFloatCellFactory(weightColumn);
        setIntegerCellFactory(locationXColumn);
        setFloatCellFactory(locationYColumn);
        setDoubleCellFactory(locationZColumn);

        tableView.getColumns().addAll(idColumn, nameColumn, coordXColumn, coordYColumn, dateColumn, heightColumn,
                weightColumn, eyeColorColumn, nationalityColumn, locationXColumn, locationYColumn, locationZColumn,
                usernameColumn);

        tableView.setItems(CollectionWrapper.getCollection());
        tableView.itemsProperty().bind(personTableProperty);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.itemsProperty().bind(personTableProperty);

        exitBtn.setOnAction(event -> Platform.exit());

        super.setMenuButton(languageMenu);

        Thread collectionUpdater = new Thread(new CollectionUpdater(UserSessionManager.getAuthorizationManager(),
                UserSessionManager.getConnectionManager()));
        collectionUpdater.setDaemon(true);
        collectionUpdater.start();

        changeLanguage();
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

    private void openUpdateIdScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/updateId.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openRemoveByIdScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/removeById.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openClearScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/clear.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openHeadScene() throws IOException {
        Stage resStage = SessionController.openResWindow();
        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                return UserSessionManager.getCommandManager().processUserCommand(new Head(null));
            }
        };

        commandTask.setOnSucceeded(event -> {
            Parent root = resStage.getScene().getRoot();
            TextArea response = (TextArea) root.lookup("#textArea");
            VBox vBox = (VBox) root.lookup("#vBox");
            vBox.setVisible(true);
            vBox.setDisable(false);
            response.setText(commandTask.getValue());
            response.setEditable(false);
        });

        new Thread(commandTask).start();
    }

    private void openAddIfMinScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/addIfMin.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openRemoveGreaterScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/removeGreater.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openRemoveByNationalityScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/commandScenes/removeByNationality.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openExecuteScriptScene() throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/commandScenes/executeScript.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openShowScene() throws IOException {
        Stage resStage = SessionController.openResWindow();
        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                return UserSessionManager.getCommandManager().processUserCommand(new Show(null));
            }
        };

        commandTask.setOnSucceeded(event -> {
            Parent root = resStage.getScene().getRoot();
            TextArea response = (TextArea) root.lookup("#textArea");
            VBox vBox = (VBox) root.lookup("#vBox");
            vBox.setVisible(true);
            vBox.setDisable(false);
            response.setText(commandTask.getValue());
            response.setEditable(false);
        });

        new Thread(commandTask).start();
    }

    private void openFilterByNationalityScene() throws IOException{
        Stage stage = new Stage();
        Scene addScene = FXMLLoader
                .load(getClass().getResource("/GUI/scenes/commandScenes/filterByNationality.fxml"));
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openPrintDescHeightScene() throws IOException {
        Stage resStage = SessionController.openResWindow();
        Task<String> commandTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                return UserSessionManager.getCommandManager()
                        .processUserCommand(new PrintFieldDescendingHeight(null));
            }
        };

        commandTask.setOnSucceeded(event -> {
            Parent root = resStage.getScene().getRoot();
            TextArea response = (TextArea) root.lookup("#textArea");
            VBox vBox = (VBox) root.lookup("#vBox");
            vBox.setVisible(true);
            vBox.setDisable(false);
            response.setText(commandTask.getValue());
            response.setEditable(false);
        });

        new Thread(commandTask).start();
    }

    @Override
    protected void changeLanguage() {
        languageMenu.setText(UserSessionManager.getCurrentBundle().getString("lang"));
        commandBtn.setText(UserSessionManager.getCurrentBundle().getString("Command"));
        filterField.setPromptText(UserSessionManager.getCurrentBundle().getString("Filter"));
        exitBtn.setText(UserSessionManager.getCurrentBundle().getString("Exit"));

        ObservableList<TableColumn<TableViewPerson, ?>> tmp = tableView.getColumns();
        ArrayList<String> tmp2 = new ArrayList<>(Arrays.asList("ID", "Name", "Coord X", "Coord Y", "Creation Date",
                "Height", "Weight", "Eye color", "Nationality", "Location X", "Location Y", "Location Z", "Owner"));
        for (int i = 0; i < tmp.size(); i++){
            tmp.get(i).setText(UserSessionManager.getCurrentBundle().getString(tmp2.get(i)));
        }

        tableView.refresh();
    }

    private void setZonedDateTimeCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, ZonedDateTime>() {
            @Override
            protected void updateItem(ZonedDateTime zdt, boolean empty) {
                Locale locale = new Locale(UserSessionManager.getCurrentBundle().getString("locale"));
                DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(locale);

                super.updateItem(zdt, empty);
                if (empty)
                    setText(null);
                else
                    setText(zdt.format(f));
            }
        });
    }

    private void setColorCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Color>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);
                if (empty)
                    setText(null);
                else
                    setText(UserSessionManager.getCurrentBundle().getString(String.valueOf(color)));
            }
        });
    }

    private void setCountryCellFactory(TableColumn tableColumn){
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Country>() {
            @Override
            protected void updateItem(Country country, boolean empty) {
                super.updateItem(country, empty);
                if (empty)
                    setText(null);
                else
                    setText(UserSessionManager.getCurrentBundle().getString(String.valueOf(country)));
            }
        });
    }

    private void setLongCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Long>() {
            Locale locale = new Locale(UserSessionManager.getCurrentBundle().getString("locale"));
            NumberFormat format = NumberFormat.getInstance(locale);

            @Override
            protected void updateItem(Long number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(format.format(number));
            }
        });
    }

    private void setIntegerCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Integer>() {
            Locale locale = new Locale(UserSessionManager.getCurrentBundle().getString("locale"));
            NumberFormat format = NumberFormat.getInstance(locale);

            @Override
            protected void updateItem(Integer number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(format.format(number));
            }
        });
    }

    private void setFloatCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Float>() {
            Locale locale = new Locale(UserSessionManager.getCurrentBundle().getString("locale"));
            NumberFormat format = NumberFormat.getInstance(locale);

            @Override
            protected void updateItem(Float number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(format.format(number));
            }
        });
    }

    private void setDoubleCellFactory(TableColumn tableColumn) {
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Double>() {
            Locale locale = new Locale(UserSessionManager.getCurrentBundle().getString("locale"));
            NumberFormat format = NumberFormat.getInstance(locale);

            @Override
            protected void updateItem(Double number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(format.format(number));
            }
        });
    }

    private void initMenu() {
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
        MenuItem menuItem14 = new MenuItem("Show");

        menuItem1.setOnAction(event -> openHelpScene());

        menuItem2.setOnAction(event -> {
            try {
                openInfoScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem3.setOnAction(event -> {
            try {
                openAddScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem4.setOnAction(event -> {
            try {
                openUpdateIdScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem5.setOnAction(event -> {
            try {
                openRemoveByIdScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem6.setOnAction(event -> {
            try {
                openClearScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem7.setOnAction(event -> {
            try {
                openExecuteScriptScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem8.setOnAction(event -> {
            try {
                openHeadScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem9.setOnAction(event -> {
            try {
                openAddIfMinScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem10.setOnAction(event -> {
            try {
                openRemoveGreaterScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem11.setOnAction(event -> {
            try {
                openRemoveByNationalityScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem14.setOnAction(event -> {
            try {
                openShowScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Server error
            }
        });

        menuItem12.setOnAction(event -> {
            try {
                openFilterByNationalityScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        menuItem13.setOnAction(event -> {
            try {
                openPrintDescHeightScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        commandBtn.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7,
                menuItem8, menuItem9, menuItem10, menuItem11, menuItem12, menuItem13, menuItem14);
    }
}
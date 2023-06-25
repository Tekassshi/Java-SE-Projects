package guicontrollers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import commands.Head;
import commands.Info;
import commands.PrintFieldDescendingHeight;
import commands.Show;
import data.*;
import guicontrollers.abstractions.LanguageChanger;
import interfaces.Command;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

import static guicontrollers.SessionController.loadErrPortChoosingField;

public class MainControlSceneController extends LanguageChanger implements Initializable {

    private CommandManager commandManager = UserSessionManager.getCommandManager();

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

    @FXML
    private AnchorPane visualPane;

    HashMap<String, javafx.scene.paint.Color> colorMap;
    HashMap <Integer, Label> infoMap;
    Random random = new Random();

    ContextMenu cm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorMap = new HashMap<>();
        infoMap = new HashMap<>();
        initMenu();
        initContextMenu();

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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        exitBtn.setOnAction(event -> Platform.exit());

        super.setMenuButton(languageMenu);

        Thread collectionUpdater = new Thread(new CollectionUpdater(UserSessionManager.getAuthorizationManager(),
                UserSessionManager.getConnectionManager()));
        collectionUpdater.setDaemon(true);
        collectionUpdater.start();

        tableView.setItems(generateFilteredList());

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
        String data[] = res.split(":");
        String out = UserSessionManager.getCurrentBundle().getString("Collection type:") + " " + data[0] + "\n" +
                UserSessionManager.getCurrentBundle().getString("Number of elements:") + " " + data[1] + "\n";

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
        infoRes.setText(out);
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

    private void openUpdateIdScene(String id) throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/updateId.fxml"));

        if (id != null) {
            TableViewPerson person = tableView.getSelectionModel().getSelectedItem();
            Parent root = addScene.getRoot();

            TextField idField = (TextField) root.lookup("#idField");
            idField.setText(String.valueOf(person.getId()));
            idField.setEditable(false);
            TextField eyeColorField = (TextField) root.lookup("#eyeColorField");
            eyeColorField.setText(person.getEyeColor().toString());
            TextField height = (TextField) root.lookup("#heightField");
            height.setText(person.getHeight().toString());
            TextField weight = (TextField) root.lookup("#weightField");
            weight.setText(person.getWeight().toString());
            TextField nameField = (TextField) root.lookup("#nameField");
            nameField.setText(person.getName());
            TextField nationalityField = (TextField) root.lookup("#nationalityField");
            nationalityField.setText(person.getNationality().toString());
            TextField weightField = (TextField) root.lookup("#weightField");
            weightField.setText(person.getWeight().toString());
            TextField xCoordField = (TextField) root.lookup("#xCoordField");
            xCoordField.setText(person.getXCoord().toString());
            TextField yCoordField = (TextField) root.lookup("#yCoordField");
            yCoordField.setText(person.getYCoord().toString());
            TextField xLoocField = (TextField) root.lookup("#xLoocField");
            xLoocField.setText(person.getXLooc().toString());
            TextField yLoocField = (TextField) root.lookup("#yLoocField");
            yLoocField.setText(person.getYLooc().toString());
            TextField zLoocField = (TextField) root.lookup("#zLoocField");
            zLoocField.setText(person.getZLooc().toString());
        }

        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    private void openRemoveByIdScene(String id) throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/removeById.fxml"));

        if (id != null) {
            Parent root = addScene.getRoot();
            TextField idToRemove = (TextField) root.lookup("#idField");
            idToRemove.setText(id);
            idToRemove.setEditable(false);
        }

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

        commandTask.setOnFailed(event -> {
            try {
                if (commandTask.getException() instanceof IOException)
                    loadErrPortChoosingField("Server error. Please, try later.");
                else if (commandTask.getException() instanceof JWTVerificationException)
                    loadErrPortChoosingField("Session timeout. Connect again.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

        commandTask.setOnFailed(event -> {
            try {
                if (commandTask.getException() instanceof IOException)
                    loadErrPortChoosingField("Server error. Please, try later.");
                else if (commandTask.getException() instanceof JWTVerificationException)
                    loadErrPortChoosingField("Session timeout. Connect again.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

        commandTask.setOnFailed(event -> {
            try {
                if (commandTask.getException() instanceof IOException)
                    loadErrPortChoosingField("Server error. Please, try later.");
                else if (commandTask.getException() instanceof JWTVerificationException)
                    loadErrPortChoosingField("Session timeout. Connect again.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        new Thread(commandTask).start();
    }

    @Override
    protected void changeLanguage() {
        languageMenu.setText(UserSessionManager.getCurrentBundle().getString("lang"));
        commandBtn.setText(UserSessionManager.getCurrentBundle().getString("Command"));
        filterField.setPromptText(UserSessionManager.getCurrentBundle().getString("Filter"));
        exitBtn.setText(UserSessionManager.getCurrentBundle().getString("Exit"));

        ObservableList<MenuItem> items = cm.getItems();
        items.get(0).setText(UserSessionManager.getCurrentBundle().getString("Remove"));
        items.get(1).setText(UserSessionManager.getCurrentBundle().getString("Update"));

        ObservableList<TableColumn<TableViewPerson, ?>> tmp = tableView.getColumns();
        ArrayList<String> tmp2 = new ArrayList<>(Arrays.asList("ID", "Name", "Coord X", "Coord Y", "Creation Date",
                "Height", "Weight", "Eye color", "Nationality", "Location X", "Location Y", "Location Z", "Owner"));
        for (int i = 0; i < tmp.size(); i++){
            tmp.get(i).setText(UserSessionManager.getCurrentBundle().getString(tmp2.get(i)));
        }

        tableView.refresh();
    }

    private void setZonedDateTimeCellFactory(TableColumn tableColumn) {
        String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, ZonedDateTime>() {
            @Override
            protected void updateItem(ZonedDateTime zdt, boolean empty) {
                super.updateItem(zdt, empty);
                if (empty)
                    setText(null);
                else
                    setText(CellDataFormatter.formatZonedDateTime(countryCode, zdt));
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
                    setText(CellDataFormatter.formatEnum(UserSessionManager.getCurrentBundle(), color));
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
                    setText(CellDataFormatter.formatEnum(UserSessionManager.getCurrentBundle(), country));
            }
        });
    }

    private void setLongCellFactory(TableColumn tableColumn) {
        String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Long>() {
            @Override
            protected void updateItem(Long number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(CellDataFormatter.formatLong(countryCode, number));
            }
        });
    }

    private void setIntegerCellFactory(TableColumn tableColumn) {
        String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Integer>() {

            @Override
            protected void updateItem(Integer number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(CellDataFormatter.formatInteger(countryCode, number));
            }
        });
    }

    private void setFloatCellFactory(TableColumn tableColumn) {
        String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Float>() {

            @Override
            protected void updateItem(Float number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(CellDataFormatter.formatFloat(countryCode, number));
            }
        });
    }

    private void setDoubleCellFactory(TableColumn tableColumn) {
        String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
        tableColumn.setCellFactory(col -> new TableCell<TableViewPerson, Double>() {

            @Override
            protected void updateItem(Double number, boolean empty) {
                super.updateItem(number, empty);
                if (empty)
                    setText(null);
                else
                    setText(CellDataFormatter.formatDouble(countryCode, number));
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
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem3.setOnAction(event -> {
            try {
                openAddScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem4.setOnAction(event -> {
            try {
                openUpdateIdScene(null);
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem5.setOnAction(event -> {
            try {
                openRemoveByIdScene(null);
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem6.setOnAction(event -> {
            try {
                openClearScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem7.setOnAction(event -> {
            try {
                openExecuteScriptScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem8.setOnAction(event -> {
            try {
                openHeadScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem9.setOnAction(event -> {
            try {
                openAddIfMinScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem10.setOnAction(event -> {
            try {
                openRemoveGreaterScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem11.setOnAction(event -> {
            try {
                openRemoveByNationalityScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem14.setOnAction(event -> {
            try {
                openShowScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem12.setOnAction(event -> {
            try {
                openFilterByNationalityScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        menuItem13.setOnAction(event -> {
            try {
                openPrintDescHeightScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });

        commandBtn.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7,
                menuItem8, menuItem9, menuItem10, menuItem11, menuItem12, menuItem13, menuItem14);

        animBtn.setOnAction(event -> {
            try {
                open2DScene();
            } catch (IOException e) {
                throw new RuntimeException(e); // Load error
            }
        });
    }

    private SortedList<TableViewPerson> generateFilteredList(){
        FilteredList<TableViewPerson> filteredList = new FilteredList<>(CollectionWrapper.getCollection(), b -> true);
        filterField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person ->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String countryCode = UserSessionManager.getCurrentBundle().getString("locale");
                ResourceBundle bundle = UserSessionManager.getCurrentBundle();

                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (String.valueOf(person.getId()).toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatZonedDateTime(countryCode, person.getCreationDate())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatInteger(countryCode, person.getHeight())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatFloat(countryCode, person.getWeight())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatEnum(bundle, person.getNationality())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatEnum(bundle, person.getEyeColor())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (String.valueOf(person.getUsername()).toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatLong(countryCode, person.getXCoord())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatLong(countryCode, person.getYCoord())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatInteger(countryCode, person.getXLooc())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatFloat(countryCode, person.getYLooc())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (CellDataFormatter.formatDouble(countryCode, person.getZLooc())
                        .toLowerCase().contains(lowerCaseFilter))
                    return true;
                else
                    return false;
            });
        }));

        SortedList<TableViewPerson> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        return sortedList;
    }

    private void initContextMenu(){
        cm = new ContextMenu();
        MenuItem mi1 = new MenuItem(UserSessionManager.getCurrentBundle().getString("Remove"));
        MenuItem mi2 = new MenuItem(UserSessionManager.getCurrentBundle().getString("Update"));
        cm.getItems().addAll(mi1, mi2);

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                cm.show(tableView, t.getScreenX(), t.getScreenY());
            }
        });

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.PRIMARY) {
                cm.hide();
            }
        });

        mi1.setOnAction(event -> {
            TableViewPerson person = tableView.getSelectionModel().getSelectedItem();
            String idToRemove = String.valueOf(person.getId());
            try {
                openRemoveByIdScene(idToRemove);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        mi2.setOnAction(event -> {
            TableViewPerson person = tableView.getSelectionModel().getSelectedItem();
            String idToUpdate = String.valueOf(person.getId());
            try {
                openUpdateIdScene(idToUpdate);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void open2DScene() throws IOException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        Scene scene = FXMLLoader.load(getClass().getResource("/GUI/scenes/2D.fxml"));
        visualPane = (AnchorPane) scene.lookup("#visualPane");
        stage.setScene(scene);
        visualise(false);
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void visualise(boolean refresh) {
        infoMap.clear();
        colorMap.clear();

        for (var person : tableView.getItems()) {
            var creatorName = person.getUsername();

            if (!colorMap.containsKey(creatorName)) {
                var r = random.nextDouble();
                var g = random.nextDouble();
                var b = random.nextDouble();
                if (Math.abs(r - g) + Math.abs(r - b) + Math.abs(b - g) < 0.6) {
                    r += (1 - r) / 1.4;
                    g += (1 - g) / 1.4;
                    b += (1 - b) / 1.4;
                }
                colorMap.put(creatorName, javafx.scene.paint.Color.color(r, g, b));
            }

            var size = Math.min(125, Math.max(55, 15 * 2) / 2);

            var circle = new Circle(size, colorMap.get(creatorName));
            double x = Math.abs(person.getXCoord());
            while (x >= 720) {
                x = x / 10;
            }
            double y = Math.abs(person.getYCoord());
            while (y >= 370) {
                y = y / 3;
            }
            if (y < 100) y += 125;

            var id = new Text('#' + String.valueOf(person.getId()));
            var info = new Label(describePerson(person));

            info.setVisible(false);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
                if(t.getButton() == MouseButton.SECONDARY) {
                    try {
                        openUpdateIdFrom2DScene(person);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            circle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                circle.setFill(colorMap.get(creatorName).brighter());
            });

            circle.setOnMouseExited(mouseEvent -> {
                id.setVisible(true);
                info.setVisible(false);
                circle.setFill(colorMap.get(creatorName));
            });

            id.setFont(Font.font("Segoe UI", size / 3));
            info.setStyle("-fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-width: 2");
            info.setFont(Font.font("Segoe UI", 15));

            visualPane.getChildren().add(circle);
            visualPane.getChildren().add(id);

            infoMap.put((int) person.getId(), info);
            if (!refresh) {
                var path = new Path();
                path.getElements().add(new MoveTo(-500, -150));
                path.getElements().add(new HLineTo(x));
                path.getElements().add(new VLineTo(y));
                id.translateXProperty().bind(circle.translateXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.translateYProperty().add(id.getLayoutBounds().getHeight() / 4));
                info.translateXProperty().bind(circle.translateXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.translateYProperty().subtract(120));
                var transition = new PathTransition();
                transition.setDuration(Duration.millis(750));
                transition.setNode(circle);
                transition.setPath(path);
                transition.setOrientation(PathTransition.OrientationType.NONE);
                transition.play();
            } else {
                circle.setCenterX(x);
                circle.setCenterY(y);
                info.translateXProperty().bind(circle.centerXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.centerYProperty().subtract(120));
                id.translateXProperty().bind(circle.centerXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.centerYProperty().add(id.getLayoutBounds().getHeight() / 4));
                var darker = new FillTransition(Duration.millis(750), circle);
                darker.setFromValue(colorMap.get(creatorName));
                darker.setToValue(colorMap.get(creatorName).darker().darker());
                var brighter = new FillTransition(Duration.millis(750), circle);
                brighter.setFromValue(colorMap.get(creatorName).darker().darker());
                brighter.setToValue(colorMap.get(creatorName));
                var transition = new SequentialTransition(darker, brighter);
                transition.play();
            }
        }

        for (var id : infoMap.keySet()) {
            visualPane.getChildren().add(infoMap.get(id));
        }
    }

    private void openUpdateIdFrom2DScene(TableViewPerson person) throws IOException {
        Stage stage = new Stage();
        Scene addScene = FXMLLoader.load(getClass().getResource("/GUI/scenes/commandScenes/updateId.fxml"));

            Parent root = addScene.getRoot();

            TextField idField = (TextField) root.lookup("#idField");
            idField.setText(String.valueOf(person.getId()));
            idField.setEditable(false);
            TextField eyeColorField = (TextField) root.lookup("#eyeColorField");
            eyeColorField.setText(person.getEyeColor().toString());
            TextField height = (TextField) root.lookup("#heightField");
            height.setText(person.getHeight().toString());
            TextField weight = (TextField) root.lookup("#weightField");
            weight.setText(person.getWeight().toString());
            TextField nameField = (TextField) root.lookup("#nameField");
            nameField.setText(person.getName());
            TextField nationalityField = (TextField) root.lookup("#nationalityField");
            nationalityField.setText(person.getNationality().toString());
            TextField weightField = (TextField) root.lookup("#weightField");
            weightField.setText(person.getWeight().toString());
            TextField xCoordField = (TextField) root.lookup("#xCoordField");
            xCoordField.setText(person.getXCoord().toString());
            TextField yCoordField = (TextField) root.lookup("#yCoordField");
            yCoordField.setText(person.getYCoord().toString());
            TextField xLoocField = (TextField) root.lookup("#xLoocField");
            xLoocField.setText(person.getXLooc().toString());
            TextField yLoocField = (TextField) root.lookup("#yLoocField");
            yLoocField.setText(person.getYLooc().toString());
            TextField zLoocField = (TextField) root.lookup("#zLoocField");
            zLoocField.setText(person.getZLooc().toString());

        stage.getIcons().add(new Image("/GUI/images/LOGO.png"));
        stage.initOwner(SessionController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.show();
    }

    public String describePerson(TableViewPerson person){
        String s = "";
        s += "Id: " + person.getId() + "\n";
        s += "Name: " + person.getName() + "\n";
        s += "Coordinates: x = \"" + person.getXCoord() + "\", y = \"" +
                person.getYCoord() + "\"\n";
        s += "Creation date: " + person.getCreationDate() + "\n";
        s += "Height: " + person.getHeight() + "\n";
        s += "Weight: " + person.getWeight() + "\n";
        s += "Eye color: " + person.getEyeColor() + "\n";
        s += "Nationality: " + person.getNationality() + "\n";
        s += "Location: x = \"" + person.getXLooc() + "\", y = \"" +
                person.getYLooc() + "\", z = \"" + person.getZLooc() + "\"\n";
        s += "Created by: " + person.getUsername() + "\n\n";
        return s;
    }
}
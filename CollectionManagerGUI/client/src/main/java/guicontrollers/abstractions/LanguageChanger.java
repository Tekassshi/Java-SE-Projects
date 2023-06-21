package guicontrollers.abstractions;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import util.UserSessionManager;

import java.util.ResourceBundle;

public abstract class LanguageChanger {
    @FXML
    private MenuButton languageMenu;

    public void setMenuButton(MenuButton menuButton) {
        this.languageMenu = menuButton;

        MenuItem menuItem1 = new MenuItem("EN");
        MenuItem menuItem2 = new MenuItem("ES");
        MenuItem menuItem3 = new MenuItem("RU");
        MenuItem menuItem4 = new MenuItem("SK");
        MenuItem menuItem5 = new MenuItem("SV");

        menuItem1.setOnAction(event -> {
            UserSessionManager.setCurrentBundle(ResourceBundle.getBundle("bundles.ClassBundle_en_EN"));
            changeLanguage();
        });

        menuItem2.setOnAction(event -> {
            UserSessionManager.setCurrentBundle(ResourceBundle.getBundle("bundles.ClassBundle_es_ES"));
            changeLanguage();
        });

        menuItem3.setOnAction(event -> {
            UserSessionManager.setCurrentBundle(ResourceBundle.getBundle("bundles.ClassBundle_ru_RU"));
            changeLanguage();
        });

        menuItem4.setOnAction(event -> {
            UserSessionManager.setCurrentBundle(ResourceBundle.getBundle("bundles.ClassBundle_sk_SK"));
            changeLanguage();
        });

        menuItem5.setOnAction(event -> {
            UserSessionManager.setCurrentBundle(ResourceBundle.getBundle("bundles.ClassBundle_sv_SV"));
            changeLanguage();
        });

        languageMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5);
    }

    protected abstract void changeLanguage();
}

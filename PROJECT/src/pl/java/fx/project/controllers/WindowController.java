package pl.java.fx.project.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WindowController {

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize() {
        loadLoginScreen();
    }

    private void loadLoginScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController loginController = loader.getController();
        loginController.setWindowController(this);
        mainStackPane.setPrefSize(600,500);
        setScreen(pane);
    }

    void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }

    Pane getPane() {
        return  mainStackPane;
    }
}

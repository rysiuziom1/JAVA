package pl.java.fx.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoginController {

    private WindowController windowController;

    @FXML
    private TextField directoryTextField;
    @FXML
    private Pane loginPane;
    @FXML
    private Label wrongPath;

    @FXML
    public void chooseDirectory() {
        wrongPath.setVisible(false);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) loginPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null)
            directoryTextField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    public void loginUser() {
        File file = new File(directoryTextField.getText());
        if (file.isDirectory()) {
            wrongPath.setVisible(false);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ClientScreen.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientController clientController = loader.getController();
            clientController.setWindowController(windowController);
            clientController.setHomeDirectory(directoryTextField.getText());
            clientController.init();
            windowController.setScreen(pane);
        } else {
            wrongPath.setVisible(true);
        }
    }

    void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }
}

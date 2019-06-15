package pl.java.fx.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.java.fx.project.controllers.ServerController;

public class Server extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ServerScreen.fxml"));
        StackPane stackPane = loader.load();
        ServerController serverController = loader.getController();
        Scene scene = new Scene(stackPane);
        primaryStage.setTitle("Server app");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        serverController.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

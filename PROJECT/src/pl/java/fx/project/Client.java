package pl.java.fx.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Window.fxml"));
        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

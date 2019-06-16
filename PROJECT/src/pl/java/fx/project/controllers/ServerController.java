package pl.java.fx.project.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import pl.java.fx.project.treebuilder.FileTreeBuilder;
import pl.java.fx.project.treebuilder.RefreshFileTree;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {

    @FXML
    private TextArea textArea;

    @FXML
    private TreeView<File> serverTreeView;

    @FXML
    private StackPane mainStackPane;

    private List<String> allFilesList = new ArrayList<>();

    @FXML
    public void initialize() {
        textArea.setText("DUPA");
        File serverHomeDirectory = new File("D:\\JAVASERVER");
        FileTreeBuilder fileTreeBuilder = new FileTreeBuilder(serverHomeDirectory.getAbsolutePath(), allFilesList, serverTreeView);
        fileTreeBuilder.displayTreeView();
    }

    public void init() {
        RefreshFileTree refreshTask = new RefreshFileTree(serverTreeView, allFilesList);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleWithFixedDelay(refreshTask, 0, 1, TimeUnit.SECONDS);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5056);
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }

        ServerSocket finalServerSocket = serverSocket;
        Runnable acceptClientTask = () -> Platform.runLater(() -> {
            Socket socket = null;
            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;

            try {
                socket = finalServerSocket.accept();
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream finalInputStream = inputStream;
                Runnable listenClientTask = () -> Platform.runLater(() -> {
                    try {
                        String received = (String) finalInputStream.readObject();
                        if(received.equalsIgnoreCase("Send file")) {
                            
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainStackPane.getScene().getWindow().setOnCloseRequest(e ->{
            scheduler.shutdown();
        });
    }
}

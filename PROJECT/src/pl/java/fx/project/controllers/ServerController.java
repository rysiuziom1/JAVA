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
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
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
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                socket = finalServerSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                InputStream finalInputStream = inputStream;
                OutputStream finalOutputStream = outputStream;
                Runnable listenClientTask = () -> Platform.runLater(() -> {
                    try {
                        InputStreamReader reader = new InputStreamReader(finalInputStream);
                        BufferedReader buffered = new BufferedReader(reader);
                        String received = buffered.readLine();
                        if(received.startsWith("SEND")) {
                            String fileName = received.split(" ")[1];
                            String fileOwner = received.split(" ")[2];
                            FileOutputStream out = new FileOutputStream(fileOwner + "\\" + fileName);
                            byte[] bytes = new byte[16*1024];
                            int count;
                            while((count = finalInputStream.read(bytes)) > 0) {
                                out.write(bytes, 0, count);
                            }
                            out.close();
                        }
                        else if(received.startsWith("GET")) {
                            String owner = received.split(" ")[1];
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(finalOutputStream);
                            ArrayList<String> files = new ArrayList<>();
                            FileTreeBuilder.createFileList(new File(owner), files);
                            objectOutputStream.writeObject(files);
                        }
                    } catch (IOException e) {
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

package pl.java.fx.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import pl.java.fx.project.treebuilder.FileTreeBuilder;
import pl.java.fx.project.treebuilder.RefreshFileTree;

import java.io.File;
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
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(refreshTask, 0, 1, TimeUnit.SECONDS);

        mainStackPane.getScene().getWindow().setOnCloseRequest(e ->{
            scheduler.shutdown();
        });
    }
}

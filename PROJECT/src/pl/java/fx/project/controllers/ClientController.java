package pl.java.fx.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import pl.java.fx.project.treebuilder.FileTreeBuilder;
import pl.java.fx.project.treebuilder.RefreshFileTree;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ClientController {

    private WindowController windowController;

    private String homeDirectory;
    private ArrayList<String> allFilesList = new ArrayList<>();

    @FXML
    private TreeView<File> clientTreeView;

    @FXML
    private Pane clientPane;


    void init() {
        FileTreeBuilder fileTreeBuilder = new FileTreeBuilder(homeDirectory, allFilesList, clientTreeView);
        fileTreeBuilder.displayTreeView();
        RefreshFileTree refreshTask = new RefreshFileTree(clientTreeView, allFilesList);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(refreshTask, 0, 1,TimeUnit.SECONDS);
        windowController.getPane().getScene().getWindow().setOnCloseRequest(e -> {
            scheduler.shutdown();
        });
    }

    void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }


    void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }
}

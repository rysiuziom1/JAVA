package pl.java.fx.project.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ClientController {

    private WindowController windowController;

    private String homeDirectory;

    @FXML
    private TreeView<File> clientTreeView;

    @FXML
    private Pane clientPane;

    private ArrayList<String> allFilesList = new ArrayList<>();

    void init() {
        File file = new File(homeDirectory){
            @Override
            public String toString(){
                return this.getName();
            }
        };
        displayTreeView(file);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(refreshTree, 0,1, TimeUnit.SECONDS);

        windowController.getPane().getScene().getWindow().setOnCloseRequest(e -> {
            scheduler.shutdown();
        });

    }

    private final Runnable refreshTree = () -> Platform.runLater(() -> {
        File file = new File(homeDirectory);
        ArrayList<String> filesList = new ArrayList<>();
        filesList.add(file.getName());
        TreeItem<File> newRootItem = new TreeItem<>(file);

        List<File> tempFileList = Arrays.stream(file.listFiles())
                .map(f -> new File(f.getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                })
                .collect(Collectors.toList());

        for(File f : tempFileList){
            createTree(f, newRootItem, filesList);
        }

        if(!allFilesList.equals(filesList)) {
            displayTreeView(file);
        }
    });

    private static void createTree(File file, TreeItem<File> parent, List<String> allFilesList) {
        TreeItem<File> treeItem = new TreeItem<>(file);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        allFilesList.add(file.getName());
        if (file.isDirectory()) {
            List<File> tempFileList = Arrays.stream(file.listFiles())
                    .map(f -> new File(f.getAbsolutePath()) {
                        @Override
                        public String toString() {
                            return this.getName();
                        }
                    })
                    .collect(Collectors.toList());
            for (File f : tempFileList) {
                createTree(f, treeItem, allFilesList);
            }
        }
    }

    private void displayTreeView(File file) {
        TreeItem<File> rootItem = new TreeItem<>(file);
        allFilesList.clear();
        allFilesList.add(file.getName());
        clientTreeView.setShowRoot(false);

        List<File> tempFileList = Arrays.stream(file.listFiles())
                .map(f -> new File(f.getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                })
                .collect(Collectors.toList());


        for(File f : tempFileList) {
            createTree(f, rootItem, allFilesList);
        }
        clientTreeView.setRoot(rootItem);
    }

    void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }


    void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }
}

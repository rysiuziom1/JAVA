package pl.java.fx.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("Root Node");
        TreeItem<String> nodeA = new TreeItem<>("Node A");
        TreeItem<String> nodeB = new TreeItem<>("Node B");
        TreeItem<String> nodeA1 = new TreeItem<>("Node A1");

        root.setExpanded(true);

        nodeA.setExpanded(true);

        root.getChildren().addAll(nodeA, nodeB);

        nodeA.getChildren().add(nodeA1);
        treeView.setRoot(root);

        textArea.setText("CYCE MARYSI...");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                treeView.getRoot().getChildren().add(new TreeItem<>("DUPA"));
                this.cancel();
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }

}

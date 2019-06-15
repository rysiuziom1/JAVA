package pl.java.fx.project.treebuilder;

import javafx.application.Platform;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RefreshFileTree implements Runnable {

    private TreeView<File> treeView;
    private List<String> files;

    public RefreshFileTree(TreeView<File> treeView, List<String> files) {
        this.treeView = treeView;
        this.files = files;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            FileTreeBuilder fileTreeBuilder = new FileTreeBuilder(treeView.getRoot().getValue().getAbsolutePath(), files, treeView);
            File file = treeView.getRoot().getValue();
            ArrayList<String> newFiles = new ArrayList<>();
            fileTreeBuilder.createFileList(file, newFiles);
            if(!files.equals(newFiles))
                fileTreeBuilder.displayTreeView();
        });
    }
}

package pl.java.fx.project.treebuilder;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileTreeBuilder {

    private String rootDirectory;
    private List<String> files;
    private TreeView<File> treeView;

    public FileTreeBuilder(String rootDirectory, List<String> files, TreeView<File> treeView) {
        this.files = files;
        this.rootDirectory = rootDirectory;
        this.treeView = treeView;
    }

    public static void createTree(String directory, TreeItem<File> parent, List<String> files) {
        File file = new File(directory){
            @Override
            public String toString() {
                return this.getName();
            }
        };
        TreeItem<File> treeItem = new TreeItem<>(file);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        files.add(file.getName());
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
                createTree(f.getAbsolutePath(), treeItem, files);
            }
        }
    }

    public void displayTreeView() {
        files.clear();
        File file = new File(rootDirectory){
            @Override
            public String toString() {
                return this.getName();
            }
        };
        TreeItem<File> rootItem = new TreeItem<>(file);
        files.add(file.getName());
        if(file.isDirectory()) {
            for(File f : file.listFiles())
                createTree(f.getAbsolutePath(), rootItem, files);
        }
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }

    public void createFileList(File file, List<String> files) {
        files.add(file.getName());
        if(file.isDirectory()) {
            for(File f : file.listFiles())
                createFileList(f, files);
        }
    }
}

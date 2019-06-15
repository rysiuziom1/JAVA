import javafx.scene.control.TreeItem;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        File myFile = new File("D:\\Documents\\GitHub\\POTEZNAJAVA\\FileTest\\directory") {
            @Override
            public String toString() {
                return this.getName();
            }
        };
        TreeItem<File> filesTree = new TreeItem<>(myFile);

        filesTree.getChildren().addAll(Stream.of(myFile.listFiles())
                .map(f -> new TreeItem<File>(f){
                    @Override
                    public String toString() {
                        return this.getValue().getName();
                    }
                })
                .collect(Collectors.toList()));

        System.out.println(filesTree.getChildren());
        System.out.println(myFile);
    }
}


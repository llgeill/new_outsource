package cn.client.util;

import javafx.scene.Node;
import javafx.stage.Stage;

public class DragUtil {
    public static void addDragListener(Stage stage,Node node){
        new DragListener(stage).enableDrag(node);
    }
}

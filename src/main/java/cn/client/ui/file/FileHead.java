package cn.client.ui.file;

import cn.client.pojo.ResourceFile;
import cn.client.util.MyUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URLEncoder;

public class   FileHead extends StackPane {
    private ResourceFile resourceFile;
    private Stage stage;
    private ImageView imageView;
    public FileHead(ResourceFile resourceFile, FlowPane fileBox) {
        this.resourceFile=resourceFile;
        this.setPrefHeight(125);
        this.setPrefWidth(124);
        if(resourceFile.getName().endsWith("zip")||resourceFile.getName().endsWith("rar")){
            imageView=new ImageView("uiImage/imageres_174.png");
            imageView.setFitHeight(75);
            imageView.setFitWidth(74);
        }else{
            imageView=new ImageView("uiImage/imageres_2.png");
            imageView.setFitHeight(75);
            imageView.setFitWidth(74);
        }

        this.getChildren().add(imageView);
        Label label=new Label();
        label.setAlignment(Pos.BOTTOM_CENTER);
        label.setText(resourceFile.getName());
        label.setPrefSize(185,170);
        label.setOnMouseClicked(event -> {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialFileName(resourceFile.getName());
                    File files = fileChooser.showSaveDialog(stage);
                    MyUtil.downLoadFromUrl(MyUtil.getProperties().getProperty("server.url")+resourceFile.getFileUrl()+URLEncoder.encode(resourceFile.getName(),"utf-8"),URLEncoder.encode(resourceFile.getName(),"utf-8"),files.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
        label.setOnMouseEntered(event -> {
           label.setTextFill(Color.BLUE);
           label.setUnderline(true);
        });
        label.setOnMouseExited(event -> {
            label.setTextFill(Color.BLACK);
            label.setUnderline(false);
        });
        this.getChildren().add(label);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

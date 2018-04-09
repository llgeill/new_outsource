package cn.client.ui.file;

import cn.client.pojo.FileUserVo;
import cn.client.pojo.ResourceFile;
import cn.client.util.MyUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class FileController {

    @FXML
    private StackPane fileDownload;
    @FXML
    private StackPane myFile;
    @FXML
    private StackPane uploadFile;
    @FXML
    private FlowPane fileBox;
    private Stage stage;


    public void initledAll(){

        fileDownload.setOnMouseClicked(event -> {
            fileBox.getChildren().remove(0,fileBox.getChildren().size());
            String json=MyUtil.doJsonPost("getServerAdminFile",MyUtil.toJSON(MyUtil.getLoginVo().getUser()));
            if(json!=null&&!json.equals("")){
                List<ResourceFile> list=MyUtil.parseJSONArray(json,ResourceFile.class);
                for (ResourceFile resourceFile :list) {
                    FileHead fileHead=new FileHead(resourceFile,fileBox);
                    fileHead.setStage(stage);
                    fileBox.getChildren().add(fileHead);
                }
            }

        });
        fileDownload.setOnMouseEntered(event -> {
            fileDownload.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        fileDownload.setOnMouseExited(event -> {
            fileDownload.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        });
        myFile.setOnMouseClicked(event -> {
            fileBox.getChildren().remove(0,fileBox.getChildren().size());
            String json=MyUtil.doJsonPost("getServerUserFile",MyUtil.toJSON(MyUtil.getLoginVo().getUser()));
            if(json!=null&&!json.equals("")){
                List<ResourceFile> list=MyUtil.parseJSONArray(json,ResourceFile.class);
                for (ResourceFile resourceFile :list) {
                    FileHead fileHead=new FileHead(resourceFile,fileBox);
                    fileHead.setStage(stage);
                    fileBox.getChildren().add(fileHead);
                }
            }


        });
        myFile.setOnMouseEntered(event -> {
            myFile.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        myFile.setOnMouseExited(event -> {
            myFile.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        });
        uploadFile.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("上传文件");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(stage);
            String security=MyUtil.doJsonPost("getUserSecurity",MyUtil.toJSON(MyUtil.getLoginVo().getUser()));
            MyUtil.uploadFile("upLoadImage/file",file.getName(),file);
            ResourceFile resourceFile=new ResourceFile();
            resourceFile.setId(MyUtil.getUUID());
            resourceFile.setFileUrl("file/");
            if(security.equals(""))resourceFile.setSecurity(0);
            resourceFile.setSecurity(Integer.parseInt(security));
            resourceFile.setName(file.getName());
            FileUserVo fileUserVo=new FileUserVo();
            fileUserVo.setUser(MyUtil.getLoginVo().getUser());
            fileUserVo.setResourceFile(resourceFile);
            MyUtil.doJsonPost("saveServerUserFile",MyUtil.toJSON(fileUserVo));
        });
        uploadFile.setOnMouseEntered(event -> {
            uploadFile.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        uploadFile.setOnMouseExited(event -> {
            uploadFile.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        });


    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

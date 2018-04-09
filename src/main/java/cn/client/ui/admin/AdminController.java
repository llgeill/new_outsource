package cn.client.ui.admin;

import cn.client.ui.file.FileAdminController;
import cn.client.util.AlertProxy;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class AdminController {
    @FXML
    private ImageView myTask;
    @FXML
    private ImageView otherTask;
    @FXML
    private ImageView attendance;
    @FXML
    private ImageView person;
    @FXML
    private ImageView file;
    @FXML
    private VBox leftVBox;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView closeImage;

    @FXML
    private ImageView maxImage;

    @FXML
    private ImageView minImage;


    private Stage stage;





    private boolean flag=true;
    private boolean myTaskFlag=true;
    private boolean otherTaskFlag=true;
    private boolean attendanceFlag=true;
    private boolean personFlag=true;
    private boolean fileFlag=true;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initializeAll(){
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            stage.close();
        });
        closeImage.setOnMouseEntered(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png"));
        });
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });

        maxImage.setImage(new Image("static/image/uiImage/window/window_max_normal.png"));
        maxImage.setOnMouseClicked(event -> {
            stage.setMaximized(!stage.isMaximized());
            if(stage.isMaximized()) maxImage.setImage(new Image("static/image/uiImage/window/window_restore_press.png"));
            else maxImage.setImage(new Image("static/image/uiImage/window/window_max_press.png"));
        });
        maxImage.setOnMouseEntered(event -> {
            if(stage.isMaximized()) maxImage.setImage(new Image("static/image/uiImage/window/window_restore_hover.png"));
            else maxImage.setImage(new Image("static/image/uiImage/window/window_max_hover.png"));
        });
        maxImage.setOnMouseExited(event -> {
            if(stage.isMaximized()) maxImage.setImage(new Image("static/image/uiImage/window/window_restore_normal.png"));
            else  maxImage.setImage(new Image("static/image/uiImage/window/window_max_normal.png"));
        });

        minImage.setImage(new Image("static/image/uiImage/window/window_min_normal.png"));
        minImage.setOnMouseClicked(event -> {
            minImage.setImage(new Image("static/image/uiImage/window/window_min_press.png"));
            stage.setIconified(true);
        });
        minImage.setOnMouseEntered(event -> {
            minImage.setImage(new Image("static/image/uiImage/window/window_min_hover.png"));
        });
        minImage.setOnMouseExited(event -> {
            minImage.setImage(new Image("static/image/uiImage/window/window_min_normal.png"));
        });


    }



    /*
    * 切换所有图标为灰色
    */
    public void buttonInitial(){
        flag=false;
        myTaskFlag=true;
        otherTaskFlag=true;
        attendanceFlag=true;
        personFlag=true;
        fileFlag=true;

        int i=0;
        ObservableList<Node> observableList=leftVBox.getChildren();
        for(Node node:observableList){
            ++i;
            ImageView imageView=(ImageView) node;
            imageView.setImage(new Image("static/image/uiImage/userViewImage/"+"userview"+i+".png"));
        }
    }


    /*
    * 项目
    * */
    @FXML
    private void myTaskMouse(){
        Platform.runLater(()->{
            buttonInitial();
            myTaskFlag=false;
            myTask.setImage(new Image("static/image/uiImage/userViewImage/userview1A.png"));
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/admin/AdminTaskManger.fxml"));
                AnchorPane anchorPane=loader.load();
                AdminTaskMangerController adminTaskMangerController=loader.getController();
                adminTaskMangerController.initializeAll();
                borderPane.setCenter(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    private void myTaskEnter(){
        if(flag&&myTaskFlag)myTask.setImage(new Image("static/image/uiImage/userViewImage/userview1B.png"));
    }

    @FXML
    private void myTaskExit(){
        if(flag&&myTaskFlag) myTask.setImage(new Image("static/image/uiImage/userViewImage/userview1.png"));
        flag=true;
    }

    /*
    * 成员查看与审核
    * */
    @FXML
    private void otherTaskMouse(){
        Platform.runLater(()->{
            buttonInitial();
            otherTaskFlag=false;
            otherTask.setImage(new Image("static/image/uiImage/userViewImage/userview2A.png"));
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/admin/AdminPersonManger.fxml"));
                AnchorPane anchorPane=loader.load();
                AdminPersonMangerController adminPersonMangerController=loader.getController();
                adminPersonMangerController.initializeAll();
                borderPane.setCenter(anchorPane);
            } catch (IOException e) {
                AlertProxy.showMessage("报错了！！");
                e.printStackTrace();
            }
        });

    }

    @FXML
    private void otherTaskEnter(){
        if(flag&&otherTaskFlag)otherTask.setImage(new Image("static/image/uiImage/userViewImage/userview2B.png"));
    }

    @FXML
    private void otherTaskExit(){
        if(flag&&otherTaskFlag) otherTask.setImage(new Image("static/image/uiImage/userViewImage/userview2.png"));
        flag=true;
    }


    /*
    * 考勤
    * */
    @FXML
    private void attendanceMouse(){
        Platform.runLater(()->{
            buttonInitial();
            attendanceFlag=false;
            attendance.setImage(new Image("static/image/uiImage/userViewImage/userview3A.png"));
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/admin/CompanyAttendance.fxml"));
                AnchorPane anchorPane= null;
                anchorPane = loader.load();
                AdminAttendanceController adminAttendanceController=loader.getController();
                adminAttendanceController.initializeAll();
                borderPane.setCenter(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void attendanceEnter(){
        if(flag&&attendanceFlag)attendance.setImage(new Image("static/image/uiImage/userViewImage/userview3B.png"));
    }

    @FXML
    private void attendanceExit(){
        if(flag&&attendanceFlag) attendance.setImage(new Image("static/image/uiImage/userViewImage/userview3.png"));
        flag=true;
    }


    /*
     * 进度展示
     * */
    @FXML
    private void personMouse() throws IOException {
        Platform.runLater(()->{

            try {
                buttonInitial();
                personFlag=false;
                person.setImage(new Image("static/image/uiImage/userViewImage/userview4A.png"));
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/admin/AdminProgress.fxml"));
                AnchorPane message= null;
                message = loader.load();
                AdminProgressController adminProgressController=loader.getController();
                adminProgressController.initializeAll();
                borderPane.setCenter(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    @FXML
    private void personEnter(){
        if(flag&&personFlag)person.setImage(new Image("static/image/uiImage/userViewImage/userview4B.png"));
    }

    @FXML
    private void personExit(){
        if(flag&&personFlag) person.setImage(new Image("static/image/uiImage/userViewImage/userview4.png"));
        flag=true;
    }




    /*
     * 进度展示
     * */
    @FXML
    private void fileMouse() throws IOException {
        Platform.runLater(()->{

            try {
                buttonInitial();
                fileFlag=false;
                file.setImage(new Image("static/image/uiImage/userViewImage/userview5A.png"));
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/file/FileDownloadAndUploadAdmin.fxml"));
                AnchorPane anchorPane= null;
                anchorPane = loader.load();
                FileAdminController fileAdminController=loader.getController();
                fileAdminController.initledAll();
                fileAdminController.setStage(stage);
                borderPane.setCenter(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    @FXML
    private void fileEnter(){
        if(flag&&personFlag)file.setImage(new Image("static/image/uiImage/userViewImage/userview5B.png"));
    }

    @FXML
    private void fileExit(){
        if(flag&&personFlag) file.setImage(new Image("static/image/uiImage/userViewImage/userview5.png"));
        flag=true;
    }
}

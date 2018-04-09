package cn.client.ui.user;

import cn.client.pojo.Attendance;
import cn.client.pojo.AttendanceUserVo;
import cn.client.ui.file.FileController;
import cn.client.util.MyUtil;
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
import java.sql.Timestamp;
import java.util.Date;


public class UserController {
    @FXML
    private ImageView myTask;
    @FXML
    private ImageView otherTask;
    @FXML
    private ImageView attendance;
    @FXML
    private ImageView person;
    @FXML
    private VBox leftVBox;

    @FXML
    private ImageView closeImage;

    @FXML
    private ImageView maxImage;

    @FXML
    private ImageView minImage;


    private Stage stage;


    @FXML
    private BorderPane borderPane;



    private boolean flag=true;
    private boolean myTaskFlag=true;
    private boolean otherTaskFlag=true;
    private boolean attendanceFlag=true;
    private boolean personFlag=true;

    public void initializeAll(){
        stage.setResizable(true);
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            MyUtil.endAttendance(0);
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


    public void startAttendance(){
            if(MyUtil.getLoginVo().getUser()!=null){
                Attendance attendance=new Attendance();
                attendance.setId(MyUtil.getUUID());
                attendance.setStartTime(new Timestamp(new Date().getTime()));
                AttendanceUserVo attendanceUserVo=new AttendanceUserVo();
                attendanceUserVo.setAttendance(attendance);
                attendanceUserVo.setUser(MyUtil.getLoginVo().getUser());
                MyUtil.setAttendanceUserVo(attendanceUserVo);
                MyUtil.doJsonPost("/saveAttendance",MyUtil.toJSON(attendanceUserVo));
            }
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
        int i=0;
        ObservableList<Node> observableList=leftVBox.getChildren();
        for(Node node:observableList){
            ++i;
            ImageView imageView=(ImageView) node;
            imageView.setImage(new Image("static/image/uiImage/userViewImage/"+"userview"+i+".png"));
        }
    }


    /*
    * 我的任务
    * */
    @FXML
    private void myTaskMouse(){
        Platform.runLater(()->{
            buttonInitial();
            myTaskFlag=false;
            myTask.setImage(new Image("static/image/uiImage/userViewImage/userview1A.png"));
            try {
                AnchorPane test = FXMLLoader.load(this.getClass().getResource("/static/fxml/user/UserTask.fxml"));
                borderPane.setCenter(test);
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
    * 可接任务
    * */
    @FXML
    private void otherTaskMouse(){
        Platform.runLater(()->{
            buttonInitial();
            otherTaskFlag=false;
            otherTask.setImage(new Image("static/image/uiImage/userViewImage/userview2A.png"));
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/file/FileDownloadAndUpload.fxml"));
                AnchorPane anchorPane=loader.load();
                FileController fileController=loader.getController();
                fileController.initledAll();
                fileController.setStage(stage);
                borderPane.setCenter(anchorPane);
            } catch (IOException e) {
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
    * 用户考勤
    * */
    @FXML
    private void attendanceMouse(){
        Platform.runLater(()->{
            buttonInitial();
            attendanceFlag=false;
            attendance.setImage(new Image("static/image/uiImage/userViewImage/userview3A.png"));
            try {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/user/UserAttendance.fxml"));
                AnchorPane anchorPane=loader.load();
                UserAttendanceController userAttendanceController=loader.getController();
                userAttendanceController.setAdminBorderPane(borderPane);
                userAttendanceController.initializeAll(MyUtil.getLoginVo().getUser());
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
     * 个人信息
     * */
    @FXML
    private void personMouse(){
        Platform.runLater(()->{
            buttonInitial();
            personFlag=false;
            person.setImage(new Image("static/image/uiImage/userViewImage/userview4A.png"));
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/user/Message.fxml"));
                AnchorPane message=loader.load();
                MessageController MessageController =loader.getController();
                MessageController.getImage();
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


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

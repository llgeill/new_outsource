package cn.client;

import cn.client.arcsoft.demo.AFRTest;
import cn.client.pojo.LoginVo;
import cn.client.ui.admin.AdminController;
import cn.client.ui.login.LoginDialog;
import cn.client.ui.user.UserController;
import cn.client.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginDialog loginDialog = new LoginDialog(primaryStage);
        Optional<LoginVo> optionalUser = loginDialog.showAndWait();
        optionalUser.ifPresent(loginVo -> {
            //打开界面
            if (loginVo.getUser() != null) {
                //用户显示主界面
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("/static/fxml/user/userInterface.fxml"));
                    AnchorPane userAnchorPane = loader.load();
                    UserController userController = loader.getController();
                    userController.setStage(primaryStage);
                    userController.initializeAll();
                    userController.startAttendance();
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(userAnchorPane, 1000, 800);
                    scene.setFill(Color.TRANSPARENT);
                    DragUtil.addDragListener(primaryStage, ((BorderPane) userAnchorPane.getChildren().get(0)).getTop());
                    DrawUtil.addDrawFunc(primaryStage, userAnchorPane);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //开启后台考勤功能
                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                KeyFrame keyFrame = new KeyFrame(Duration.minutes(2000), event -> {
                    try {
                        MyCode myCode = new MyCode(false, "");
                        for (int i = 0; i < 3; i++) {
                            myCode = AFRTest.startForBackground("/static/image/faceImage/" + loginVo.getUser().getFaceImage());
                            Thread.currentThread().sleep(5000);
                        }
                        if (!myCode.isSuccess()) {
                            MyUtil.endAttendance(1);
                            primaryStage.close();
                            timeline.stop();
                            System.exit(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }


                });
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();

            } else if (loginVo.getAdmin() != null) {
                //管理员显示主界面
                try {
                    FXMLLoader loader = new FXMLLoader();
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    loader.setLocation(this.getClass().getResource("/static/fxml/admin/adminInterface.fxml"));
                    AnchorPane adminAnchorPane = loader.load();
                    AdminController adminController = loader.getController();
                    adminController.setStage(primaryStage);
                    adminController.initializeAll();
                    Scene scene = new Scene(adminAnchorPane, 1000, 800);
                    scene.setFill(Color.TRANSPARENT);
                    DragUtil.addDragListener(primaryStage, ((BorderPane) adminAnchorPane.getChildren().get(0)).getTop());
                    DrawUtil.addDrawFunc(primaryStage, adminAnchorPane);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

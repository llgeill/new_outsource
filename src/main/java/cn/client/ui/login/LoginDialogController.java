package cn.client.ui.login;

import cn.client.arcsoft.demo.AFRTest;
import cn.client.pojo.Admin;
import cn.client.pojo.LoginVo;
import cn.client.pojo.User;
import cn.client.ui.register.RegisterDialog;
import cn.client.util.AlertProxy;
import cn.client.util.MyCode;
import cn.client.util.MyUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static cn.client.util.MyUtil.downLoadFromUrl;


public class LoginDialogController {
    @FXML
    private DialogPane loginDialogPane;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView closeImage;

    @FXML
    private ImageView landerImage;

    @FXML
    private Label landerLabel;

    @FXML
    private ImageView minImage;
//
//    @FXML
//    private Label registerLabel;

    private Stage stage;
    private Dialog dialog;
    private boolean isAdmin=false;


    @FXML
    private void initialize(){
        Platform.runLater(() -> username.requestFocus());
        //loginButton.setDefaultButton(true);
        login();
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            dialog.setResult("");
            dialog.close();
        });
        closeImage.setOnMouseEntered(event -> closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png")));
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });
        landerImage.setImage(new Image("static/image/uiImage/user.png"));
        landerImage.setOnMouseClicked(event -> {
            if(!isAdmin) {
                landerImage.setImage(new Image("static/image/uiImage/admin.png"));
                landerLabel.setText("管理员登录");
            }
            else{
                landerImage.setImage(new Image("static/image/uiImage/user.png"));
                landerLabel.setText("用户登录");
            }
            isAdmin=!isAdmin;
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
        register();
    }

    public void minimize(){

    }
    public void lander(){

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void login(){
        ButtonType buttonType=loginDialogPane.getButtonTypes().get(1);
        Button button= (Button) loginDialogPane.lookupButton(buttonType);
        button.setText("√  安 全 登 录  ");
        button.setTextFill(Color.WHITE);
        button.setPrefWidth(215);
        button.setPrefHeight(30);

        button.setTranslateX(-105);
        button.setStyle("-fx-background-color: #05BAFB");
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: cyan");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: #05BAFB");
        });
        ButtonType buttonTypes=loginDialogPane.getButtonTypes().get(0);
        Button buttons= (Button) loginDialogPane.lookupButton(buttonTypes);
        buttons.setTextFill(Color.GRAY);
        buttons.setOnMouseEntered(event -> {
            buttons.setTextFill(Color.valueOf("#05BAFB"));
        });
        buttons.setOnMouseExited(event -> {
            buttons.setTextFill(Color.GRAY);
        });
        button.addEventFilter(ActionEvent.ACTION, event->{
            if(username.getText().trim().equals("")){
                AlertProxy.showErrorAlert("用户名不能为空！");
                event.consume();
            }else if(password.getText().trim().equals("")){
                AlertProxy.showErrorAlert("密码不能为空！");
                event.consume();
            }else{
                if(!isAdmin){
                    User user=new User();
                    User users=null;
                    user.setName(username.getText().trim());
                    user.setPassword(password.getText().trim());
                    String json=MyUtil.doJsonPost("findUser",MyUtil.toJSON(user));
                    if(!json.equals("")){
                        users=MyUtil.parseJSON(json, User.class);
                        MyUtil.getLoginVo().setUser(users);
                        if(users!=null&&users.getAudit()!=1) {
                            AlertProxy.showErrorAlert("账号正在审核中。。。。");
                            event.consume();
                        }else{
                            try {
                                File file=new File(this.getClass().getResource("/static").getPath()+"/"+users.getFaceImage());
                                if(!file.exists()){
                                    MyUtil.downLoadFromUrlSmall(MyUtil.getProperties().getProperty("server.url")+"image/faceImage/"+users.getFaceImage(),this.getClass().getResource("/static").getPath()+"/"+users.getFaceImage());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            MyCode myCode = AFRTest.startForLogin("/static/"+users.getFaceImage());
//                            if (!myCode.isSuccess()){
//                                AlertProxy.showErrorAlert(myCode.getMessage());
//                                event.consume();
//                            }
                        }
                    }else{
                        AlertProxy.showErrorAlert("用户名或密码错误！");
                        event.consume();
                    }
                }else{
                    Admin admin=new Admin();
                    Admin admins=null;
                    admin.setName(username.getText().trim());
                    admin.setPassword(password.getText().trim());
                    String adminJson=MyUtil.doJsonPost("findAdmin",MyUtil.toJSON(admin));
                    if(!adminJson.equals("")){
                        admins=MyUtil.parseJSON(adminJson,Admin.class);
                        MyUtil.getLoginVo().setAdmin(admins);
                    }else{
                        AlertProxy.showErrorAlert("用户名或密码错误！");
                        event.consume();
                    }

                }
            }
        });
    }

    public void register(){
        ButtonType buttonType=loginDialogPane.getButtonTypes().get(0);
        Button button= (Button) loginDialogPane.lookupButton(buttonType);
        button.setText("注册");
        button.setMaxWidth(50);
        button.setPrefHeight(30);
        button.setTranslateX(-130);
        button.setStyle("-fx-background-color:trans" +"parency");
        button.addEventFilter(ActionEvent.ACTION, event-> {
            RegisterDialog registerDialog=new RegisterDialog(stage);
            Optional<LoginVo> optionalUser=registerDialog.showAndWait();
            optionalUser.ifPresent(loginVo -> {
                if(loginVo.getUser()!=null){
                    registerDialog.close();
                    AlertProxy.showMessage("账号注册成功，请等待管理员审核......");
                }
            });
            event.consume();
        });

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

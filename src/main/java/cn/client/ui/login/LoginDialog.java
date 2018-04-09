package cn.client.ui.login;


import cn.client.pojo.LoginVo;
import cn.client.util.DragUtil;
import cn.client.util.MyUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginDialog extends Dialog<LoginVo> {
    private DialogPane dialogPane;
    private LoginDialogController loginDialogController;


    public LoginDialog(Stage stage) {
        super();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/login/Login.fxml"));
            dialogPane=loader.load();

            loginDialogController=loader.getController();
            this.initStyle(StageStyle.TRANSPARENT);
            //dialogPane.setStyle("-fx-border-color: lightskyblue;-fx-border-width: 1px");
            loginDialogController.setStage(stage);
            loginDialogController.setDialog(this);
            DragUtil.addDragListener(stage,dialogPane.getContent());
            this.setTitle("外包人员管理系统");
            this.setDialogPane(dialogPane);
            this.setResultConverter(dialogButton -> {
                if (dialogButton != ButtonType.CANCEL){
                    return MyUtil.getLoginVo();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

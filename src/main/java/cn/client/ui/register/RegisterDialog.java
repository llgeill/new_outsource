package cn.client.ui.register;


import cn.client.pojo.LoginVo;
import cn.client.util.MyUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RegisterDialog extends Dialog<LoginVo> {
    private DialogPane dialogPane;
    private RegisterDialogController registerDialogController;



    public RegisterDialog(Stage stage) {
        super();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/register/RegisterDialog.fxml"));
            this.initStyle(StageStyle.TRANSPARENT);
            dialogPane=loader.load();
            registerDialogController=loader.getController();
            registerDialogController.setStage(stage);
            registerDialogController.setDialog(this);
            registerDialogController.initializeAll();
            this.setTitle("外包人员管理系统");
            this.setDialogPane(dialogPane);
            this.setResultConverter(dialogButton -> {
                if (dialogButton == dialogPane.getButtonTypes().get(0)) {
                    return MyUtil.getLoginVo();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}

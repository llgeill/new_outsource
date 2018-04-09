package cn.client.ui.user;


import cn.client.pojo.UserTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by GMUK on 2018/3/19 0019.
 */
public class ShowUserTaskDetail extends Dialog<UserTask> {
    public ShowUserTaskDetail(UserTask userTask) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/user/UserTaskDetailDialog.fxml"));
            DialogPane dialogPane= null;
            dialogPane = loader.load();
            this.initStyle(StageStyle.TRANSPARENT);
            ShowUserTaskDetailController showUserTaskDetailController=loader.getController();
            showUserTaskDetailController.showUserTaskDetail(userTask);
            this.setDialogPane(dialogPane);
            this.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

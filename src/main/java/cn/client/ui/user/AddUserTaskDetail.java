package cn.client.ui.user;

import cn.client.pojo.UserTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by GMUK on 2018/3/21 0021.
 */
public class AddUserTaskDetail extends Dialog<UserTask>{
    public AddUserTaskDetail(VBox claimTasks) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/user/AddUserTaskDialog.fxml"));
            DialogPane dialogPane= null;
            dialogPane = loader.load();
            this.initStyle(StageStyle.TRANSPARENT);
            AddUserTaskDetailController addUserTaskDetailController=loader.getController();
            addUserTaskDetailController.setDialog(this);
            addUserTaskDetailController.addUserTask(claimTasks);
            addUserTaskDetailController.initliedAll();
            this.setDialogPane(dialogPane);
            this.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

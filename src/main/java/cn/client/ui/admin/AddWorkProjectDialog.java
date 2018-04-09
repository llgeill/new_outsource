package cn.client.ui.admin;


import cn.client.pojo.WorkProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddWorkProjectDialog extends Dialog<WorkProject> {
    private AddWorkProjectDialogController addWorkProjectDialogController;
    public AddWorkProjectDialog() {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/AddWorkProjectDialog.fxml"));
            DialogPane dialogPane=loader.load();
            addWorkProjectDialogController=loader.getController();
            addWorkProjectDialogController.setDialog(this);
            this.initStyle(StageStyle.TRANSPARENT);
            this.setDialogPane(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(WorkProject workProject){
        addWorkProjectDialogController.setData(workProject);
    }
}

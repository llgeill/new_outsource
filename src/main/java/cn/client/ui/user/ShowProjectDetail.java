package cn.client.ui.user;


import cn.client.pojo.Company;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by GMUK on 2018/3/19 0019.
 */
public class ShowProjectDetail extends Dialog<Company> {
    public ShowProjectDetail(Company company) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/user/ShowProjectDialog.fxml"));
            DialogPane dialogPane= null;
            this.initStyle(StageStyle.TRANSPARENT);
            dialogPane = loader.load();
            ShowProjectDetailController showProjectDetailController=loader.getController();
            showProjectDetailController.showProjectDetail(company);
            this.setDialogPane(dialogPane);
            this.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package cn.client.ui.admin;


import cn.client.pojo.Company;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ShowCompanyDialog extends Dialog<Company> {

    public ShowCompanyDialog(Company company) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/ShowCompanyDialog.fxml"));
            DialogPane dialogPane=loader.load();
            ShowCompanyDialogController showCompanyDialogController=loader.getController();
            showCompanyDialogController.flush(company);
            this.initStyle(StageStyle.TRANSPARENT);
            this.setDialogPane(dialogPane);
            this.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

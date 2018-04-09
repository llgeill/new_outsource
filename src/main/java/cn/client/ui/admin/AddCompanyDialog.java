package cn.client.ui.admin;


import cn.client.pojo.Company;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddCompanyDialog extends Dialog<Company> {

    public AddCompanyDialog() {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/AddCompanyDialog.fxml"));
            DialogPane dialogPane=loader.load();
             AddCompanyDialogController addCompanyDialogController= loader.getController();
             addCompanyDialogController.setDialog(this);
            this.initStyle(StageStyle.TRANSPARENT);
            this.setDialogPane(dialogPane);
            this.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

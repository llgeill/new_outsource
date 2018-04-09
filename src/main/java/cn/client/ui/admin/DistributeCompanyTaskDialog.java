package cn.client.ui.admin;


import cn.client.pojo.Company;
import cn.client.pojo.CompanyTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DistributeCompanyTaskDialog extends Dialog<Company> {
    private DistributeCompanyTaskDialogController controller;

    public DistributeCompanyTaskDialog(CompanyTask companyTask) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/DistributeCompanyTask.fxml"));
            DialogPane dialogPane=loader.load();
            controller=loader.getController();
            controller.setCompanyTask(companyTask);
            this.initStyle(StageStyle.TRANSPARENT);
            controller.initializeAll();
            this.setDialogPane(dialogPane);
            this.setResultConverter(dialogButton -> {
                if (dialogButton == dialogPane.getButtonTypes().get(1)) {
                    return new Company();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DistributeCompanyTaskDialogController getController() {
        return controller;
    }

    public void setController(DistributeCompanyTaskDialogController controller) {
        this.controller = controller;
    }
}

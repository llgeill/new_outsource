package cn.client.ui.admin;


import cn.client.pojo.CompanyTask;
import cn.client.pojo.WorkProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddCompanyTaskDialog extends Dialog<WorkProject> {
    private AddCompanyTaskDialogController addCompanyTaskDialogController;
    private WorkProject workProject;

    public AddCompanyTaskDialog(WorkProject workProject) {
        try {
            this.workProject=workProject;
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/AddCompanyTaskDialog.fxml"));
            DialogPane dialogPane=loader.load();
            addCompanyTaskDialogController=loader.getController();
            addCompanyTaskDialogController.setDialog(this);
            this.initStyle(StageStyle.TRANSPARENT);
            addCompanyTaskDialogController=loader.getController();
            addCompanyTaskDialogController.setWorkProject(workProject);
            this.setDialogPane(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDate(CompanyTask companyTask){
        addCompanyTaskDialogController.setDate(companyTask);
    }

    public void submitToNo(CompanyTask companyTask){
        addCompanyTaskDialogController.submitToNo(companyTask);
    }
    public void submitToHave(CompanyTask companyTask){
        addCompanyTaskDialogController.submitToHave(companyTask);
    }
    public void submitToYes(CompanyTask companyTask){
        addCompanyTaskDialogController.submitToYes(companyTask);
    }

}

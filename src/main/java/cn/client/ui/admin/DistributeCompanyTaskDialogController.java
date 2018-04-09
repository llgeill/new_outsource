package cn.client.ui.admin;


import cn.client.pojo.Company;
import cn.client.pojo.CompanyTask;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public class DistributeCompanyTaskDialogController extends Dialog {

    @FXML
    private DialogPane distributeCompanyTaskDialogPane;

    @FXML
    private ComboBox<String> distributeCompanyTask;
    @FXML
    private ImageView closeImage;


    public Company company;
    private CompanyTask companyTask=null;
    public List<Company> list;
    private Stage stage;


    @FXML
    private void initialize(){
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            stage.close();
        });
        closeImage.setOnMouseEntered(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png"));
        });
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initializeAll(){
        submit();
        String json=MyUtil.doJsonPost("findAllCompany","");
        list=MyUtil.parseJSONArray(json,Company.class);
        distributeCompanyTask.getItems().add("请选择任务所属公司");
        distributeCompanyTask.setValue("请选择任务所属公司");
        String id="";
        if(MyUtil.getCompanyTaskUI().getCp()!=null){id=MyUtil.getCompanyTaskUI().getCp().getId();}
        for(Company company:list){
            if(company.getCompanyTask()!=null){
                if(company.getCompanyTask().getId().equals(id))this.company=company;
            }else{
                distributeCompanyTask.getItems().add(company.getName());
            }

        }
    }

    public  void personSubmit(){
        if(company!=null&&company.getCompanyTask()!=null){
            company.setCompanyTask(companyTask);
            MyUtil.doJsonPost("saveCompany",MyUtil.toJSON(company));
        }
    }

    public int submit(){
        ButtonType buttonType1=distributeCompanyTaskDialogPane.getButtonTypes().get(0);
        Button butoon1= (Button) distributeCompanyTaskDialogPane.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=distributeCompanyTaskDialogPane.getButtonTypes().get(1);
        Button butoon= (Button) distributeCompanyTaskDialogPane.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("保存");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(distributeCompanyTask.getValue().equals("请选择任务所属公司")){
                AlertProxy.showErrorAlert("没有选择任务所属公司！");
                event.consume();
            }else{
                for(Company company:list){
                    if(distributeCompanyTask.getValue().equals(company.getName())){
                        company.setCompanyTask(companyTask);
                        MyUtil.doJsonPost("saveCompany",MyUtil.toJSON(company));
                    }
                }
            }
        });
        return 0;
    }

    public CompanyTask getCompanyTask() {
        return companyTask;
    }

    public void setCompanyTask(CompanyTask companyTask) {
        this.companyTask = companyTask;
    }
}

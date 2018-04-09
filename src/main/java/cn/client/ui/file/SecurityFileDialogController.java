package cn.client.ui.file;


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

public class SecurityFileDialogController extends Dialog {

    @FXML
    private DialogPane securityFileDialogPane;

    @FXML
    private ComboBox<String> distributeCompanyTask;
    @FXML
    private ImageView closeImage;


    public Company company;
    private CompanyTask companyTask=null;
    public List<Company> list;
    private Stage stage;
    private int security;


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
        submit();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initializeAll(){
        String json=MyUtil.doJsonPost("findAllCompany","");
        list=MyUtil.parseJSONArray(json,Company.class);
        distributeCompanyTask.getItems().add("请选择公司分配任务");
        distributeCompanyTask.setValue("请选择公司分配任务");
        //安全等级  0普通 1一般 2重大 3特大
        String[]strings={"普通安全等级","一般安全等级","重大安全等级","特大安全等级"};
        distributeCompanyTask.getItems().addAll(strings);
    }

    public  void personSubmit(){
        if(company!=null&&company.getCompanyTask()!=null){
            company.setCompanyTask(companyTask);
            MyUtil.doJsonPost("saveCompany",MyUtil.toJSON(company));
        }
    }

    public void submit(){
        ButtonType buttonType1=securityFileDialogPane.getButtonTypes().get(0);
        Button butoon1= (Button) securityFileDialogPane.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=securityFileDialogPane.getButtonTypes().get(1);
        Button butoon= (Button) securityFileDialogPane.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("保存");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(distributeCompanyTask.getValue().equals("请选择公司分配任务")){
                AlertProxy.showErrorAlert("没有选择所需要分配任务的公司！");
                event.consume();
            }else{
                String choice= distributeCompanyTask.getValue();
                if(choice.equals("普通安全等级")){
                    this.security=0;
                }else if(choice.equals("一般安全等级")){
                    this.security=1;
                }else if(choice.equals("重大安全等级")){
                    this.security=2;
                }else if(choice.equals("特大安全等级")){
                    this.security=3;
                }
            }
        });
    }

    public CompanyTask getCompanyTask() {
        return companyTask;
    }

    public void setCompanyTask(CompanyTask companyTask) {
        this.companyTask = companyTask;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }
}

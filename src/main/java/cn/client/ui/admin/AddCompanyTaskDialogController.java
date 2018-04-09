package cn.client.ui.admin;


import cn.client.pojo.CompanyTask;
import cn.client.pojo.WorkProject;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddCompanyTaskDialogController extends Dialog {

    @FXML
    private DialogPane addCompanyTaskDialog;
    @FXML
    private TextField name;
    @FXML
    private TextArea content;
    @FXML
    private ComboBox<String> completeState;
    @FXML
    private ImageView closeImage;

    private Dialog dialog;

    private WorkProject workProject;

    private String id="";

    @FXML
    private void initialize(){
        completeState.getItems().addAll("未认领","已认领","已完成");
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            dialog.close();
        });
        closeImage.setOnMouseEntered(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png"));
        });
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });
        submit();
    }

    public void setDate(CompanyTask companyTask){
        if(companyTask.getCompleteState()==-1){
            completeState.setValue("未认领");
        }else if(companyTask.getCompleteState()==0){
            completeState.setValue("已认领");
        }else{
            completeState.setValue("已完成");
        }
        id=companyTask.getId();
        name.setText(companyTask.getName());
        content.setText(companyTask.getContent());
    }
    private void submit(){
        ButtonType buttonType1=addCompanyTaskDialog.getButtonTypes().get(0);
        Button butoon1= (Button) addCompanyTaskDialog.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=addCompanyTaskDialog.getButtonTypes().get(1);
        Button butoon= (Button) addCompanyTaskDialog.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("确定");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(name.getText().trim().equals("")){
                AlertProxy.showErrorAlert("没有填写项目名称！");
                event.consume();
            }else if(content.getText().trim().equals("")){
                AlertProxy.showErrorAlert("没有填写项目内容！");
                event.consume();
            }
            else {
                CompanyTask companyTask=new CompanyTask();
                if(!id.equals(""))companyTask.setId(id);
                else companyTask.setId(MyUtil.getUUID());
                companyTask.setContent(content.getText());
                companyTask.setName(name.getText());
                if( completeState.getValue().equals("未认领")){
                    companyTask.setCompleteState(-1);
                }else if(completeState.getValue().equals("已认领")){
                    companyTask.setCompleteState(0);
                }else{
                    companyTask.setCompleteState(1);
                }
                companyTask.setWorkProject(workProject);
                MyUtil.doJsonPost("saveCompanyTask",MyUtil.toJSON(companyTask));
            }
        });
    }
    public void submitToNo(CompanyTask companyTask){
        companyTask.setCompleteState(-1);
        MyUtil.doJsonPost("saveCompanyTask",MyUtil.toJSON(companyTask));
    }
    public void submitToHave(CompanyTask companyTask){
        companyTask.setCompleteState(0);
        MyUtil.doJsonPost("saveCompanyTask",MyUtil.toJSON(companyTask));
    }
    public void submitToYes(CompanyTask companyTask){
        companyTask.setCompleteState(1);
        MyUtil.doJsonPost("saveCompanyTask",MyUtil.toJSON(companyTask));
    }

    public WorkProject getWorkProject() {
        return workProject;
    }

    public void setWorkProject(WorkProject workProject) {
        this.workProject = workProject;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

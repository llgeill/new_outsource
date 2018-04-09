package cn.client.ui.admin;


import cn.client.pojo.WorkProject;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Date;

public class AddWorkProjectDialogController extends Dialog {

    @FXML
    private DialogPane addWorkProjectDialog;
    @FXML
    private TextField name;
    @FXML
    private TextArea content;
    @FXML
    private ComboBox<String> security;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ImageView closeImage;

    private Dialog dialog;

    private String id="";
    @FXML
    private void initialize(){
        security.getItems().addAll("普通","一般","重大","特大");
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


    public void setData(WorkProject workProject){
        id=workProject.getId();
        name.setText(workProject.getName());
        content.setText(workProject.getContent());
        if(workProject.getSecurity()==0){
            security.setValue("普通");
        }else if(workProject.getSecurity()==1){
            security.setValue("一般");
        }else if(workProject.getSecurity()==2){
            security.setValue("重大");
        }else if(workProject.getSecurity()==3){
            security.setValue("特大");
        }
        startDate.setValue(workProject.getStartDate().toLocalDate());
        endDate.setValue(workProject.getEndDate().toLocalDate());
    }

    private void submit(){
        ButtonType buttonType1=addWorkProjectDialog.getButtonTypes().get(0);
        Button butoon1= (Button) addWorkProjectDialog.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=addWorkProjectDialog.getButtonTypes().get(1);
        Button butoon= (Button) addWorkProjectDialog.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("确定");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(name.getText().trim().equals("")){
                AlertProxy.showErrorAlert("没有填写项目名称！");
                event.consume();
            }else if(content.getText().trim().equals("")){
                AlertProxy.showErrorAlert("没有填写项目内容！");
                event.consume();
            }else if(security.getValue()==null||security.getValue().equals("")){
                AlertProxy.showErrorAlert("没有填写安全等级！");
                event.consume();
            }else if(startDate.getValue()==null){
                AlertProxy.showErrorAlert("没有填写项目开始时间！");
                event.consume();
            }else if(endDate.getValue()==null){
                AlertProxy.showErrorAlert("没有填写项目结束时间！");
                event.consume();
            }
            else {
                WorkProject workProject=new WorkProject();
                if(!id.equals(""))workProject.setId(id);
                else workProject.setId(MyUtil.getUUID());
                workProject.setAdmin(MyUtil.getLoginVo().getAdmin());
                workProject.setContent(content.getText());
                workProject.setName(name.getText());
                workProject.setStartDate(Date.valueOf(startDate.getValue()));
                workProject.setEndDate(Date.valueOf(endDate.getValue()));
                if(security.getValue().equals("普通")){
                    workProject.setSecurity(0);
                }else if(security.getValue().equals("一般")){
                    workProject.setSecurity(1);
                }else if(security.getValue().equals("重大")){
                    workProject.setSecurity(2);
                }else if(security.getValue().equals("特大")){
                    workProject.setSecurity(3);
                }
                MyUtil.doJsonPost("SaveWorkProject",MyUtil.toJSON(workProject));
            }
        });

    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

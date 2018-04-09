package cn.client.ui.admin;

import cn.client.pojo.*;
import cn.client.util.MyUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;


public class WorkProjectUI extends StackPane {
    private WorkProject cp;
    public Label label= new Label();
    private BorderPane adminBorderPane;
    private  AnchorPane firstAnchorPane;
    private StackPane addCompanyTask;
    private VBox one;
    private VBox two;
    private VBox three;

    public  WorkProjectUI(WorkProject workProject,BorderPane adminBorderPane){
        this.adminBorderPane=adminBorderPane;
        this.cp=workProject;
        this.firstAnchorPane=(AnchorPane) adminBorderPane.getCenter();
        this.setMinHeight(55);
        this.setMinWidth(178);
        this.setPrefHeight(55);
        this.setPrefWidth(165);
        this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        label.setFont(new Font(12));
        label.setText(cp.getName());
        this.getChildren().add(label);
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        this.setOnMouseExited(event -> {
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        });
    }

    public WorkProjectUI(WorkProject workProject,AdminTaskMangerController controller, BorderPane adminBorderPane, VBox one,VBox two,VBox three) {
        this.one=one;
        this.two=two;
        this.three=three;
        this.adminBorderPane=adminBorderPane;
        this.cp=workProject;
        this.firstAnchorPane=(AnchorPane) adminBorderPane.getCenter();
        this.setMinHeight(55);
        this.setMinWidth(178);
        this.setPrefHeight(55);
        this.setPrefWidth(165);
        this.setStyle(" -fx-border-color: white white #949494 white;-fx-border-width: 1px;-fx-background-color: pink");
        label.setFont(new Font(12));
        label.setText(cp.getName());
        this.getChildren().add(label);
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        this.setOnMouseExited(event -> {
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
        });
        this.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                AddWorkProjectDialog addWorkProjectDialog=new AddWorkProjectDialog();
                addWorkProjectDialog.setData(this.cp);
                addWorkProjectDialog.showAndWait();
                controller.flushDate();
            }else{
                flush();
                addCompanyTask.setOnMouseClicked(event1->{
                    AddCompanyTaskDialog addCompanyTaskDialog=new  AddCompanyTaskDialog(cp);
                    addCompanyTaskDialog.showAndWait();
                    flush();
                });
            }
        });

    }
    public void flush(){
        //清空未领取Vbox列表
        one.getChildren().remove(2,one.getChildren().size());
        addCompanyTask=(StackPane) one.getChildren().get(1);
       // one.getChildren().remove(1);
        //清空已领取Vbox列表
        two.getChildren().remove(1,two.getChildren().size());
        //清空已完成Vbox列表
        three.getChildren().remove(1,three.getChildren().size());
        String json=MyUtil.doJsonPost("findAllByWorkProject",MyUtil.toJSON(cp));
        List<CompanyTask> list=MyUtil.parseJSONArray(json,CompanyTask.class);
        for(CompanyTask companyTask:list){
            CompanyTaskUI companyTaskUI=new CompanyTaskUI(companyTask,this,cp,adminBorderPane);
            if(companyTask.getCompleteState()==-1){
                one.getChildren().add(companyTaskUI);
            }else if(companyTask.getCompleteState()==0){
                two.getChildren().add(companyTaskUI);
            }else if(companyTask.getCompleteState()==1){
                three.getChildren().add(companyTaskUI);
            }
        }
        //one.getChildren().add(addCompanyTask);

    }


}

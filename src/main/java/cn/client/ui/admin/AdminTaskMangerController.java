package cn.client.ui.admin;

import cn.client.pojo.WorkProject;
import cn.client.util.MyUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class AdminTaskMangerController {
    @FXML
    public VBox workProjectList;
    @FXML
    private VBox companyTaskNo;
    @FXML
    private VBox companyTaskHave;
    @FXML
    private VBox companyTaskYes;
    @FXML
    private BorderPane adminBorderPane;

    private StackPane addWorkProject;
    private StackPane addCompanyTask;
    private StackPane NoTitle;
    private StackPane HaveTitle;
    private StackPane YesTitle;

    public void initializeAll(){
        addWorkProject= (StackPane) workProjectList.getChildren().get(0);
        companyTaskNo.setOnDragOver(event -> {event.acceptTransferModes(TransferMode.ANY); });
        companyTaskHave.setOnDragOver(event -> {event.acceptTransferModes(TransferMode.ANY); });
        companyTaskYes.setOnDragOver(event -> {event.acceptTransferModes(TransferMode.ANY); });

        companyTaskNo.setOnDragDropped(event -> {
            if(MyUtil.getCompanyTaskUI()!=null){
                CompanyTaskUI companyTaskUI= (CompanyTaskUI) event.getGestureSource();
                VBox vBox= (VBox) companyTaskUI.getParent();
                if(vBox.getId().equals(companyTaskYes.getId())||vBox.getId().equals(companyTaskHave.getId())){
                    DistributeCompanyTaskDialog distributeCompanyTaskDialog=new DistributeCompanyTaskDialog(null);
                    distributeCompanyTaskDialog.getController().personSubmit();
                    MyUtil.getCompanyTaskUI().turnToNo();
                }
            }
        });
        companyTaskHave.setOnDragDropped(event -> {
            if(MyUtil.getCompanyTaskUI()!=null){
                 CompanyTaskUI companyTaskUI= (CompanyTaskUI) event.getGestureSource();
                 VBox vBox= (VBox) companyTaskUI.getParent();
                if(vBox.getId().equals(companyTaskNo.getId())){
                    //显示Dialog
                    DistributeCompanyTaskDialog distributeCompanyTaskDialog=new DistributeCompanyTaskDialog(MyUtil.getCompanyTaskUI().getCp());
                    Optional optional=distributeCompanyTaskDialog.showAndWait();
                    optional.ifPresent(company->{
                        MyUtil.getCompanyTaskUI().turnToHave();
                    });
                }else if(vBox.getId().equals(companyTaskYes.getId())){
                    MyUtil.getCompanyTaskUI().turnToHave();
                }

            }
        });
        companyTaskYes.setOnDragDropped(event -> {
            if(MyUtil.getCompanyTaskUI()!=null){
                CompanyTaskUI companyTaskUI= (CompanyTaskUI) event.getGestureSource();
                VBox vBox= (VBox) companyTaskUI.getParent();
                if(vBox.getId().equals(companyTaskNo.getId())){
                    //显示Dialog
                    DistributeCompanyTaskDialog distributeCompanyTaskDialog=new DistributeCompanyTaskDialog(MyUtil.getCompanyTaskUI().getCp());
                    Optional optional=distributeCompanyTaskDialog.showAndWait();
                    optional.ifPresent(company->{
                        MyUtil.getCompanyTaskUI().turnToYes();
                    });
                }else if(vBox.getId().equals(companyTaskHave.getId())){
                    MyUtil.getCompanyTaskUI().turnToYes();
                }

            }
        });
        flushDate();
    }

    public void flushDate(){
        workProjectList.getChildren().remove(1,workProjectList.getChildren().size());
        String json=MyUtil.doJsonPost("/findAllWorkProjectByAdmin",MyUtil.toJSON(MyUtil.getLoginVo().getAdmin()));
        List<WorkProject> list=MyUtil.parseJSONArray(json,WorkProject.class);
        for(WorkProject workProject:list){
            WorkProjectUI workProjectUI=new WorkProjectUI(workProject,this,adminBorderPane,companyTaskNo,companyTaskHave,companyTaskYes);
            workProjectList.getChildren().add(workProjectUI);
        }
        //workProjectList.getChildren().add(addWorkProject);
    }

    @FXML
    private void addWorkProjectClick(){
        AddWorkProjectDialog addWorkProjectDialog=new AddWorkProjectDialog();
        addWorkProjectDialog.showAndWait();
        flushDate();
    }

    @FXML
    private void addWorkProjectEnter(Event event){
        StackPane stackPane= (StackPane) event.getSource();
        stackPane.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:  #ffa5fc");
    }

    @FXML
    private void addWorkProjectExit(Event event){
        StackPane stackPane= (StackPane) event.getSource();
        stackPane.setStyle("-fx-border-color: white white #949494 white;;-fx-background-color: pink");
    }

}

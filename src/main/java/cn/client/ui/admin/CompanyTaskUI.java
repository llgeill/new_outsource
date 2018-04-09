package cn.client.ui.admin;

import cn.client.pojo.Company;
import cn.client.pojo.CompanyTask;
import cn.client.pojo.WorkProject;
import cn.client.util.MyUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class CompanyTaskUI extends StackPane{
    private CompanyTask cp;
    private WorkProject workProject;
    private WorkProjectUI workProjectUI;
    private BorderPane adminBorderPane;
    private  AnchorPane firstAnchorPane;
    Label label= new Label();
    Label labelCompany= new Label();
    public CompanyTaskUI(CompanyTask companyTask,WorkProjectUI workProjectUI, WorkProject workProject,BorderPane adminBorderPane) {
        this.workProjectUI=workProjectUI;
        this.workProject=workProject;
        this.adminBorderPane=adminBorderPane;
        this.cp=companyTask;
        this.firstAnchorPane=(AnchorPane) adminBorderPane.getCenter();
        this.setMinHeight(55);
        this.setMinWidth(178);
        this.setPrefHeight(55);
        this.setPrefWidth(165);
        if(cp.getCompleteState()==-1){
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:RGB(25,122,182)");
            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #0090ff");//  #ffca29  //  #53e4a8
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: RGB(25,122,182)");//RGB(239,172,39) // RGB(92,198,139)
            });
        }else if(cp.getCompleteState()==0){
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:RGB(239,172,39)");
            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffca29");//  #ffca29  //  #53e4a8
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: RGB(239,172,39)");//RGB(239,172,39) // RGB(92,198,139)
            });
        }else if(cp.getCompleteState()==1){
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:RGB(92,198,139)");

            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #53e4a8");//  #ffca29  //  #53e4a8
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: RGB(92,198,139)");//RGB(239,172,39) // RGB(92,198,139)
            });
        }
        label.setFont(new Font(12));
        label.setText("任务名称："+cp.getName());
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPrefSize(178,55);
        label.setTextFill(Color.WHITE);
        String json=MyUtil.doJsonPost("findByCompanyTaskId",companyTask.getId());
        if(json!=null&&!json.equals("")){
            Company company=MyUtil.parseJSON(json,Company.class);
            labelCompany.setText(company.getName());
        }else{
            labelCompany.setText("无");
        }
        labelCompany.setTextFill(Color.WHITE);
        labelCompany.setFont(new Font(12));
        labelCompany.setAlignment(Pos.BOTTOM_RIGHT);
        labelCompany.setPrefSize(178,55);

        this.getChildren().addAll(label,labelCompany);

        this.setOnMouseClicked(event -> {
            if(event.getClickCount()==2){
                AddCompanyTaskDialog addCompanyTaskDialog=new AddCompanyTaskDialog(workProject);
                addCompanyTaskDialog.setDate(cp);
                addCompanyTaskDialog.showAndWait();
                workProjectUI.flush();
            }

        });
        this.setOnDragDetected(event -> {
            Dragboard dragboard=this.startDragAndDrop(TransferMode.ANY);
            //拖曳文件视图
            //dragboard.setDragView(new Image("static/image/faceImage/llx.jpg"));
            ClipboardContent content=new ClipboardContent();
            content.putString("");
            dragboard.setContent(content);
            MyUtil.setCompanyTaskUI(this);
            event.consume();
        });

    }

    public void flush(){

    }

    public void turnToNo(){
        AddCompanyTaskDialog addCompanyTaskDialog=new AddCompanyTaskDialog(workProject);
        addCompanyTaskDialog.submitToNo(cp);
        workProjectUI.flush();

    }
    public void turnToHave(){
        AddCompanyTaskDialog addCompanyTaskDialog=new AddCompanyTaskDialog(workProject);
        addCompanyTaskDialog.submitToHave(cp);
        workProjectUI.flush();
    }
    public void turnToYes(){
        AddCompanyTaskDialog addCompanyTaskDialog=new AddCompanyTaskDialog(workProject);
        addCompanyTaskDialog.submitToYes(cp);
        workProjectUI.flush();
    }

    public CompanyTask getCp() {
        return cp;
    }

    public void setCp(CompanyTask cp) {
        this.cp = cp;
    }
}

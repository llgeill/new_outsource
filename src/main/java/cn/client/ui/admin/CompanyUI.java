package cn.client.ui.admin;

import cn.client.pojo.Company;
import cn.client.pojo.User;
import cn.client.util.MyUtil;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.List;


public class CompanyUI extends StackPane {
    private Company cp;
    public Label label= new Label();
    private BorderPane adminBorderPane;
    private  AnchorPane firstAnchorPane;
    public CompanyUI(Company company, BorderPane adminBorderPane) {
        this.adminBorderPane=adminBorderPane;
        this.cp=company;
        this.firstAnchorPane=(AnchorPane) adminBorderPane.getCenter();
        this.setMinHeight(55);
        this.setMinWidth(9);
        this.setPrefHeight(55);
        this.setPrefWidth(9);
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
            if(event.getClickCount()==2){
                ShowCompanyDialog showCompanyDialog=new ShowCompanyDialog(cp);
                flush();
            }else{
               flush();
            }

        });
    }

    public void flush(){
        ScrollPane scrollPane= (ScrollPane) firstAnchorPane.getChildren().get(0);
        FlowPane flowPane= (FlowPane) scrollPane.getContent();
        flowPane.getChildren().remove(0,flowPane.getChildren().size());
        //用户列表
        String json=MyUtil.doJsonPost("findUserByCompanyId",MyUtil.toJSON(cp));
        List<User> list=MyUtil.parseJSONArray(json,User.class);
        for(User user:list){
            UserHead userHead=new UserHead(user,adminBorderPane,this);
            flowPane.getChildren().add(userHead);
        }
        adminBorderPane.setCenter(firstAnchorPane);
    }
}

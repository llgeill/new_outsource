package cn.client.ui.admin;

import cn.client.pojo.User;
import cn.client.util.MyUtil;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;


public class UserHead extends StackPane {

    private CompanyUI companyUI;
    public UserHead(User user, BorderPane adminBorderPane,CompanyUI companyUI) {
        this.companyUI=companyUI;
        this.setPrefHeight(165);
        this.setPrefWidth(164);
        ImageView imageView=new ImageView(MyUtil.getProperties().getProperty("server.url")+"image/faceImage/"+user.getFaceImage());
        imageView.setFitHeight(115);
        imageView.setFitWidth(90);
        this.getChildren().add(imageView);
        Label label=new Label();
        label.setAlignment(Pos.BOTTOM_CENTER);
        if(user.getRole().equals("admin")){
            label.setFont(new Font(14));
            label.setTextFill(Color.RED);
        }else{
            label.setFont(new Font(14));
        }
        if(user.getAudit()!=1){
            imageView.setOpacity(0.2);
        }
        label.setText(user.getName()+"—"+(user.getRole().equals("admin")?"负责人":"成员"));
        label.setPrefSize(185,170);
        this.getChildren().add(label);
        this.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/admin/UserHeadDetail.fxml"));
                AnchorPane anchorPane=loader.load();
                UserHeadDetailController userHeadDetailController=loader.getController();
                userHeadDetailController.setUser(user);
                userHeadDetailController.setBorderPane(adminBorderPane);
                userHeadDetailController.setCompanyUI(companyUI);
                userHeadDetailController.initializeAll();
                adminBorderPane.setCenter(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

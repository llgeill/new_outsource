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

public class CompanyUIForAttendance extends StackPane {
    private Company cp;
    public Label label= new Label();
    private BorderPane adminBorderPane;
    private AnchorPane firstAnchorPane;
    public static AnchorPane prepane;
    public CompanyUIForAttendance(Company company, BorderPane adminBorderPane) {
        this.adminBorderPane = adminBorderPane;
        this.firstAnchorPane = (AnchorPane) adminBorderPane.getCenter();
        this.cp = company;
        this.setMinHeight(55);
//        this.setMinWidth(190);
        this.setPrefHeight(55);
        this.setPrefWidth(999);
        this.setStyle(" -fx-border-color: white white #949494 white;-fx-border-width: 1px;-fx-background-color: pink");
        label.setFont(new Font(12));
        label.setText(cp.getName());
        this.getChildren().add(label);
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
        });
        this.setOnMouseExited(event -> {
            this.setStyle(" -fx-border-color: white white #949494 white;-fx-border-width: 1px;-fx-background-color: pink");
        });
        this.setOnMouseClicked(event -> {
            ScrollPane scrollPane=(ScrollPane) firstAnchorPane.getChildren().get(0);
            FlowPane flowPane =(FlowPane) scrollPane.getContent();
            flowPane.getChildren().remove(0,flowPane.getChildren().size());
            //用户列表
            String json = MyUtil.doJsonPost("/findUserByCompanyId", MyUtil.toJSON(company));
            List<User> list = MyUtil.parseJSONArray(json, User.class);
            for (User user : list) {
                UserForAttendance userForAttendance = new UserForAttendance(user, adminBorderPane);
                flowPane.getChildren().add(userForAttendance);
            }
            adminBorderPane.setCenter(firstAnchorPane);
        });
    }
}

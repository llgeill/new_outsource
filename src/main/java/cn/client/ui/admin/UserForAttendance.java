package cn.client.ui.admin;

import cn.client.pojo.Attendance;
import cn.client.pojo.User;
import cn.client.ui.user.UserAttendanceController;
import cn.client.util.MyUtil;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class UserForAttendance extends StackPane {
   // private CompanyUI companyUI;
    public UserForAttendance(User user, BorderPane adminBorderPane) {
        //this.companyUI=companyUI;
        this.setPrefHeight(165);
        this.setPrefWidth(164);
        //ImageView imageView=new ImageView("static/image/faceImage/llx.jpg");
        ImageView imageView=new ImageView("http://localhost:7700/image/faceImage/"+user.getFaceImage());
        imageView.setFitHeight(115);
        imageView.setFitWidth(90);
        this.getChildren().add(imageView);
        Label label=new Label();
        label.setAlignment(Pos.BOTTOM_CENTER);
        String json= MyUtil.doJsonPost("/getAttendance", MyUtil.toJSON(user));
        List<Attendance> list= MyUtil.parseJSONArray(json,Attendance.class);
        long workTimeSum=0;
        for (Attendance attendance:list) {
            workTimeSum+=(attendance.getEndTime().getTime()-attendance.getStartTime().getTime());
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(workTimeSum-8*60*60*1000);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DAY_OF_MONTH);
        int hour = calendar2.get(Calendar.HOUR_OF_DAY);//24小时制
//      int hour = calendar2.get(Calendar.HOUR);//12小时制
        int minute = calendar2.get(Calendar.MINUTE);
        int second = calendar2.get(Calendar.SECOND);
        System.out.println(workTimeSum);
        //GregorianCalendar gc=new GregorianCalendar();
        //gc.setTime(new Date(workTimeSum));
        //label.setText(user.getName()+"—"+new SimpleDateFormat("HH:mm:ss").format(gc.getTime()));
        label.setText(user.getName()+"—"+hour+"小时"+minute+"分"+second+"秒");
        label.setPrefSize(185,170);
        this.getChildren().add(label);
        this.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/static/fxml/user/UserAttendance.fxml"));
                AnchorPane anchorPane= loader.load();
                UserAttendanceController userAttendanceController=loader.getController();
                userAttendanceController.setAdminBorderPane(adminBorderPane);
                userAttendanceController.initializeAll(user);
                userAttendanceController.byAdminView(user);
                adminBorderPane.setCenter(anchorPane);
               // borderPane.setCenter(anchorPane);
//                FXMLLoader loader=new FXMLLoader();
//                loader.setLocation(this.getClass().getResource("/static/fxml/admin/UserAttendance.fxml"));
//                AnchorPane anchorPane=loader.load();
//                UserHeadDetailController userHeadDetailController=loader.getController();
//                userHeadDetailController.setUser(user);
//                userHeadDetailController.setBorderPane(adminBorderPane);
//                userHeadDetailController.setCompanyUI(companyUI);
//                userHeadDetailController.initializeAll();
//                adminBorderPane.setCenter(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

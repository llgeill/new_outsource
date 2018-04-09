package cn.client.ui.user;

import cn.client.pojo.Attendance;
import cn.client.pojo.User;
import cn.client.util.MyUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserAttendanceController {
    @FXML
    private Label dateLable;

    @FXML
    private Label attendanceTitle;

    @FXML
    private GridPane gridPane;

    private List<Attendance> attendanceList=null;
    private  FXMLLoader loader=new FXMLLoader();
    private DialogPane dialogPane;
    private AnchorPane firstAnchorPane;

    private BorderPane adminBorderPane;

    @FXML
    void initialize(){
        try {
            loader.setLocation(this.getClass().getResource("/static/fxml/user/UserAttendanceDetail.fxml"));
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeAll(User user){
        firstAnchorPane=(AnchorPane) adminBorderPane.getCenter();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        dateLable.setText(year+"年"+month+"月");
        String json=MyUtil.doJsonPost("/getAttendance",MyUtil.toJSON(user));
        attendanceList=MyUtil.parseJSONArray(json,Attendance.class);
        flushDate(year,month);
    }
    public  long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();

        return field.between(startTime, endTime);
    }

    private String getNowMonth(){
        int monthIndex=dateLable.getText().indexOf("年");
        String thisMonth=dateLable.getText().substring(monthIndex+1,dateLable.getText().indexOf("月"));
        return thisMonth;
    }
    private String getNowYear(){
        int monthYear=dateLable.getText().indexOf("年",0);
        String thisYear=dateLable.getText().substring(0,monthYear);
        return thisYear;
    }

    private void flushDate(int thisYear,int thisMonth){
        Calendar calendar=Calendar.getInstance();
        calendar.set(thisYear,thisMonth-1,1);
        int maxDays=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i=0;i<31;i++){
            long allTime=0;
            StackPane stackPane= (StackPane) gridPane.getChildren().get(i);
            ImageView imageView= (ImageView) stackPane.getChildren().get(0);
            Label label= (Label) stackPane.getChildren().get(1);
            for(Attendance attendance:attendanceList){
                LocalDateTime startTime=attendance.getStartTime().toLocalDateTime();
                LocalDateTime endTime=attendance.getEndTime().toLocalDateTime();
                if(startTime.getDayOfMonth()==i+1&&startTime.getMonth().getValue()==thisMonth&&startTime.getYear()==thisYear)
                allTime+=betweenTwoTime(startTime,endTime,ChronoUnit.MINUTES);
            }
            int hour=Integer.parseInt(MyUtil.getProperties().getProperty("user.attendance"));
            if(allTime>=hour){
                imageView.setImage(new Image("static/image/uiImage/userViewImage/attendenceYes.jpg"));
                //stackPane.setStyle("-fx-background-color: aquamarine");
            }else{
                imageView.setImage(null);
            }


            label.setText(""+(i+1));
            if(i+1>maxDays){
                label.setText("");
                imageView.setImage(null);
            }

        }
        for(int i=0;i<31;i++){
            StackPane stackPane= (StackPane) gridPane.getChildren().get(i);
            int finalI = i;
            stackPane.setOnMouseClicked(event ->{
                    UserAttendanceDetailController userAttendanceDetailController  =loader.getController();
                    userAttendanceDetailController.initializeAll(attendanceList, finalI +1,thisMonth,thisYear);
                    Dialog dialog=new Dialog();
                    dialog.initStyle(StageStyle.TRANSPARENT);
                    dialog.setDialogPane(dialogPane);
                    dialog.showAndWait();
            });
            if(i>=maxDays)
                stackPane.setOnMouseClicked(event ->{
            });
        }

    }
    @FXML
    private void lastMonth(){
        int thisMonth=Integer.parseInt(getNowMonth())-1;
        int thisYear=Integer.parseInt(getNowYear());
        if(thisMonth==0){
            thisYear=thisYear-1;
            thisMonth=12;
        }
        dateLable.setText(thisYear+"年"+thisMonth+"月");
        flushDate(thisYear,thisMonth);
    }
    @FXML
    private void nextMonth(){
        int thisMonth=Integer.parseInt(getNowMonth())+1;
        int thisYear=Integer.parseInt(getNowYear());
        if(thisMonth==13){
            thisYear=thisYear+1;
            thisMonth=1;
        }
        dateLable.setText(thisYear+"年"+thisMonth+"月");
        flushDate(thisYear,thisMonth);
    }
    public void byAdminView(User user){
        attendanceTitle.setText("返回");
        attendanceTitle.setOnMouseClicked(event -> {
            System.out.println(""+firstAnchorPane);
            adminBorderPane.setCenter(firstAnchorPane);

        });
    }

    public void setAdminBorderPane(BorderPane adminBorderPane) {
        this.adminBorderPane = adminBorderPane;
    }
}

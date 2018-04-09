package cn.client.ui.user;

import cn.client.pojo.Attendance;
import cn.client.pojo.AttendanceDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.util.List;


public class UserAttendanceDetailController {
    @FXML
    private TableView<AttendanceDate> tableView;

    @FXML
    private TableColumn<AttendanceDate, Timestamp> startColumn;
    @FXML
    private TableColumn<AttendanceDate, Timestamp> endColumn;
    @FXML
    private TableColumn<AttendanceDate, String> statusColumn;
    @FXML
    private DialogPane userAttendanceDetail;

    private List<Attendance> attendanceList=null;


    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        //statusColumn.setCellValueFactory(cellData ->cellData.getValue().getStatus());
    }

    public void initializeAll(List<Attendance> attendanceList,int dayOfMonth,int month,int year){

        ButtonType buttonType1=userAttendanceDetail.getButtonTypes().get(0);
        Button butoon1= (Button) userAttendanceDetail.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ObservableList<AttendanceDate> attendanceDates = FXCollections.observableArrayList();
        for(Attendance attendance:attendanceList){
            if(attendance.getStartTime().toLocalDateTime().getDayOfMonth()==dayOfMonth&&attendance.getStartTime().toLocalDateTime().getYear()==year&&attendance.getStartTime().toLocalDateTime().getMonth().getValue()==month){
                AttendanceDate attendanceDate=new AttendanceDate();
                attendanceDate.setId(attendance.getId());
                if(attendance.getStatus()==1)attendanceDate.setStatus("异常退出");
                else attendanceDate.setStatus("正常");
                attendanceDate.setStartTime(attendance.getStartTime());
                attendanceDate.setEndTime(attendance.getEndTime());
                attendanceDates.add(attendanceDate);
            }
        }
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        endColumn.setCellValueFactory(cellData ->  cellData.getValue().endTimeProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        tableView.setItems(attendanceDates);

    }

}

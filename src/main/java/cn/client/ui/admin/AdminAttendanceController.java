package cn.client.ui.admin;

import cn.client.pojo.Company;
import cn.client.util.MyUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminAttendanceController {
    @FXML
    public VBox companyList;
    @FXML
    private BorderPane StaffAttendancePance;


    public void initializeAll(){
        flushDate();
    }

    public void flushDate(){
        companyList.getChildren().remove(0,companyList.getChildren().size());
        String json= MyUtil.doJsonPost("/findAllByAdmin", MyUtil.toJSON(MyUtil.getLoginVo().getAdmin()));
        List<Company> list= MyUtil.parseJSONArray(json,Company.class);
        for(Company company:list){
            CompanyUIForAttendance companyUIForAttendance=new CompanyUIForAttendance(company,StaffAttendancePance);
            companyList.getChildren().add(companyUIForAttendance);
        }
    }

    @FXML
    private void Company_MouseClick(){
        //System.out.println("Company_MouseClick");
        //AddCompanyDialog dialog=new AddCompanyDialog();
       // flushDate();

    }

    @FXML
    private void Company_MouseEnter(Event event){
        //initializeAll();
//        StackPane stackPane= (StackPane) event.getSource();
//        stackPane.setStyle("-fx-border-color: #949494;-fx-background-color: #707070");
    }

    @FXML
    private void Company_MouseExit(Event event){
      //  StackPane stackPane= (StackPane) event.getSource();
       // stackPane.setStyle("-fx-border-color: #949494;-fx-background-color: #DBDBDB");
    }
}

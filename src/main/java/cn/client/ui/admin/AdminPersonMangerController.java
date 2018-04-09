package cn.client.ui.admin;

import cn.client.pojo.Company;
import cn.client.util.MyUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminPersonMangerController {
    @FXML
    public VBox companyList;
    @FXML
    private BorderPane adminBorderPane;

    private StackPane addCompany;

    public void initializeAll(){
       // addCompany= (StackPane) companyList.getChildren().get(0);
        flushDate();
    }

    public void flushDate(){
        companyList.getChildren().remove(1,companyList.getChildren().size());
        String json=MyUtil.doJsonPost("/findAllByAdmin",MyUtil.toJSON(MyUtil.getLoginVo().getAdmin()));
        List<Company> list=MyUtil.parseJSONArray(json,Company.class);
        for(Company company:list){
            CompanyUI companyUI=new CompanyUI(company,adminBorderPane);
            companyList.getChildren().add(companyUI);
        }
        //companyList.getChildren().add(addCompany);
    }

    @FXML
    private void addCompanyClick(){
        AddCompanyDialog dialog=new AddCompanyDialog();
        flushDate();
    }

    @FXML
    private void addCompanyEnter(Event event){
        StackPane stackPane= (StackPane) event.getSource();
        stackPane.setStyle("-fx-border-color: white white #949494 white;-fx-background-color: #ffa5fc");
    }

    @FXML
    private void addCompanyExit(Event event){
        StackPane stackPane= (StackPane) event.getSource();
        stackPane.setStyle("-fx-border-color: white white #949494 white;-fx-background-color:pink");
    }

}

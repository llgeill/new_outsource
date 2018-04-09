package cn.client.ui.admin;

import cn.client.pojo.WorkProject;
import cn.client.util.MyUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;


public class AdminProgressController {
    @FXML
    public VBox workProjectList;
    @FXML
    private BorderPane adminBorderPane;
    @FXML
    private BarChart workProjectChart;
    @FXML
    private CategoryAxis cate;
    @FXML
    private NumberAxis number;
    @FXML
    private ProgressBar workProjectProgressBar;

    @FXML
    private ProgressIndicator workProjectProgressIndicator;



    public void initializeAll(){
        flushDate();
    }

    public void flushDate(){
        workProjectList.getChildren().remove(0,workProjectList.getChildren().size());
        String json=MyUtil.doJsonPost("/findAllWorkProjectByAdmin",MyUtil.toJSON(MyUtil.getLoginVo().getAdmin()));
        List<WorkProject> list=MyUtil.parseJSONArray(json,WorkProject.class);
        for(WorkProject workProject:list){
            CompanyProgressUI companyProgressUI=new CompanyProgressUI(workProject,adminBorderPane);
            companyProgressUI.setCate(cate);
            companyProgressUI.setNumber(number);
            companyProgressUI.setWorkProjectChart(workProjectChart);
            companyProgressUI.setProgressBar(workProjectProgressBar);
            companyProgressUI.setProgressIndicator(workProjectProgressIndicator);
            workProjectList.getChildren().add(companyProgressUI);
        }

    }


}

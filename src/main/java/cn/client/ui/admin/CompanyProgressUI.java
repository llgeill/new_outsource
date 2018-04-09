package cn.client.ui.admin;

import cn.client.pojo.CompanyForTaskVo;
import cn.client.pojo.UserTask;
import cn.client.pojo.WorkProject;
import cn.client.util.MyUtil;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.List;


public class CompanyProgressUI extends WorkProjectUI {

    private BarChart workProjectChart;
    private CategoryAxis cate;
    private NumberAxis number;
    private WorkProject workProject;
    private ProgressIndicator progressIndicator;
    private ProgressBar progressBar;

    public CompanyProgressUI(WorkProject workProject, BorderPane adminBorderPane) {
        super(workProject,adminBorderPane);
        this.workProject=workProject;
        this.setOnMouseClicked(event -> {
            workProjectChart.getData().remove(0,workProjectChart.getData().size());
            flush();
        });
    }


    @Override
    public void flush() {
        workProjectChart.setTitle("外包公司任务完成进度");
        cate.setLabel("公司名");
        cate.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("未认领", "已认领", "已完成")));
        number.setLabel("数量");
        String json =MyUtil.doJsonPost("findForChart",MyUtil.toJSON(workProject));
        List<CompanyForTaskVo> list=MyUtil.parseJSONArray(json,CompanyForTaskVo.class);
        int allyesSize=0;
        int count=0;
        for(CompanyForTaskVo companyForTaskVo:list){
            int noSize=0;
            int haveSize=0;
            int yesSize=0;
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(companyForTaskVo.getCompany().getName());
            if(companyForTaskVo.getUserTaskList()==null){
                series.getData().add(new XYChart.Data<>("未认领", 0));
                series.getData().add(new XYChart.Data<>("已认领", 0));
                series.getData().add(new XYChart.Data<>("已完成", 0));
            }else{
                count+=companyForTaskVo.getUserTaskList().size();
                for(UserTask userTask:companyForTaskVo.getUserTaskList()){
                    if(userTask.getCompleteState().equals("-1"))noSize++;
                    else if(userTask.getCompleteState().equals("0"))haveSize++;
                    else if(userTask.getCompleteState().equals("1")){
                        yesSize++;
                        allyesSize++;
                    }
                }
                series.getData().add(new XYChart.Data<>("未认领", noSize));
                series.getData().add(new XYChart.Data<>("已认领", haveSize));
                series.getData().add(new XYChart.Data<>("已完成", yesSize));
            }
            workProjectChart.getData().add(series);
        }
        progressIndicator.setProgress((double) allyesSize/count);
        progressBar.setProgress((double) allyesSize/count);

    }

    public BarChart getWorkProjectChart() {
        return workProjectChart;
    }

    public void setWorkProjectChart(BarChart workProjectChart) {
        this.workProjectChart = workProjectChart;
    }

    public CategoryAxis getCate() {
        return cate;
    }

    public void setCate(CategoryAxis cate) {
        this.cate = cate;
    }

    public NumberAxis getNumber() {
        return number;
    }

    public void setNumber(NumberAxis number) {
        this.number = number;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}

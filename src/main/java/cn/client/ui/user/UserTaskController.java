package cn.client.ui.user;

import cn.client.pojo.Company;
import cn.client.pojo.UserTask;
import cn.client.pojo.UserTaskLabel;
import cn.client.util.MyUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by GMUK on 2018/3/10 0010.
 */
public class  UserTaskController implements Initializable{
    @FXML
    private VBox acceptTasks;
    @FXML
    private VBox finishTasks;
    @FXML
    private VBox claimTasks;
    @FXML
    private Button projectName;

    private List<UserTaskLabel> userTaskLabels=new ArrayList<UserTaskLabel>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<VBox> vBoxes=new ArrayList<>();
        vBoxes.add(claimTasks);vBoxes.add(acceptTasks);vBoxes.add(finishTasks);
        initLists(vBoxes);
        initVBox(vBoxes);
        initProjectName();
    }

    public void initLists(List<VBox> vBoxes){
        UserTaskLabel userTaskLabel;
        for (VBox vBox:vBoxes){
            vBox.getChildren().remove(1,vBox.getChildren().size());
        }
        initAdmin();
        userTaskLabels.clear();
        String json=MyUtil.doJsonPost("findTask", MyUtil.toJSON(MyUtil.getLoginVo().getUser()));
        List<UserTask> userTasks=MyUtil.parseJSONArray(json,UserTask.class);
        for (UserTask userTask:userTasks){
            if (userTask.getCompleteState().equals("-1")){
                userTaskLabel=new UserTaskLabel(claimTasks,userTask);

            }else if (userTask.getCompleteState().equals("0")){
                userTaskLabel=new UserTaskLabel(acceptTasks,userTask);

            }else {
                userTaskLabel=new UserTaskLabel(finishTasks,userTask);
            }
            userTaskLabel.getvBox().getChildren().add(userTaskLabel);
            userTaskLabels.add(userTaskLabel);
        }
    }

    public void initVBox(List<VBox> vBoxes){
        for (int i=0;i<vBoxes.size();i++){
            VBox tempVBox=vBoxes.get(i);
            tempVBox.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            });
            tempVBox.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.ANY);
                    UserTaskLabel tempLabel=null;
                    String userTaskId=event.getDragboard().getString();
                    for (UserTaskLabel userTaskLabel:userTaskLabels){
                        if (userTaskLabel.getUserTask().getId().equals(userTaskId)){
                            tempLabel=userTaskLabel;
                            break;
                        }
                    }
                    if (tempLabel.getvBox().equals(tempVBox)){
                        return;
                    }
                    tempLabel.setvBox(tempVBox);
                    tempVBox.getChildren().add(tempLabel);
                    if (tempVBox.equals(claimTasks)){
                        UserTaskLabel finalTempLabel1 = tempLabel;
                        tempLabel.setStyle("-fx-background-color: RGB(25,122,182)");
                        tempLabel.setOnMouseEntered(event1 -> {
                            finalTempLabel1.setStyle("-fx-background-color: #0090ff");
                        });
                        UserTaskLabel finalTempLabel = tempLabel;
                        tempLabel.setOnMouseExited(event2 -> {
                            finalTempLabel.setStyle("-fx-background-color: RGB(25,122,182)");
                        });
                        tempLabel.getUserTask().setCompleteState("-1");
                        tempLabel.getUserTask().setUser(null);
                    }else if (tempVBox.equals(acceptTasks)){
                        UserTaskLabel finalTempLabel1 = tempLabel;
                        tempLabel.setStyle("-fx-background-color: RGB(239,172,39)");
                        tempLabel.setOnMouseEntered(event1 -> {
                            finalTempLabel1.setStyle("-fx-background-color: #ffca29");
                        });
                        UserTaskLabel finalTempLabel = tempLabel;
                        tempLabel.setOnMouseExited(event2 -> {
                            finalTempLabel.setStyle("-fx-background-color:RGB(239,172,39)");
                        });
                        tempLabel.getUserTask().setCompleteState("0");
                        tempLabel.getUserTask().setUser(MyUtil.getLoginVo().getUser());
                    }else {
                        UserTaskLabel finalTempLabel1 = tempLabel;
                        tempLabel.setStyle("-fx-background-color: RGB(92,198,139)");
                        tempLabel.setOnMouseEntered(event1 -> {
                            finalTempLabel1.setStyle("-fx-background-color: #53e4a8");
                        });
                        UserTaskLabel finalTempLabel = tempLabel;
                        tempLabel.setOnMouseExited(event2 -> {
                            finalTempLabel.setStyle("-fx-background-color:RGB(92,198,139)");
                        });
                        tempLabel.getUserTask().setCompleteState("1");
                        tempLabel.getUserTask().setUser(MyUtil.getLoginVo().getUser());
                    }
                    cn.client.util.MyUtil.doJsonPost("updateTask", cn.client.util.MyUtil.toJSON(tempLabel.getUserTask()));
                    event.consume();
                }
            });
        }
    }

    public void initProjectName(){
        String json=MyUtil.doJsonPost("findCompanyById",MyUtil.getLoginVo().getUser().getCompanyId());
        Company company= MyUtil.parseJSON(json,Company.class);
        if(company.getCompanyTask()!=null){
            projectName.setText(company.getCompanyTask().getName());
            projectName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==2){
                        ShowProjectDetail showProjectDetail=new ShowProjectDetail(company);
                    }
                }
            });
        }

    }

    public void initAdmin(){
        if (MyUtil.getLoginVo().getUser().getRole().equals("admin")){
            Label label=new Label("添加任务");
            claimTasks.getChildren().add(label);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    AddUserTaskDetail addUserTaskDetail=new AddUserTaskDetail(claimTasks);
                    List<VBox> vBoxes=new ArrayList<>();
                    vBoxes.add(claimTasks);vBoxes.add(acceptTasks);vBoxes.add(finishTasks);
                    initLists(vBoxes);
                }
            });
            label.setOnMouseEntered(event -> {
                label.setStyle("-fx-background-color: #0090ff");
            });
            label.setOnMouseExited(event -> {
                label.setStyle("-fx-background-color: RGB(25,122,182)");
            });
        }
    }
}
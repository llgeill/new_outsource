package cn.client.pojo;

import cn.client.ui.user.ShowUserTaskDetail;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author GMUK
 * @date 2018/3/14 0014
 */
public class UserTaskLabel extends Label{
    private VBox vBox;
    private UserTask userTask;

    public UserTaskLabel(VBox vBox,UserTask userTask) {
        this.setText(userTask.getName());
        this.setvBox(vBox);
        this.setUserTask(userTask);
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard=startDragAndDrop(TransferMode.ANY);
                ClipboardContent content=new ClipboardContent();
                content.putString(getUserTask().getId());
                dragboard.setContent(content);
                event.consume();
            }
        });
        this.setOnMouseClicked(event -> {
            if(event.getClickCount()==2){
                ShowUserTaskDetail showUserTaskDetail=new ShowUserTaskDetail(this.getUserTask());
            }
        });
        if (vBox.getId().equals("claimTasks")){
            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-background-color: #0090ff");
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-background-color: RGB(25,122,182)");
            });
        }else if (vBox.getId().equals("acceptTasks")){
            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-background-color: #ffca29");
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-background-color: RGB(239,172,39)");
            });
        }else if (vBox.getId().equals("finishTasks")){
            this.setOnMouseEntered(event -> {
                this.setStyle("-fx-background-color: #53e4a8");
            });
            this.setOnMouseExited(event -> {
                this.setStyle("-fx-background-color: RGB(92,198,139)");
            });
        }
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public UserTask getUserTask() {
        return userTask;
    }

    public void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }
}

package cn.client.ui.user;

import cn.client.pojo.UserTask;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by GMUK on 2018/3/21 0021.
 */
public class AddUserTaskDetailController extends Dialog{
    @FXML
    private TextField name;
    @FXML
    private TextArea content;
    @FXML
    private DialogPane addUserTaskDialog;
    @FXML
    private ImageView closeImage;
    private Dialog dialog;




    public void initliedAll(){
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            dialog.close();
        });
        closeImage.setOnMouseEntered(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png"));
        });
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });
    }


    public void addUserTask(VBox claimTasks){
        ButtonType buttonType1=addUserTaskDialog.getButtonTypes().get(0);
        Button butoon1= (Button) addUserTaskDialog.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=addUserTaskDialog.getButtonTypes().get(1);
        Button butoon= (Button) addUserTaskDialog.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("保存");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(name.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写任务名称！");
                event.consume();
            }else if(content.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写任务内容！");
                event.consume();
            }else {
                UserTask userTask=new UserTask();
                userTask.setId(MyUtil.getUUID());
                userTask.setName(name.getText());
                userTask.setContent(content.getText());
                userTask.setCompleteState("-1");
                MyUtil.doJsonPost("saveUserTasks",MyUtil.toJSON(userTask));

            }
        });
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

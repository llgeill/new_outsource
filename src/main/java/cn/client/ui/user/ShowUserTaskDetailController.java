package cn.client.ui.user;


import cn.client.pojo.UserTask;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Created by GMUK on 2018/3/19 0019.
 */
public class ShowUserTaskDetailController extends Dialog {
    @FXML
    private TextField name;
    @FXML
    private TextArea content;
    @FXML
    private ImageView closeImage;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initliedAll(){
        closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        closeImage.setOnMouseClicked(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_press.png"));
            stage.close();
        });
        closeImage.setOnMouseEntered(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_hover.png"));
        });
        closeImage.setOnMouseExited(event -> {
            closeImage.setImage(new Image("static/image/uiImage/window/window_close_normal.png"));
        });
    }


    public void showUserTaskDetail(UserTask userTask){
        this.initStyle(StageStyle.TRANSPARENT);
        name.setText(userTask.getName());
        content.setText(userTask.getContent());
    }
}

package cn.client.ui.user;


import cn.client.pojo.Company;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Created by GMUK on 2018/3/19 0019.
 */
public class ShowProjectDetailController extends Dialog {
    @FXML
    private TextField name;
    @FXML
    private TextArea content;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private ImageView closeImage;
    @FXML
    private DialogPane addWorkProjectDialog;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initliedAll(){
        ButtonType buttonType1=addWorkProjectDialog.getButtonTypes().get(0);
        Button butoon1= (Button) addWorkProjectDialog.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
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

    public void showProjectDetail(Company company){
        name.setText(company.getCompanyTask().getName());
        content.setText(company.getCompanyTask().getContent());
//        startTime.setText();
    }
}

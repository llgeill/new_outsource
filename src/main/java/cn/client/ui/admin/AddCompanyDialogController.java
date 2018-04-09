package cn.client.ui.admin;


import cn.client.pojo.Company;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddCompanyDialogController extends Dialog {

    @FXML
    private DialogPane addCompanyDialogPane;
    @FXML
    private TextField companyName;
    @FXML
    private TextArea companyAddress;
    @FXML
    private ImageView closeImage;


    public Company company;

    private Dialog dialog;

    @FXML
    private void initialize(){
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
        submit();
    }

    private void submit(){
        ButtonType buttonType1=addCompanyDialogPane.getButtonTypes().get(0);
        Button butoon1= (Button) addCompanyDialogPane.lookupButton(buttonType1);
        butoon1.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonType=addCompanyDialogPane.getButtonTypes().get(1);
        Button butoon= (Button) addCompanyDialogPane.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("保存");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(companyName.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写公司名称！");
                event.consume();
            }else if(companyAddress.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写公司地址！");
                event.consume();
            }else {
                Company company=new Company();
                company.setId(MyUtil.getUUID());
                company.setName(companyName.getText());
                company.setAddress(companyAddress.getText());
                company.setAdmin(MyUtil.getLoginVo().getAdmin());
                MyUtil.doJsonPost("saveCompany",MyUtil.toJSON(company));
               //刷新公司列表

            }
        });
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

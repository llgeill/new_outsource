package cn.client.ui.admin;


import cn.client.pojo.Company;
import cn.client.util.AlertProxy;
import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ShowCompanyDialogController extends Dialog {

    @FXML
    private DialogPane addCompanyDialogPane;
    @FXML
    private TextField companyId;
    @FXML
    private TextField companyName;
    @FXML
    private TextArea companyAddress;
    @FXML
    private ImageView closeImage;

    private Stage stage;


    public Company company;

    @FXML
    private void initialize(){
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
        submit();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void flush(Company company){
        companyId.setText(company.getId());
        companyName.setText(company.getName());
        companyAddress.setText(company.getAddress());
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

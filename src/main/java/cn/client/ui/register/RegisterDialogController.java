package cn.client.ui.register;

import cn.client.arcsoft.demo.AFRTest;
import cn.client.pojo.User;
import cn.client.util.AlertProxy;
import cn.client.util.MyCode;
import cn.client.util.MyUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class RegisterDialogController {
    @FXML
    private TextField name;
    @FXML
    private TextField onePassword;
    @FXML
    private TextField twoPassword;
    @FXML
    private TextField numberPhone;
    @FXML
    private TextField compandyId;
    @FXML
    private TextField contractImage;
    @FXML
    private TextField confidentialityAgreementImage;
    @FXML
    private TextField faceImage;
    @FXML
    private ImageView closeImage;

    @FXML
    private DialogPane registerDialogPane;

    private Stage stage;
    private Dialog dialog;

    private File contractFile;
    private File confidentialityAgreementFile;
    private File faceFile;
    private String contractFileName;
    private String confidentialityAgreementFileName;
    private String faceFileName;


    @FXML
    private void initialize(){
        Platform.runLater(() -> name.requestFocus());
        submitRegister();
    }

    public void initializeAll(){
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

    @FXML
    private void contractAction(){
        contractFile=getUploadFile();
        if(contractFile!=null){
            contractFileName=contractFile.getName();
            if(contractFileName.endsWith("jpg")){
                contractFileName=MyUtil.getUUID()+".jpg";
            }else if(contractFileName.endsWith("png")) {
                contractFileName=MyUtil.getUUID()+".png";
            }
            contractImage.setText(contractFile.getAbsolutePath());
        }
    }
    @FXML
    private void confidentialityAgreementAction(){
        confidentialityAgreementFile=getUploadFile();
        if(confidentialityAgreementFile!=null){
            confidentialityAgreementFileName=confidentialityAgreementFile.getName();
            if(confidentialityAgreementFileName.endsWith("jpg")){
                confidentialityAgreementFileName=MyUtil.getUUID()+".jpg";
            }else if(confidentialityAgreementFileName.endsWith("png")) {
                confidentialityAgreementFileName=MyUtil.getUUID()+".png";
            }
            confidentialityAgreementImage.setText(confidentialityAgreementFile.getAbsolutePath());
        }
    }
    @FXML
    private void faceAction(){
        faceFile=getUploadFile();
        if(faceFile!=null){
            faceFileName=faceFile.getName();
            if(faceFileName.endsWith("jpg")){
                faceFileName=MyUtil.getUUID()+".jpg";
            }else if(faceFileName.endsWith("png")) {
                faceFileName=MyUtil.getUUID()+".png";
            }
            faceImage.setText(faceFile.getAbsolutePath());
        }
    }
    @FXML
    private void takePhotoAction() {
        MyCode myCode=AFRTest.getFaceImage();
        if(myCode.isSuccess()){
            faceFile=new File(myCode.getMessage());
            faceFileName=faceFile.getName();
            faceImage.setText(faceFile.getAbsolutePath());
            AlertProxy.showMessage("人脸识别图片上传上传成功！");
        }else{
            AlertProxy.showMessage(myCode.getMessage());
        }



    }

    private File getUploadFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("上传图片");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Images", "*.*"),
                //new FileChooser.ExtensionFilter("GIF", "*.gif"),
                //new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
    private void submitRegister(){
        ButtonType buttonType=registerDialogPane.getButtonTypes().get(1);
        Button butoon= (Button) registerDialogPane.lookupButton(buttonType);
        butoon.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        ButtonType buttonTypes=registerDialogPane.getButtonTypes().get(0);
        Button butoons= (Button) registerDialogPane.lookupButton(buttonTypes);
        butoons.setStyle("-fx-background-color:#3BBA7D;-fx-text-fill:#f9fffc");
        butoon.setText("完成");
        butoon.addEventFilter(ActionEvent.ACTION, event-> {
            if(name.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写真实姓名！");
                event.consume();
            }else if(onePassword.getText().equals("")||twoPassword.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写密码！");
                event.consume();
            }else if(!onePassword.getText().equals(twoPassword.getText())){
                AlertProxy.showErrorAlert("两次密码错误！");
                event.consume();
            }else if(numberPhone.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写手机号码！");
                event.consume();
            }else if(compandyId.getText().equals("")){
                AlertProxy.showErrorAlert("没有填写公司Id！");
                event.consume();
            }else if(contractImage.getText().equals("")){
                AlertProxy.showErrorAlert("没有上传合同协议图片！");
                event.consume();
            }else if(confidentialityAgreementImage.getText().equals("")){
                AlertProxy.showErrorAlert("没有上传保密协议图片！");
                event.consume();
            }else if(faceImage.getText().equals("")){
                AlertProxy.showErrorAlert("没有上传人脸图片！");
                event.consume();
            }else {
                User user=new User();
                user.setId(MyUtil.getUUID());
                user.setRole("user");
                user.setName(name.getText());
                user.setPassword(onePassword.getText());
                user.setAudit(0);
                user.setCompanyId(compandyId.getText());
                user.setNumberPhone(numberPhone.getText());
                user.setConfidentialityAgreementImage(confidentialityAgreementFileName);
                user.setContractImage(contractFileName);
                user.setFaceImage(faceFileName);
                MyUtil.uploadFiles("upLoadImage/image/contractImage",contractFileName,contractFile);
                MyUtil.uploadFiles("upLoadImage/image/CAImage",confidentialityAgreementFileName,confidentialityAgreementFile);
                MyUtil.uploadFiles("upLoadImage/image/faceImage",faceFileName,faceFile);
                MyUtil.doJsonPost("registerUser",MyUtil.toJSON(user));
                MyUtil.getLoginVo().setUser(user);
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}

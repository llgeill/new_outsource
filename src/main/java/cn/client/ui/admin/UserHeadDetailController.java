package cn.client.ui.admin;

import cn.client.pojo.Company;
import cn.client.pojo.User;
import cn.client.util.MyUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class UserHeadDetailController {
    @FXML
    private TextField name;
    @FXML
    private TextField numberPhone;
    @FXML
    private TextField company;

    @FXML
    private ComboBox role;

    @FXML
    private ComboBox audit;

    private User user;
    private BorderPane borderPane;
    private AnchorPane firstAnchorPane;
    private CompanyUI companyUI;

    public void initializeAll(){
        this.firstAnchorPane= (AnchorPane) borderPane.getCenter();
        name.setText(user.getName());
        numberPhone.setText(user.getNumberPhone());
        String json=MyUtil.doJsonPost("/findCompanyById",user.getCompanyId());
        Company companys=MyUtil.parseJSON(json,Company.class);
        company.setText(companys.getName());
        if(user.getRole().trim().equals("admin")) {
            role.setPromptText("高级");
            role.setValue("高级");
        }
        else {
            role.setPromptText("普通");
            role.setValue("普通");
        }
        role.getItems().addAll("普通","高级");
        if(user.getAudit()==1){
            audit.setPromptText("审核通过");
            audit.setValue("审核通过");
        }
        else{
            audit.setPromptText("未审核");
            audit.setValue("未审核");
        }
        audit.getItems().addAll("未审核","审核通过");
    }

    @FXML
    private void faceImage(){
        DialogPane dialogPane=new DialogPane();
        ImageView imageView=new ImageView(MyUtil.getProperties().getProperty("server.url")+"image/faceImage/"+user.getFaceImage());
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        dialogPane.getChildren().add(imageView);
        dialogPane.setPrefSize(800,800);
        dialogPane.getButtonTypes().add(ButtonType.FINISH);
        Dialog dialog=new Dialog();
        dialog.setDialogPane(dialogPane);
        dialog.setResizable(true);
        dialog.showAndWait();

    }

    @FXML
    private void contractImage() {
        DialogPane dialogPane=new DialogPane();
        ImageView imageView=new ImageView(MyUtil.getProperties().getProperty("server.url")+"image/contractImage/"+user.getContractImage());
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        dialogPane.getChildren().add(imageView);
        dialogPane.setPrefSize(800,800);
        dialogPane.getButtonTypes().add(ButtonType.FINISH);
        Dialog dialog=new Dialog();
        dialog.setDialogPane(dialogPane);
        dialog.setResizable(true);
        dialog.showAndWait();
    }

    @FXML
    private void secretImage(){
        DialogPane dialogPane=new DialogPane();
        ImageView imageView=new ImageView(MyUtil.getProperties().getProperty("server.url")+"image/CAImage/"+user.getConfidentialityAgreementImage());
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        dialogPane.getChildren().add(imageView);
        dialogPane.setPrefSize(800,800);
        dialogPane.getButtonTypes().add(ButtonType.FINISH);
        Dialog dialog=new Dialog();
        dialog.setDialogPane(dialogPane);
        dialog.setResizable(true);
        dialog.showAndWait();
    }

    @FXML
    private void comeback(){
        borderPane.setCenter(firstAnchorPane);
    }

    @FXML
    private void comebackButton(){
        borderPane.setCenter(firstAnchorPane);
    }

    @FXML
    private void save(){
        user.setAudit(audit.getValue().equals("未审核")?0:1);
        user.setRole(role.getValue().equals("普通")?"user":"admin");
        String json=MyUtil.doJsonPost("/saveUser",MyUtil.toJSON(user));
        if(json!=null||!json.equals("")){
           companyUI.flush();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public CompanyUI getCompanyUI() {
        return companyUI;
    }

    public void setCompanyUI(CompanyUI companyUI) {
        this.companyUI = companyUI;
    }
}

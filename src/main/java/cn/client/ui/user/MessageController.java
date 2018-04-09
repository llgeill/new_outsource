package cn.client.ui.user;

import cn.client.pojo.User;
import cn.client.util.MyUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MessageController {
        @FXML
        private TextField password;
        @FXML
        private TextField number;
        @FXML
        private TextField name;
        @FXML
        private TextField confidentiality;
        @FXML
        private TextField contract;
        @FXML
        private TextField role;
        @FXML
        private ImageView image;
        @FXML
        private Button change;


        public  void getImage(){
            User user = MyUtil.getLoginVo().getUser();
            image.setImage(new Image(MyUtil.getProperties().getProperty("server.url")+"image/faceImage/"+user.getFaceImage()));
        }
        @FXML
        public void initialize() {
            User user = MyUtil.getLoginVo().getUser();
            password.setText(user.getPassword());
            password.setEditable(false);
            name.setText(user.getName());
            name.setEditable(false);
            number.setText(user.getNumberPhone());
            number.setEditable(false);
            contract.setEditable(false);
            confidentiality.setEditable(false);
            role.setEditable(false);


            if (user.getConfidentialityAgreementImage()!=null)
                confidentiality.setText("已签订");
            else
                confidentiality.setText("未签订");

            if(user.getContractImage()!=null)
                contract.setText("已签订");
            else
                contract.setText("未签订");

            if(user.getRole().equals("user"))
                role.setText("user");
            else if(user.getRole().equals("admin"))
                role.setText("admin");
        }
        @FXML
    public void change(javafx.event.ActionEvent actionEvent) {
           String text = "修改信息";
            if(change.getText().equals(text)){
                change.setText("保存信息");
                password.setEditable(true);
                number.setEditable(true);
                password.setText(password.getText());
                number.setText(number.getText());
            }
            else
            {
                password.setEditable(false);
                number.setEditable(false);
                User user = MyUtil.getLoginVo().getUser();
                user.setPassword(password.getText());
                user.setNumberPhone(number.getText());
                MyUtil.doJsonPost("saveAdmin",MyUtil.toJSON(user));
                change.setText("修改信息" );
            }



    }
}

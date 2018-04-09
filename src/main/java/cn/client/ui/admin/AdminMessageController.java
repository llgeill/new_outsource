package cn.client.ui.admin;

import cn.client.util.MyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by GMUK on 2018/3/21.
 */
public class AdminMessageController {
    @FXML
    private Button change;
    @FXML
    private TextField password;
    @FXML
    private TextField number;
    @FXML
    private  TextField role;
    @FXML
    private  TextField name;
    @FXML
    public void change(ActionEvent actionEvent) {
        String text = "修改信息";
        if (change.getText().equals(text)) {
            change.setText("保存信息");
            password.setEditable(true);
            number.setEditable(true);
            password.setText(password.getText());
            number.setText(number.getText());
        } else {
            password.setEditable(false);
            number.setEditable(false);
            cn.client.pojo.Admin admin = MyUtil.getLoginVo().getAdmin();
            admin.setPassword(password.getText());
            admin.setPhoneNumber(number.getText());
            MyUtil.doJsonPost("saveAdmin", MyUtil.toJSON(admin));
            change.setText("修改信息");
        }
    }
    public void initialize() {
        change.setText("修改信息");
        cn.client.pojo.Admin admin = MyUtil.getLoginVo().getAdmin();
        password.setText(admin.getPassword());
        password.setEditable(false);
        name.setText(admin.getName());
        name.setEditable(false);
        number.setText(admin.getPhoneNumber());
        number.setEditable(false);
        role.setEditable(false);

        if(admin.getRole().equals("user")) {
            role.setText("user");
        }else if(admin.getRole().equals("admin")){
            role.setText("admin");}
    }
}

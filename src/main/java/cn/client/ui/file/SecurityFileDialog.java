package cn.client.ui.file;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SecurityFileDialog extends Dialog<Integer> {
    private SecurityFileDialogController controller;

    public SecurityFileDialog() {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/static/fxml/admin/SecurityFileDialog.fxml"));
            DialogPane dialogPane=loader.load();
            controller=loader.getController();
            this.initStyle(StageStyle.TRANSPARENT);
            controller.initializeAll();
            this.setDialogPane(dialogPane);
            this.setResultConverter(dialogButton -> {
                if (dialogButton == dialogPane.getButtonTypes().get(1)) {
                    return controller.getSecurity();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SecurityFileDialogController getController() {
        return controller;
    }

    public void setController(SecurityFileDialogController controller) {
        this.controller = controller;
    }
}

package cn.client.ui.file;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class FileProgressController {
    @FXML
    private ProgressBar fileProgress;
    @FXML
    private AnchorPane tempPane;
    public ProgressBar getFileProgress() {
        return fileProgress;
    }

    public void setFileProgress(ProgressBar fileProgress) {
        this.fileProgress = fileProgress;
    }

    public AnchorPane getTempPane() {
        return tempPane;
    }

    public void setTempPane(AnchorPane tempPane) {
        this.tempPane = tempPane;
    }
}

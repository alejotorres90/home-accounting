package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class About {

    @FXML
    private Button closeBtn;

    @FXML
    void closeClicked(MouseEvent event) {
        Stage stage = (Stage)closeBtn.getScene().getWindow();
        stage.close();
    }

}
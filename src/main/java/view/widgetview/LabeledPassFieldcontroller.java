package main.java.view.widgetview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LabeledPassFieldcontroller {

    @FXML
    Label LabeledPassField_Label;
    @FXML
    PasswordField LabeledPassField_Password;

    public void setLabel(String text) {
        LabeledPassField_Label.setText(text);
    }

    public void setPromptText(String text){
        LabeledPassField_Password.setPromptText(text);
    }

    public String getText() {
        return LabeledPassField_Password.getText();
    }
}

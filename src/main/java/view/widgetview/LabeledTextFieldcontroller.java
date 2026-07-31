package main.java.view.widgetview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LabeledTextFieldcontroller {
    @FXML
    Label LabeledTextField_Label;

    @FXML
    TextField LabeledTextField_text;

    public void setLabel(String text) {
        LabeledTextField_Label.setText(text);
    }

    public String getText() {
        return LabeledTextField_text.getText();
    }

}

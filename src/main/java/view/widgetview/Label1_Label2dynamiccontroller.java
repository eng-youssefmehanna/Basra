package main.java.view.widgetview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Label1_Label2dynamiccontroller {
    @FXML
    Label Label1_static;

    @FXML
    Label Label2_dynamic;

    public void setLabel(String text) {
        Label1_static.setText(text);
    }

    // will be replaced by binding
    public void setLabelDynamic(String text) {
        Label2_dynamic.setText(text);
    }
}

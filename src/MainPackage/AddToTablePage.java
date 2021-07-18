package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddToTablePage {
    @FXML
    private TextField passwordCheckFieldShow;
    @FXML
    private TextField userCheckFieldShow;
    @FXML
    private PasswordField passwordCheckFieldHide;
    @FXML
    private RadioButton showPassRadio;

    private Stage stage;
    private Scene scene;
    private Parent root;



    public void onPressRadio() {
        if (showPassRadio.isSelected()) {
            passwordCheckFieldShow.toFront();
            passwordCheckFieldShow.setText(passwordCheckFieldHide.getText());
        } else {
            passwordCheckFieldHide.toFront();
            passwordCheckFieldHide.setText(passwordCheckFieldShow.getText());
        }
    }

    public void addToTable(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afterLoginPage.fxml"));
            root = loader.load();
            AfterLoginPage controller = loader.getController();
            String pass;
            if (showPassRadio.isSelected()) {
                pass = passwordCheckFieldShow.getText();
            } else {
                pass = passwordCheckFieldHide.getText();
            }
            controller.updateTable(userCheckFieldShow.getText(), pass);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

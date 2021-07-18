package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.*;

public class LoginPage implements Initializable {

    /*
    todo: add validation, loading data, saving data, load to the after login page only userdata, delete older data.
     */

    private Stage stage;
    private Scene scene;
    private HashMap<String ,PairOfUserAndPass> passwords = new HashMap<>();


    @FXML
    TextField userNameTextField;

    @FXML
    PasswordField passwordTextField;

    public void goToCreateAccountPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("createAccountPage.fxml")));
            Parent root = loader.load();
            CreateAccountPage controller = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToAfterLoginPage(ActionEvent event) throws IOException {
        if (validateUser()) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("afterLoginPage.fxml")));
                Parent root = loader.load();
                AfterLoginPage controller = loader.getController();
                PairOfUserAndPass user = new PairOfUserAndPass(passwordTextField.getText(),userNameTextField.getText());
                controller.loadData(user);
                controller.updateTable("","");
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // todo, this.
    private boolean validateUser() {
        PairOfUserAndPass user = new PairOfUserAndPass(passwordTextField.getText(),userNameTextField.getText());
        return passwords.containsValue(user);
    }

    // todo, load users data to the hash map
    private void loadUsersData() {
        File f = new File("users.txt");
        if (f.exists()) {
            String file = "users.txt";
            try {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream obj = new ObjectInputStream(fin);
                passwords = (HashMap<String, PairOfUserAndPass>) obj.readObject();
                obj.close();
                fin.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUsersData();
    }
}

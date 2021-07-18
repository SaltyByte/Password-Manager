package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class LoginPage implements Initializable {


    private Stage stage;
    private Scene scene;
    private HashMap<String ,PairOfUserAndPass> passwords = new HashMap<>();


    @FXML
    TextField userNameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    private Label notMatchPasswords;

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
        notMatchPasswords.setVisible(true);
        return passwords.containsValue(user);
    }

    // todo, load users data to the hash map
    private void loadUsersData() {
        File f = new File("users");
        if (f.exists()) {

            try {
                SecretKey key64 = new SecretKeySpec(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07}, "Blowfish");
                Cipher cipher = Cipher.getInstance("Blowfish");

                cipher.init(Cipher.DECRYPT_MODE, key64);
                CipherInputStream cipherInputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(f)), cipher);
                ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
                SealedObject sealedObject = (SealedObject) inputStream.readObject();
                passwords = (HashMap<String, PairOfUserAndPass>) sealedObject.getObject(cipher);
            }
            catch (Exception e){
                e.printStackTrace();
            }






//            String file = "users.txt";
//            try {
//                FileInputStream fin = new FileInputStream(file);
//                ObjectInputStream obj = new ObjectInputStream(fin);
//                passwords = (HashMap<String, PairOfUserAndPass>) obj.readObject();
//                obj.close();
//                fin.close();
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUsersData();
    }
}

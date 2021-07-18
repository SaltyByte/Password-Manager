package MainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

import java.net.URL;
import java.util.*;

public class CreateAccountPage implements Serializable, Initializable {
    private HashMap<String, PairOfUserAndPass> passwords = new HashMap<>();


    @FXML
    TextField createUserNameTextField;

    @FXML
    PasswordField createPasswordTextField1;

    @FXML
    PasswordField createPasswordTextField2;

    @FXML
    Label passwordsMatchLabel;

    public void createNewUser() {
        String userName = createUserNameTextField.getText();
        String password1 = createPasswordTextField1.getText();
        String password2 = createPasswordTextField2.getText();
        PairOfUserAndPass user = new PairOfUserAndPass(password1, userName);
        if (!password1.equals(password2)) {
            passwordsMatchLabel.setStyle("-fx-text-fill: red;");
            passwordsMatchLabel.setText("Passwords do not Match");
            passwordsMatchLabel.setVisible(true);
        } else if (passwords.containsKey(userName)) {
            passwordsMatchLabel.setStyle("-fx-text-fill: red;");
            passwordsMatchLabel.setText("User Already Exists");
            passwordsMatchLabel.setVisible(true);
        } else if (!userName.isEmpty() && !password1.isEmpty()) {
            passwords.put(userName, user);
            passwordsMatchLabel.setStyle("-fx-text-fill: green;");
            passwordsMatchLabel.setText("Successes! Made New User");
            passwordsMatchLabel.setVisible(true);
            save();
        }
    }


    public void goBackToLoginPage(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        String file = "users.txt"; // fix this, not working properly
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(fout);
            obj.writeObject(passwords);
            obj.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
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
        load();
    }
}

package MainPackage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;


// todo when going toi after login page, read data from encrypted file and not list, add load data

public class AfterLoginPage implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static ArrayList<PairOfUserAndPass> listOfPasses = new ArrayList<>();
    private static PairOfUserAndPass user;


    @FXML
    private TextField passwordCheckFieldShow;
    @FXML
    private Label passwordCheckLabel;
    @FXML
    private PasswordField passwordCheckFieldHide;
    @FXML
    private RadioButton showPassRadio;
    @FXML
    private TextField passwordGenerateField;
    @FXML
    private  TableView<PairOfUserAndPass> passwordsTable;
    @FXML
    private TableColumn<PairOfUserAndPass, String> userNameColumn;
    @FXML
    private TableColumn<PairOfUserAndPass, String> passwordColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // this is a grid drawing
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//
//        // vertical lines
//        for(int i = 0 ; i < canvas.getWidth() ; i+=20){
//            gc.strokeLine(i, 0, i, canvas.getHeight() - (canvas.getHeight()%30));
//        }
//
//        // horizontal lines
//        for(int i = 0 ; i < canvas.getHeight() ; i+=20){
//            gc.strokeLine(0, i, canvas.getWidth(), i);
//        }
    }


    private void saveData(){
        String file = "user" + user.getUserName() + ".txt";
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(fout);
            obj.writeObject(listOfPasses);
            obj.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadData(PairOfUserAndPass user){
        AfterLoginPage.user = user;
        File f = new File("user" + user.getUserName() + ".txt");
        if (f.exists()) {
            String file = "user" + user.getUserName() + ".txt";
            try {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream obj = new ObjectInputStream(fin);
                listOfPasses = (ArrayList<PairOfUserAndPass>) obj.readObject();
                obj.close();
                fin.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void goBackToLoginPage(ActionEvent event) throws IOException {
        listOfPasses.clear();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
            root = loader.load();
            LoginPage controller = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks the strength of the password and changes the label under to weak/strong according to strength
     */
    public void checkPassword() {
        if (passwordStrength()) {
            passwordCheckLabel.setStyle("-fx-text-fill: green;");
            passwordCheckLabel.setText("Stronk!");
        } else {
            passwordCheckLabel.setStyle("-fx-text-fill: red;");
            passwordCheckLabel.setText("Weak");
        }
    }

    /**
     * checks the strength of the password
     * @return boolean - if password is strong enough
     */
    private boolean passwordStrength() {
        int size = 10;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        String pass;
        if (showPassRadio.isSelected()) {
            pass = passwordCheckFieldShow.getText();
        } else {
            pass = passwordCheckFieldHide.getText();
        }
        for (int i = 0; i < pass.length(); i++) {
            if (pass.charAt(i) <= 'Z' && pass.charAt(i) >= 'A') {
                hasUpperCase = true;
            } else if (pass.charAt(i) <= 'z' && pass.charAt(i) >= 'a') {
                hasLowerCase = true;
            } else if (pass.charAt(i) <= '9' && pass.charAt(i) >= '0') {
                hasDigit = true;
            } else if (pass.charAt(i) <= '/' && pass.charAt(i) >= '!' || pass.charAt(i) <= '@' && pass.charAt(i) >= ':' || pass.charAt(i) <= '`' && pass.charAt(i) >= '[') {
                hasSymbol = true;
            }
        }
        return hasDigit && hasSymbol && hasUpperCase && hasLowerCase && pass.length() >= size;

    }

    /**
     * shows the password text field on press
     */
    public void onPressRadio() {
        if (showPassRadio.isSelected()) {
            passwordCheckFieldShow.toFront();
            passwordCheckFieldShow.setText(passwordCheckFieldHide.getText());
        } else {
            passwordCheckFieldHide.toFront();
            passwordCheckFieldHide.setText(passwordCheckFieldShow.getText());
        }
    }

    /**
     * generating new random strong password using naive and stupid algorithm
     */
    public void generatePassword() {
        StringBuilder sb = new StringBuilder();
        int size = (int) (Math.random() * 4) + 14;
        for (int i = 0; i < size / 4; i++) {
            int randomNum = (int) (Math.random() * 9);
            int randomLower = (int) (Math.random() * (122 - 97)) + 97;
            int randomUpper = (int) (Math.random() * (90 - 65)) + 65;
            int randomSymbol = (int) (Math.random() * (47 - 33)) + 33;
            sb.append((char) (randomNum + '0'));
            sb.append((char) (randomLower));
            sb.append((char) (randomUpper));
            sb.append((char) (randomSymbol));
        }
        List<Character> list = new ArrayList<>();
        for (char c : sb.toString().toCharArray()) {
            list.add(c);
        }
        Collections.shuffle(list);
        StringBuilder newSb = new StringBuilder();
        for (char c : list) {
            newSb.append(c);
        }
        passwordGenerateField.setText(newSb.toString());
    }

    /**
     * this method opens new window for the adding table page
     * @param event
     * @throws IOException
     */
    public void addToTable(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("addToTablePage.fxml")));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * this method getting new user name and password from the addtoTablePage and adds it to the table
     * @param userName - String, user name
     * @param password - String, password
     */
    public void updateTable(String userName, String password) {
        if (!userName.isEmpty() && !password.isEmpty()) {
            PairOfUserAndPass pair = new PairOfUserAndPass(password, userName);
            listOfPasses.add(pair);
        }
        passwordsTable.getItems().addAll(listOfPasses);
        saveData();
    }

    /**
     * shhh, a secret
     */
    public void clickingShrek() {
        try {
            String url_open = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deleting row with button
     */
    public void deleteRow(){
        PairOfUserAndPass selectedItem = passwordsTable.getSelectionModel().getSelectedItem();
        passwordsTable.getItems().remove(selectedItem);
        listOfPasses.remove(selectedItem);
    }
}

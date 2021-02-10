import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class of the login/registration screen.
 */
public class LoginController {


    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button connectButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField registerNameTextField;

    @FXML
    private TextField registerSurnameTextField;

    @FXML
    private TextField registerEmailTextField;

    @FXML
    private TextField registerPhoneNumberTextField;

    @FXML
    private PasswordField registerPasswordTextField;

    @FXML
    private Button confirmRegistrationButton;

    @FXML
    private Button returnFromRegistrationButton;

    @FXML
    private ImageView loginImageView;


    public static DBUtil dbUtil;
    public static StringBuilder consoleText = new StringBuilder();


    /**
     * Processes return from registration button click.
     * @param event Click event.
     */
    @FXML
    void onRetFromRegClick(ActionEvent event) {
        registerButton.setDisable(false);
        connectButton.setDisable(false);
        loginImageView.setVisible(true);
    }

    /**
     * Processes confirm registration button click. Creates a basic user with basic privileges and inserts his data into
     * the appropriate "clients" table in the database.
     * @param event Click event.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     * @throws NoSuchAlgorithmException When an error with hashing occurred.
     */
    @FXML
    void confirmRegistrationPressed(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        if (inputsAreValid()) {

            try {
                String selectStmt = "insert into klienci (imie, nazwisko, email, id_klienta) VALUES\n" +
                        " ('" + registerNameTextField.getText() + "','" + registerSurnameTextField.getText() + "','" + registerEmailTextField.getText() + "','" + registerPhoneNumberTextField.getText() + "');";
                dbUtil.dbExecuteUpdate(selectStmt);

                selectStmt = "CREATE USER '" + registerPhoneNumberTextField.getText() + "'@'localhost' IDENTIFIED BY '" + Hasher.hashMD5(registerPasswordTextField.getText()) + "';";
                dbUtil.dbExecuteUpdate(selectStmt);

                selectStmt = "GRANT select on paczkex.* TO '" + registerPhoneNumberTextField.getText() + "'@'localhost';";
                dbUtil.dbExecuteUpdate(selectStmt);

                selectStmt = "GRANT EXECUTE ON PROCEDURE paczkex.nadanie TO '" + registerPhoneNumberTextField.getText() + "'@'localhost';";
                dbUtil.dbExecuteUpdate(selectStmt);

                selectStmt = "GRANT EXECUTE ON PROCEDURE paczkex.odbior TO '" + registerPhoneNumberTextField.getText() + "'@'localhost';";
                dbUtil.dbExecuteUpdate(selectStmt);

                consoleTextArea.appendText("Z powodzeniem stworzono użytkownika!" + "\n");


            } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
                consoleTextArea.appendText("While creating user an error occured. \n");
                throw e;
            }


            registerButton.setDisable(false);
            connectButton.setDisable(false);
            loginImageView.setVisible(true);

        }else{
            consoleTextArea.appendText("Invalid credentials! \n");
        }

    }

    /**
     * Checks whether all the inputs are valid (are of appropriate length and do not contain illegal symbols).
     * @return True/false (depending on whether the inputs were valid or not).
     */
    private boolean inputsAreValid() {

        //check name and surname for illegal symbols and length
        String temp=registerNameTextField.getText().toLowerCase();
        char[] charArray=temp.toCharArray();
        if (!checkForLettersOnly(charArray)) return false;

        temp=registerSurnameTextField.getText().toLowerCase();
        charArray=temp.toCharArray();
        if (!checkForLettersOnly(charArray)) return false;

        //check email for '@' and '.' and length
        temp=registerEmailTextField.getText();
        if (!(temp.contains("@") && temp.contains(".")) || temp.length()>30 || temp.length()<5) return false;

        //check phone number for numbers only and length
        temp=registerPhoneNumberTextField.getText();
        if (temp.length()==9){
            try{
                int test=Integer.parseInt(temp);
            }catch(NumberFormatException e){
                return false;
            }
        }else return false;

        //check password for length
        temp=registerPasswordTextField.getText();
        if (temp.length()<8 || temp.length()>30)
            return false;

        return true;
    }

    private boolean checkForLettersOnly(char[] charArray) {
        if (charArray.length>30 || charArray.length<3)
            return false;
        for (char ch : charArray) {
            if (!(ch >= 'a' && ch <= 'z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes begin registration button click. Logs in as a temporary "create new basic users only" - type user and sets
     * up the appropriate view.
     * @param event Click event.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     * @throws NoSuchAlgorithmException When an error with hashing occurred.
     */
    @FXML
    void registerButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        dbUtil = new DBUtil("default_login_user", "1234", consoleTextArea);
        dbUtil.dbConnect();

        consoleText.append("Możesz się zarejestrować!").append("\n");
        consoleTextArea.setText(consoleText.toString());

        connectButton.setDisable(true);
        registerButton.setDisable(true);
        loginImageView.setVisible(false);

    }

    /**
     *
     * @param event
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     * @throws IOException When there was a problem with .fxml file.
     * @throws NoSuchAlgorithmException When an error with hashing occurred.
     */
    @FXML
    void connectButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException {
        dbUtil = new DBUtil(userTextField.getText(), passwordTextField.getText(), consoleTextArea);
        dbUtil.dbConnect();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(RacketApp.loadFXML("user"), 800, 600);
        stage.setScene(scene);


    }

    /**
     * Initializes the login/registration screen.
     */
    @FXML
    void initialize() {
        assert userTextField != null : "fx:id=\"userTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'login.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'login.fxml'.";
        assert consoleTextArea != null : "fx:id=\"consoleTextArea\" was not injected: check your FXML file 'login.fxml'.";
        assert registerPane != null : "fx:id=\"registerPane\" was not injected: check your FXML file 'login.fxml'.";
        assert registerNameTextField != null : "fx:id=\"registerNameTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerSurnameTextField != null : "fx:id=\"registerSurnameTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerEmailTextField != null : "fx:id=\"registerEmailTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerPhoneNumberTextField != null : "fx:id=\"registerPhoneNumberTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert registerPasswordTextField != null : "fx:id=\"registerPasswordTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert confirmRegistrationButton != null : "fx:id=\"confirmRegistrationButton\" was not injected: check your FXML file 'login.fxml'.";
        assert returnFromRegistrationButton != null : "fx:id=\"returnFromRegistrationButton\" was not injected: check your FXML file 'login.fxml'.";
        assert loginImageView != null : "fx:id=\"loginImageView\" was not injected: check your FXML file 'login.fxml'.";

    }
}


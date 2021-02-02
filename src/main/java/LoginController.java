import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {



    @FXML
    private PasswordField userTextField;

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
    private TextField registerPasswordTextField;

    @FXML
    private Button confirmRegistrationButton;

    public static DBUtil dbUtil;
    public static StringBuilder consoleText=new StringBuilder();
    private PackageDAO racketDAO;

    @FXML
    void confirmRegistrationPressed(ActionEvent event) throws SQLException, ClassNotFoundException {


        try {
            String selectStmt = "insert into klienci (imie, nazwisko, email, telefon) VALUES\n" +
                    " ('"+registerNameTextField.getText()+"','"+registerSurnameTextField.getText()+"','"+registerEmailTextField.getText()+"','"+registerPhoneNumberTextField.getText()+"');";
            dbUtil.dbExecuteUpdate(selectStmt);

            selectStmt="CREATE USER '"+registerPhoneNumberTextField.getText()+"'@'localhost' IDENTIFIED BY '"+registerPasswordTextField.getText()+"';";
            dbUtil.dbExecuteUpdate(selectStmt);

            selectStmt="GRANT select on paczkex.zlecenia TO '"+registerPhoneNumberTextField.getText()+"'@'localhost';";
            dbUtil.dbExecuteUpdate(selectStmt);

            selectStmt="GRANT EXECUTE ON PROCEDURE paczkex.nadanie TO '"+registerPhoneNumberTextField.getText()+"'@'localhost';";
            dbUtil.dbExecuteUpdate(selectStmt);

            selectStmt="GRANT EXECUTE ON PROCEDURE paczkex.odbior TO '"+registerPhoneNumberTextField.getText()+"'@'localhost';";
            dbUtil.dbExecuteUpdate(selectStmt);

            consoleTextArea.appendText("User created successfully!");

            consoleTextArea.appendText("Connection closed. Bye!" + "\n");

        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("While creating user an error occured. \n");
            throw e;
        }


        registerButton.setDisable(false);
        registerPane.setVisible(false);
        connectButton.setDisable(false);

    }

    @FXML
    void registerButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        dbUtil = new DBUtil("default_login_user", "1234", consoleTextArea);
        dbUtil.dbConnect();

        consoleText.append("You can register!").append("\n");
        consoleTextArea.setText(consoleText.toString());

        connectButton.setDisable(true);
        registerButton.setDisable(true);
        registerPane.setVisible(true);

    }

    @FXML
    void connectButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
        dbUtil = new DBUtil(userTextField.getText(), passwordTextField.getText(), consoleTextArea);
        racketDAO = new PackageDAO(dbUtil, consoleTextArea);

        dbUtil.dbConnect();

        consoleText.append("Access granted for user \"").append(userTextField.getText()).append("\".").append("\n");
        consoleTextArea.setText(consoleText.toString());

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(RacketApp.loadFXML("user"), 800, 600);
        stage.setScene(scene);
    }


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

    }
}


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField leftTextField;

    @FXML
    private TextField rightTextField;

    @FXML
    private Button processButton;

    @FXML
    private ComboBox<?> comboBox1;

    @FXML
    private ComboBox<?> comboBox2;

    @FXML
    private TableView<Package> ordersTable;

    @FXML
    private TableColumn<Package, Integer> idCol;

    @FXML
    private TableColumn<Package, String> stanCol;

    @FXML
    private TableColumn<Package, String> dataNadaniaCol;

    @FXML
    private TableColumn<Package, String> dataOdbioruCol;

    @FXML
    private TableColumn<Package, String> ulicaCol;

    @FXML
    private TableColumn<Package, Integer> nrCol;

    @FXML
    private TableColumn<Package, String> miastoCol;

    @FXML
    private TableColumn<Package, String> krajCol;

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button showAllButton;

    private DBUtil dbUtil;
    private PackageDAO racketDAO;

    @FXML
    void disconnectButtonPressed(ActionEvent event) throws IOException, SQLException {
        dbUtil.dbDisconnect();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(RacketApp.loadFXML("login"), 800, 600);
        stage.setScene(scene);
    }

    @FXML
    void onProcessClick(ActionEvent event) {

    }

    @FXML
    void showAllButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {

            ordersTable.getItems().clear();
            ObservableList<Package> racketData = racketDAO.showAllOrders();
            populateRackets(racketData);

        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("Error occurred while getting packages from DB.\n");
            throw e;
        }
    }

    private void populateRackets(ObservableList<Package> racketData) {
        ordersTable.setItems(racketData);
    }

    @FXML
    void initialize() {
        assert leftTextField != null : "fx:id=\"leftTextField\" was not injected: check your FXML file 'user.fxml'.";
        assert rightTextField != null : "fx:id=\"rightTextField\" was not injected: check your FXML file 'user.fxml'.";
        assert processButton != null : "fx:id=\"processButton\" was not injected: check your FXML file 'user.fxml'.";
        assert comboBox1 != null : "fx:id=\"comboBox1\" was not injected: check your FXML file 'user.fxml'.";
        assert comboBox2 != null : "fx:id=\"comboBox2\" was not injected: check your FXML file 'user.fxml'.";
        assert ordersTable != null : "fx:id=\"ordersTable\" was not injected: check your FXML file 'user.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'user.fxml'.";
        assert stanCol != null : "fx:id=\"stanCol\" was not injected: check your FXML file 'user.fxml'.";
        assert dataNadaniaCol != null : "fx:id=\"dataNadaniaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert dataOdbioruCol != null : "fx:id=\"dataOdbioruCol\" was not injected: check your FXML file 'user.fxml'.";
        assert ulicaCol != null : "fx:id=\"ulicaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert nrCol != null : "fx:id=\"nrCol\" was not injected: check your FXML file 'user.fxml'.";
        assert miastoCol != null : "fx:id=\"miastoCol\" was not injected: check your FXML file 'user.fxml'.";
        assert krajCol != null : "fx:id=\"krajCol\" was not injected: check your FXML file 'user.fxml'.";
        assert consoleTextArea != null : "fx:id=\"consoleTextArea\" was not injected: check your FXML file 'user.fxml'.";
        assert disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'user.fxml'.";
        assert showAllButton != null : "fx:id=\"showAllButton\" was not injected: check your FXML file 'user.fxml'.";

        dbUtil=LoginController.dbUtil;
        racketDAO=new PackageDAO(dbUtil, consoleTextArea);
    }

}



//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//public class UserController {
//
//    @FXML // ResourceBundle that was given to the FXMLLoader
//    private ResourceBundle resources;
//
//    @FXML // URL location of the FXML file that was given to the FXMLLoader
//    private URL location;
//
//    @FXML // fx:id="racketNameToAddTextField"
//    private TextField racketNameToAddTextField; // Value injected by FXMLLoader
//
//    @FXML // fx:id="addRacketButton"
//    private Button addRacketButton; // Value injected by FXMLLoader
//
//    @FXML // fx:id="selectRacketNameTextField"
//    private TextField selectRacketNameTextField; // Value injected by FXMLLoader
//
//    @FXML // fx:id="selectRacketButton"
//    private Button selectRacketButton; // Value injected by FXMLLoader
//
//    @FXML // fx:id="showRacketsButton"
//    private Button showRacketsButton; // Value injected by FXMLLoader
//
//    @FXML // fx:id="racketTable"
//    private TableView racketTable; // Value injected by FXMLLoader
//
//    @FXML
//    private TextArea consoleTextArea;
//
//    @FXML
//    private Button disconnectButton;
//
//    @FXML
//    private TableColumn<Racket, String> nameCol;
//
//    @FXML
//    private TableColumn<Racket, String> manufacturerCol;
//
//    @FXML
//    private TableColumn<Racket, String> massCol;
//
//    @FXML
//    private TableColumn<Racket, String> headSizeCol;
//
//    @FXML
//    private TableColumn<Racket, String> dominantColorCol;
//
//    @FXML
//    private TableColumn<Racket, String> priceUSDCol;
//
//    private DBUtil dbUtil;
//    private RacketDAO racketDAO;
//
//    @FXML
//    void disconnectButtonPressed(ActionEvent event) throws SQLException, IOException {
//        dbUtil=LoginController.dbUtil;
//        dbUtil.dbDisconnect();
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(RacketApp.loadFXML("login"), 800, 600);
//        stage.setScene(scene);
//
//    }
//
//    @FXML
//    void addRacketButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
//
//        try {
//
//            if (!racketNameToAddTextField.getText().equals(null)) {
//
//                racketDAO.insertRacket(racketNameToAddTextField.getText());
//                consoleTextArea.appendText("New package " + racketNameToAddTextField.getText() + " sent." + "\n");
//
//            }
//        } catch (SQLException e) {
//            consoleTextArea.appendText("Error occurred while sending package.\n");
//            throw e;
//        }
//
//    }
//
//
//    @FXML
//    void selectRacketButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
//
//        try {
//
//            if (!selectRacketNameTextField.getText().equals(null)) {
//
//                racketTable.getItems().clear();
//                ObservableList<Racket> wineData = racketDAO.searchRackets(selectRacketNameTextField.getText());
//                populateRackets(wineData);
////
//            }
//        } catch (SQLException e) {
//            consoleTextArea.appendText("Error occurred while getting wines from DB.\n");
//            throw e;
//        }
//
//    }
//
//    @FXML
//    void showPackagesButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
//
//        try {
//
//            racketTable.getItems().clear();
//            ObservableList<Racket> racketData = racketDAO.showAllRackets();
//            populateRackets(racketData);
//
//        } catch (SQLException e) {
//            consoleTextArea.appendText("Error occurred while getting packages from DB.\n");
//            throw e;
//        }
//
//    }
//
//    private void populateRackets(ObservableList<Racket> racketData) {
//        racketTable.setItems(racketData);
//    }
//
//    @FXML
//        // This method is called by the FXMLLoader when initialization is complete
//    void initialize() {
//        assert consoleTextArea != null : "fx:id=\"consoleTextArea\" was not injected: check your FXML file 'user.fxml'.";
//        assert racketNameToAddTextField != null : "fx:id=\"racketNameToAddTextField\" was not injected: check your FXML file 'user.fxml'.";
//        assert addRacketButton != null : "fx:id=\"addRacketButton\" was not injected: check your FXML file 'user.fxml'.";
//        assert selectRacketNameTextField != null : "fx:id=\"selectRacketNameTextField\" was not injected: check your FXML file 'user.fxml'.";
//        assert selectRacketButton != null : "fx:id=\"selectRacketButton\" was not injected: check your FXML file 'user.fxml'.";
//        assert showRacketsButton != null : "fx:id=\"showRacketsButton\" was not injected: check your FXML file 'user.fxml'.";
//        assert racketTable != null : "fx:id=\"racketTable\" was not injected: check your FXML file 'user.fxml'.";
//        assert disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'user.fxml'.";
//
//        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'user.fxml'.";
//        assert manufacturerCol != null : "fx:id=\"manufacturerCol\" was not injected: check your FXML file 'user.fxml'.";
//        assert massCol != null : "fx:id=\"massCol\" was not injected: check your FXML file 'user.fxml'.";
//        assert headSizeCol != null : "fx:id=\"headSizeCol\" was not injected: check your FXML file 'user.fxml'.";
//        assert dominantColorCol != null : "fx:id=\"dominantColorCol\" was not injected: check your FXML file 'user.fxml'.";
//        assert priceUSDCol != null : "fx:id=\"priceUSDCol\" was not injected: check your FXML file 'user.fxml'.";
//
//        consoleTextArea.setText(LoginController.consoleText.toString());
//
//
//    }
//
//
//}

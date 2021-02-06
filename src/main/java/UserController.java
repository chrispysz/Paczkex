import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;

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
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ComboBox<String> destinationComboBox;

    @FXML
    private TableView<Package> ordersTable;

    @FXML
    private TableColumn<Package, Integer> idCol;

    @FXML
    private TableColumn<Package, String> rozmiarCol;

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
    private TableColumn<Package, String> nadawcaCol;

    @FXML
    private TableColumn<Package, String> odbiorcaCol;

    @FXML
    private AnchorPane sendPane;

    @FXML
    private AnchorPane statPane;

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button showAllButton;

    @FXML
    private TextField leftTextField1;

    @FXML
    private TextField rightTextField1;

    @FXML
    private Button processButton1;

    @FXML
    private TextArea txtResult;

    private DBUtil dbUtil;
    private PackageDAO packageDAO;
    private boolean isAdmin = false;

    @FXML
    void disconnectButtonPressed(ActionEvent event) throws IOException, SQLException {
        dbUtil.dbDisconnect();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(RacketApp.loadFXML("login"), 800, 600);
        stage.setScene(scene);
    }

    @FXML
    void onProcessClick(ActionEvent event) throws SQLException, ClassNotFoundException {
        //TODO: Implementacja wyboru paczkomatu, w którym aktualnie się znajdujemy(z którego ndajaemy/odbieramy)
        try {
            if (comboBox1.getValue().equals("Nadanie")) {
                String nadanieStmt = "call nadanie('" + sizeComboBox.getValue() + "'" +
                        ",'NADANA',null," + dbUtil.getUserName() + "," + leftTextField.getText() + ",1," + destinationComboBox.getValue().charAt(0) + ");";
                dbUtil.dbExecuteUpdate(nadanieStmt);
                consoleTextArea.appendText(nadanieStmt + "\n");
            }
            if (comboBox1.getValue().equals("Odbiór")) {
                String odbiorStmt = "call odbior("+comboBox2.getValue()+","+dbUtil.getUserName()+");";
                dbUtil.dbExecuteUpdate(odbiorStmt);
                consoleTextArea.appendText(odbiorStmt + "\n");
            }

            updateComboBoxes();
            
        }catch (SQLException | ClassNotFoundException e){
            consoleTextArea.appendText("Wystąpił błąd podczas przetwarzania paczki! Upewnij się," +
                    " że wszystkie pola są odpowiednio uzupełnione.\n");
            throw e;
        }
    }

    @FXML
    void onProcess1Click(ActionEvent event) throws SQLException, ClassNotFoundException {

        String data = leftTextField1.getText();

        if (rightTextField1.getText().equals("")) {
            //UTARG W DANYM DNIU


            ResultSet rs = dbUtil.dbExecuteQuery("select utarg('" + data + "') as utarg;");

            StringBuilder utarg = new StringBuilder();

            while (rs.next()) {

                Float n = rs.getFloat("utarg");
                utarg.append(n);

            }

            txtResult.setText(utarg.toString() + "zł");

        } else {
            //STATYSTYKI DANEGO PACZKOMATU W DANYM DNIU
            String id = rightTextField1.getText();
            ResultSet rs = dbUtil.dbExecuteQuery("CALL statystyki(" + id + ", '" + data + "');");

            StringBuilder utarg = new StringBuilder();

            while (rs.next()) {

                Float w = rs.getFloat("wysłane");
                Float o = rs.getFloat("odebrane");
                utarg.append("Wysłane: " + w + "\n" + "Odebrane: " + o);

            }

            txtResult.setText(utarg.toString());


        }
    }

    @FXML
    void showAllButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        //TODO: Wyświetlanie paczek związanych z użytkownikiem (wyświetla wszystkie)
        try {

            ordersTable.getItems().clear();
            ObservableList<Package> packageData = packageDAO.showAllOrders();
            populateRackets(packageData);

        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("Error occurred while getting packages from DB.\n");
            throw e;
        }
    }

    private void populateRackets(ObservableList<Package> packageData) {
        ordersTable.setItems(packageData);
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert leftTextField != null : "fx:id=\"leftTextField\" was not injected: check your FXML file 'user.fxml'.";
        assert rightTextField != null : "fx:id=\"rightTextField\" was not injected: check your FXML file 'user.fxml'.";
        assert processButton != null : "fx:id=\"processButton\" was not injected: check your FXML file 'user.fxml'.";
        assert comboBox1 != null : "fx:id=\"comboBox1\" was not injected: check your FXML file 'user.fxml'.";
        assert comboBox2 != null : "fx:id=\"comboBox2\" was not injected: check your FXML file 'user.fxml'.";
        assert sizeComboBox != null : "fx:id=\"sizeComboBox\" was not injected: check your FXML file 'user.fxml'.";
        assert destinationComboBox != null : "fx:id=\"destinationComboBox\" was not injected: check your FXML file 'user.fxml'.";
        assert ordersTable != null : "fx:id=\"ordersTable\" was not injected: check your FXML file 'user.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'user.fxml'.";
        assert rozmiarCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'user.fxml'.";
        assert stanCol != null : "fx:id=\"stanCol\" was not injected: check your FXML file 'user.fxml'.";
        assert dataNadaniaCol != null : "fx:id=\"dataNadaniaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert dataOdbioruCol != null : "fx:id=\"dataOdbioruCol\" was not injected: check your FXML file 'user.fxml'.";
        assert ulicaCol != null : "fx:id=\"ulicaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert nrCol != null : "fx:id=\"nrCol\" was not injected: check your FXML file 'user.fxml'.";
        assert miastoCol != null : "fx:id=\"miastoCol\" was not injected: check your FXML file 'user.fxml'.";
        assert krajCol != null : "fx:id=\"krajCol\" was not injected: check your FXML file 'user.fxml'.";
        assert nadawcaCol != null : "fx:id=\"nadawcaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert odbiorcaCol != null : "fx:id=\"odbiorcaCol\" was not injected: check your FXML file 'user.fxml'.";
        assert consoleTextArea != null : "fx:id=\"consoleTextArea\" was not injected: check your FXML file 'user.fxml'.";
        assert disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'user.fxml'.";
        assert showAllButton != null : "fx:id=\"showAllButton\" was not injected: check your FXML file 'user.fxml'.";
        assert statPane != null : "fx:id=\"statPane\" was not injected: check your FXML file 'user.fxml'.";
        assert leftTextField1 != null : "fx:id=\"leftTextField1\" was not injected: check your FXML file 'user.fxml'.";
        assert rightTextField1 != null : "fx:id=\"rightTextField1\" was not injected: check your FXML file 'user.fxml'.";
        assert processButton1 != null : "fx:id=\"processButton1\" was not injected: check your FXML file 'user.fxml'.";

        dbUtil = LoginController.dbUtil;
        packageDAO = new PackageDAO(dbUtil, consoleTextArea);

        isAdmin = dbUtil.getUserName().contains("admin");

        if (isAdmin) {
            sendPane.setVisible(false);
            statPane.setVisible(true);

            leftTextField1.setPromptText("data YYYY-MM-DD");
            leftTextField1.setDisable(false);
            rightTextField1.setPromptText("id przesyłkomatu");
            rightTextField1.setDisable(false);
            txtResult.setVisible(true);
            txtResult.setPromptText("data -> utarg \t\t\t" +
                    "data i id -> statystyki");
        } else {
            statPane.setVisible(false);
            sendPane.setVisible(true);
            rightTextField1.setPromptText("id przesyłkomatu");
        }

        consoleTextArea.setText(LoginController.consoleText.toString());

        ArrayList<String> nadOdbList=new ArrayList<>();
        nadOdbList.add("Nadanie");
        nadOdbList.add("Odbiór");
        ArrayList<String> sizeList=new ArrayList<>();
        sizeList.add("S");
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");

        sizeComboBox.setItems(FXCollections.observableArrayList(sizeList));

        comboBox1.setItems(FXCollections.observableArrayList(nadOdbList));
        comboBox1.setValue("Nadanie");
        comboBox1.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Tryb: "+newVal);
            if (newVal.equals("Nadanie")){
                sizeComboBox.setVisible(true);
                leftTextField.setVisible(true);
                rightTextField.setVisible(true);
                comboBox2.setVisible(false);
                destinationComboBox.setVisible(true);
            }
            if (newVal.equals("Odbiór")){
                leftTextField.setVisible(false);
                rightTextField.setVisible(false);
                comboBox2.setVisible(true);
                sizeComboBox.setVisible(false);
                destinationComboBox.setVisible(false);
            }
        });


        updateComboBoxes();
        
        

    }

    private void updateComboBoxes() throws SQLException, ClassNotFoundException {
        
        destinationComboBox.getItems().clear();
        comboBox2.getItems().clear();
        
        ArrayList<String> paczkomatList=new ArrayList<>();
        ResultSet rs = dbUtil.dbExecuteQuery("select * from paczkomaty;");
        while (rs.next()) {
            String paczkomat = rs.getString(1)+": "+rs.getString(2)+" "+
                    rs.getString(3)+" "+rs.getString(4)+", "+rs.getString(5);
            paczkomatList.add(paczkomat);
        }
        destinationComboBox.setItems(FXCollections.observableArrayList(paczkomatList));

        //TODO: Tu mają się wyświetlać tylko paczki znajdujące się w aktualnym paczkomacie
        ArrayList<String> paczkiList=new ArrayList<>();
        rs = dbUtil.dbExecuteQuery("select * from paczki where id_odbiorcy="+dbUtil.getUserName()+";");
        while (rs.next()) {
            String paczka = rs.getString(1);
            paczkiList.add(paczka);
        }
        comboBox2.setItems(FXCollections.observableArrayList(paczkiList));
    }

}

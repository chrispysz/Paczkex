import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageDAO {

    private DBUtil dbUtil;
    private TextArea consoleTextArea;

    public PackageDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        this.dbUtil = dbUtil;
        this.consoleTextArea = consoleTextArea;
    }

    private ObservableList<Package> getPackageList(ResultSet rs) throws SQLException {

        ObservableList<Package> PackageList = FXCollections.observableArrayList();

        while (rs.next()) {

            Package r = new Package();
            r.setId(rs.getInt("ID"));
            r.setRozmiar(rs.getString("rozmiar"));
            r.setStan_paczki(rs.getString("stan"));
            r.setData_nadania(rs.getString("data_nadania"));
            r.setData_odbioru(rs.getString("data_odbioru"));
            r.setUlica(rs.getString("ulica"));
            r.setNr(rs.getInt("nr"));
            r.setMiasto(rs.getString("miasto"));
            r.setKraj(rs.getString("kraj"));
            r.setNadawca(rs.getString("nadawca"));
            r.setOdbiorca(rs.getString("odbiorca"));
            PackageList.add(r);
        }

        return PackageList;
    }



    public ObservableList<Package> showAllOrders(String id) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM zlecenia " +
                "join paczki on zlecenia.ID=paczki.id_paczki "+
                "join klienci nadawcy on nadawcy.id_klienta = paczki.id_nadawcy " +
                "join klienci odbiorcy on odbiorcy.id_klienta = paczki.id_odbiorcy " +
                "where nadawcy.id_klienta="+id+" or odbiorcy.id_klienta="+id+";";

        try {

            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);

            ObservableList<Package> PackageList = getPackageList(resultSet);
            consoleTextArea.appendText("Wyświetlono Twoje paczki!"+"\n");

            return PackageList;


        } catch (SQLException e) {
            consoleTextArea.appendText("W czasie wczytywanie listy paczek wystąpił błąd! \n");
            throw e;
        }

    }



}

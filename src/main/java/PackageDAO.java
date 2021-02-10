import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for populating the table in user screen.
 */
public class PackageDAO {

    private DBUtil dbUtil;
    private TextArea consoleTextArea;

    /**
     * Main constructor.
     * @param dbUtil Current user.
     * @param consoleTextArea TextArea where messages are going to be displayed.
     */
    public PackageDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        this.dbUtil = dbUtil;
        this.consoleTextArea = consoleTextArea;
    }

    /**
     * Creates a new list of packages.
     * @param rs Result of the MySQL query.
     * @return Returns an easily-processable list of packages.
     * @throws SQLException When an unspecified MySQL error occurred.
     */
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


    /**
     * Processes the MySQL query to display appropriate packages.
     * @param id ID of the user whose packages are going to be displayed.
     * @param isAdmin Whether the user is an admin and has access to all the packages or not.
     * @return A finished list of packages.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     */
    public ObservableList<Package> showAllOrders(String id, boolean isAdmin) throws SQLException, ClassNotFoundException {

        String selectStmt;
        if (isAdmin) {
            selectStmt = "SELECT * FROM zlecenia;";
        } else {

            selectStmt = "SELECT * FROM zlecenia " +
                    "join paczki on zlecenia.ID=paczki.id_paczki " +
                    "join klienci nadawcy on nadawcy.id_klienta = paczki.id_nadawcy " +
                    "join klienci odbiorcy on odbiorcy.id_klienta = paczki.id_odbiorcy " +
                    "where nadawcy.id_klienta=" + id + " or odbiorcy.id_klienta=" + id + ";";
        }

        try {

            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);

            ObservableList<Package> PackageList = getPackageList(resultSet);
            consoleTextArea.appendText("Wyświetlono Twoje paczki!" + "\n");

            return PackageList;


        } catch (SQLException e) {
            consoleTextArea.appendText("W czasie wczytywanie listy paczek wystąpił błąd! \n");
            throw e;
        }

    }


}

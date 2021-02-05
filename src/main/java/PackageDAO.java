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



    public ObservableList<Package> showAllOrders() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM zlecenia;";

        try {

            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);

            ObservableList<Package> PackageList = getPackageList(resultSet);
            consoleTextArea.appendText(selectStmt+"\n");

            return PackageList;


        } catch (SQLException e) {
            consoleTextArea.appendText("While searching Packages, an error occurred. \n");
            throw e;
        }

    }

    public void insertPackage(String name) throws SQLException, ClassNotFoundException {

        StringBuilder sb = new StringBuilder("INSERT INTO Packages(model) VALUES('");
        sb.append(name);
        sb.append("');");
        String insertStmt = sb.toString();

        try {

            dbUtil.dbExecuteUpdate(insertStmt);
            consoleTextArea.appendText(insertStmt + "\n");

        } catch (SQLException e) {
            consoleTextArea.appendText("Error occurred while INSERT Operation.");
            throw e;
        }

    }


}

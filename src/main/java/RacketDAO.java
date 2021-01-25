import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RacketDAO {

    private DBUtil dbUtil;
    private TextArea consoleTextArea;

    public RacketDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        this.dbUtil = dbUtil;
        this.consoleTextArea = consoleTextArea;
    }

    private ObservableList<Racket> getRacketList(ResultSet rs) throws SQLException {

        ObservableList<Racket> racketList = FXCollections.observableArrayList();

        while (rs.next()) {

            Racket r = new Racket();
            r.setId(rs.getInt("id"));
            r.setModel(rs.getString("model"));
            r.setManufacturer(rs.getString("manufacturer"));
            r.setMass(rs.getInt("mass"));
            r.setHeadSize(rs.getInt("head_size"));
            r.setDominantColor(rs.getString("dominant_color"));
            r.setPriceUSD(rs.getDouble("priceUSD"));
            racketList.add(r);
        }

        return racketList;
    }


    public ObservableList<Racket> searchRackets(String manuf) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM rackets WHERE manufacturer LIKE '%" + manuf + "%';";

        try {

            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);

            ObservableList<Racket> rackets = getRacketList(resultSet);

            consoleTextArea.appendText(selectStmt + "\n");

            return rackets;

        } catch (SQLException e) {
            consoleTextArea.appendText("While searching a racket from '" + manuf + "' name, an error occurred. \n");
            throw e;
        }

    }

    public ObservableList<Racket> showAllRackets() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM rackets;";

        try {

            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);

            ObservableList<Racket> racketList = getRacketList(resultSet);
            consoleTextArea.appendText(selectStmt);

            return racketList;


        } catch (SQLException e) {
            consoleTextArea.appendText("While searching rackets, an error occurred. \n");
            throw e;
        }

    }

    public void insertRacket(String name) throws SQLException, ClassNotFoundException {

        StringBuilder sb = new StringBuilder("INSERT INTO rackets(model) VALUES('");
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

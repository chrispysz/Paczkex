import javafx.scene.control.TextArea;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.sql.rowset.CachedRowSet;

/**
 * Class responsible for processing MySQL requests.
 */
public class DBUtil {

    private String userName;
    private String userPassword;
    private TextArea consoleTextArea;

    private Connection conn = null;

    /**
     * Primary constructor.
     * @param userName Username of the user who's trying to log in.
     * @param userPassword Password of the user trying to log in.
     * @param consoleTextArea TextArea in which messages are going to be displayed
     * @throws NoSuchAlgorithmException When an error with hashing occurred.
     */
    public DBUtil(String userName, String userPassword, TextArea consoleTextArea) throws NoSuchAlgorithmException {
        this.userName = userName;
        if (userName.equals("default_login_user"))
            this.userPassword = userPassword;
        else
            this.userPassword = Hasher.hashMD5(userPassword);
        this.consoleTextArea = consoleTextArea;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Establishes connection to the database.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     */
    public void dbConnect() throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            consoleTextArea.appendText("No MySQL JDBC Driver found." + "\n");
            e.printStackTrace();
            throw e;
        }

        try {
            conn = DriverManager.getConnection(createURL());
        } catch (SQLException e) {
            consoleTextArea.appendText("Connection Failed! Try again." + "\n");
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Disconnects from the database.
     * @throws SQLException When an unspecified MySQL error occurred.
     */
    public void dbDisconnect() throws SQLException {

        try {

            if (conn != null && !conn.isClosed()) {

                conn.close();

            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Creates the URL used for connection to the database.
     * @return URL used for connection to the database.
     */
    private String createURL() {

        StringBuilder urlSB = new StringBuilder("jdbc:mysql://");
        urlSB.append("localhost:3306/");
        urlSB.append("paczkex?");
        urlSB.append("useUnicode=true&characterEncoding=utf-8");
        urlSB.append("&user=");
        urlSB.append(userName);
        urlSB.append("&password=");
        urlSB.append(userPassword);
        urlSB.append("&serverTimezone=CET");

        return urlSB.toString();
    }

    /**
     * Processes query requests.
     * @param queryStmt MySQL statement to be processed.
     * @return ResultSet of the query.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     */
    public ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs;

        try {

            dbConnect();

            stmt = conn.prepareStatement(queryStmt);

            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetWrapper();

            crs.populate(resultSet);
        } catch (SQLException e) {
            consoleTextArea.appendText("Problem occurred at executeQuery operation. \n");
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }

        return crs;
    }


    /**
     * Processes update requests (insert, update etc.).
     * @param sqlStmt MySQL statement to be processed.
     * @throws SQLException When an unspecified MySQL error occurred.
     * @throws ClassNotFoundException When JDBC driver was not found.
     */
    public void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {

        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);

        } catch (SQLException e) {
            consoleTextArea.appendText("Problem occurred at executeUpdate operation. \n");
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

}

package checkpoint.andela.db;

import checkpoint.andela.Constants.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji.
 */
public class DatabaseManager {
    private String db_Url = Constants.DBURL.toString();
    private String db_Name = Constants.DBNAME.toString();
    private String db_Password = Constants.PASS.toString();
    private String db_User = Constants.USER.toString();
    private String db_driver = Constants.DRIVER.toString();
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseManager() {

    }

    private Connection createDatabaseConnection(String driver) {
        try {
            registerDriver(driver);
            connection = DriverManager.getConnection(db_Url, db_User, db_Password);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection connectToDatabase() throws SQLException {
        registerDriver(db_driver);
        Connection connectDb = DriverManager.getConnection(db_Url + db_Name, db_User, db_Password);
        return connectDb;
    }

    private void registerDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            cnfe.getMessage();
        }
    }

    public void createDatabase(String databaseName) throws SQLException {
        createDatabaseConnection(db_driver);
        String createDbQuery = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        executeQuery(createDbQuery);
    }

    public void createTable(String databaseName, String tableName, ArrayList<String> tableFields) throws Exception {
        createDatabaseConnection(db_driver);
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + databaseName + "." + tableName + " (";
        for (String field : tableFields) {
            createTableQuery += "`" + field + "` text,";
        }
        createTableQuery = createTableQuery.substring(0, createTableQuery.length() - 1) + ")";
        executeQuery(createTableQuery);
    }

    public void removeTable(String databaseName, String tableName) throws Exception {
        createDatabaseConnection(db_driver);
        String removeTable = "DROP TABLE IF EXISTS " + databaseName + "." + tableName;
        executeQuery(removeTable);
    }

    public void insertTableQuery(DatabaseRecord databaseRecord) {
        createDatabaseConnection(db_driver);
        String attribute = "";
        String value = "";

        Hashtable<String, String> insertRecord = databaseRecord.getRecord();
        for (String key : insertRecord.keySet()) {
            if (isColumnAllowed(key)) {
                attribute += "`" + key + "`, ";
                value += "'" + insertRecord.get(key) + "', ";
            }
        }
        attribute = attribute.substring(0, attribute.length() - 2);
        value = value.substring(0, value.length() - 2);

        String insertDataQuery = "INSERT INTO `reactiondb`.`reactions` (" + attribute + " )" + " VALUES (" + value + " );";
        executeQuery(insertDataQuery);
    }

    private boolean isColumnAllowed(String columnName) {
        String[] allowedColumns = {"IN-PATHWAY", "TYPES", "UNIQUE-ID"};

        return Arrays.binarySearch(allowedColumns, columnName) > -1;
    }

    private void executeQuery(String query) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            closeDatabaseConnection();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    private void closeDatabaseConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
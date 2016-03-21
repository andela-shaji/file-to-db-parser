package checkpoint.andela.db;

import checkpoint.andela.Constants.Constants;
import checkpoint.andela.log.LogBuffer;


import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji on 3/10/16.
 */
public class DatabaseWriter implements Runnable {

    BlockingQueue<DatabaseRecord> dbRecords;
    LogBuffer logBuffer = LogBuffer.getBuffer();
    ArrayList<String> existingDatabases = new ArrayList<String>();

    private String db_Url = Constants.DBURL.toString();
    private String db_Name = Constants.DBNAME.toString();
    private String db_Password = Constants.PASS.toString();
    private String db_User = Constants.USER.toString();
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseWriter(BlockingQueue<DatabaseRecord> dbRecords) {
        this.dbRecords = dbRecords;
    }

    public Connection createDatabaseConnection(String driver) {
        try {
            // This will load the MySQL driver
            registerDriver(driver);
            // Setup the connection with the DB
            connection = DriverManager.getConnection(db_Url, db_User, db_Password);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Connection connectToDatabase(String dbName) throws SQLException {
        registerDriver(Constants.DRIVER.toString());
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
        if (!databaseExists(databaseName)) {
            String createDbQuery = "CREATE DATABASE " + databaseName;
            executeQuery(createDbQuery);
            System.out.println("Database successfully created");
        }
    }

    public void deleteDatabase(String databaseName) throws SQLException {
        if (databaseExists(databaseName)) {
            String deleteDatabaseQuery = "DROP DATABASE " + databaseName;
            executeQuery(deleteDatabaseQuery);
            System.out.println("Database successfully deleted");
        }
    }

    public void createTable(String databaseName, String tableName, ArrayList<String> tableFields) throws Exception {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + databaseName + "." + tableName + " (";
        for (String field : tableFields) {
            createTableQuery += "`" + field + "` text,";
        }
        createTableQuery = createTableQuery.substring(0, createTableQuery.length() - 1) + ")";
        executeQuery(createTableQuery);
        System.out.println(tableName + " table successfully created");
    }

    public void removeTable(String databaseName, String tableName) throws Exception {
        String removeTable = "DROP TABLE IF EXISTS " + databaseName + "." + tableName;
        executeQuery(removeTable);
        System.out.println("Table successfully deleted");
    }


    public void writeToDatabase() throws InterruptedException {
        while (!isRecordEmpty()) {
            DatabaseRecord getRecord = getRecord();
            logBuffer.writeToLog("DBWriter", getRecord.getUniqueId());
            insertTableQuery(getRecord);
        }
    }

    public DatabaseRecord getRecord() throws InterruptedException {
        return dbRecords.take();
    }

    private void insertTableQuery(DatabaseRecord databaseRecord) {
        String attribute = "";
        String value = "";

        Hashtable<String, String> insertRecord = databaseRecord.getRecord();
        for (String key : insertRecord.keySet()) {
            attribute += "`" + key + "`, ";
            value += "'" + insertRecord.get(key) + "', ";
        }
        attribute = attribute.substring(0, attribute.length() - 2);
        value = value.substring(0, value.length() - 2);

        String insertDataQuery = "INSERT INTO `reactiondb`.`reactions` (" + attribute + " )" + " VALUES (" + value + " );";
        executeQuery(insertDataQuery);
        System.out.println("New row inserted");
    }

    public void executeQuery(String query) {
        try {

            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public boolean databaseExists(String databaseName) throws SQLException {
        existingDatabases = getExistingDatabases();
        return existingDatabases.contains(databaseName);
    }

    public ArrayList<String> getExistingDatabases() throws SQLException {
        createDatabaseConnection(Constants.DRIVER.toString());
        ArrayList<String> listDatabases = new ArrayList<String>();
        ResultSet databases = connection.getMetaData().getCatalogs();
        while (databases.next()) {
            listDatabases.add(databases.getString(1));
            existingDatabases = listDatabases;
        }
        return listDatabases;
    }

    public boolean tableExists(String databaseName, String tableName) throws SQLException {
        Connection connectDb = connectToDatabase(databaseName);
        DatabaseMetaData databaseMetaData = connectDb.getMetaData();
        ResultSet tables = databaseMetaData.getTables(null, null, tableName, null);
        boolean existingTables = tables.next();
        closeDatabaseConnection();
        return existingTables;
    }

    public boolean isRecordEmpty() {
        return dbRecords.isEmpty();
    }

    public void closeDatabaseConnection() {
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
    @Override
    public void run() {
        try {
            writeToDatabase();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }
}

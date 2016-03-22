package checkpoint.andela.db;

import checkpoint.andela.Constants.Constants;
import checkpoint.andela.parser.AttributeValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class DatabaseManagerTest {

    DatabaseManager databaseManager;
    DatabaseRecord databaseRecord;
    AttributeValuePair pair;
    AttributeValuePair pair2;
    AttributeValuePair pair3;

    @Before
    public void setUp() throws Exception {
        databaseManager = new DatabaseManager();
        databaseRecord = new DatabaseRecord();
        pair = new AttributeValuePair();
        pair.setKey("UNIQUE-ID");
        pair.setValue(Double.toString(Math.random()));
        pair2 = new AttributeValuePair();
        pair2.setKey("TYPES");
        pair2.setValue("Small-Molecule-Reactions");
        databaseRecord.addColumn(pair2);
        databaseRecord.addColumn(pair);
        pair3 = new AttributeValuePair();
        pair3.setKey("ATOM-MAPPINGS");
        pair3.setValue(":UNBALANCED");
        databaseRecord.addColumn(pair3);
        databaseRecord.addColumn(pair);
    }

    @Test
    public void testCreateDatabaseConnection() throws Exception {
        databaseManager.connectToDatabase();
    }
    @Test
    public void testCreateDatabase() throws Exception {
        databaseManager.createDatabase("reactiondb");
    }

    @Test
    public void testCreateTable() throws Exception {
        databaseManager.removeTable("reactiondb", "reactions");

        ArrayList<String> tableFields = new ArrayList<String>();
        tableFields.add("UNIQUE-ID");
        tableFields.add("TYPES");
        tableFields.add("IN-PATHWAY");

        databaseManager.createTable("reactiondb", "reactions", tableFields);
    }

    @Test
    public void testInsertTableQuery() throws Exception {
        databaseManager.insertTableQuery(databaseRecord);
    }

}
package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValuePair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class DatabaseWriterTest {
    DatabaseRecord databaseRecord;
    DatabaseWriter dbWriter;
    String employeesDatabase;
    String studentDatabase;
    String reactionsDatabase;
    String reactionsTable;
    ArrayList<String> tableFields;
    BlockingQueue<DatabaseRecord> dbRecords;

    @Before
    public void setUp() throws Exception {

        databaseRecord = new DatabaseRecord();
        dbRecords = new ArrayBlockingQueue<DatabaseRecord>(10);
        dbWriter = new DatabaseWriter(dbRecords);
        employeesDatabase = "employees";
        studentDatabase = "students";
        reactionsDatabase = "reactiondb";
        reactionsTable = "reactions";
        tableFields = new ArrayList<String>();
        tableFields.add("UNIQUE-ID");
        tableFields.add("TYPES");
        tableFields.add("ATOM-MAPPINGS");
    }

    @Test
    public void testCreateDatabase() throws Exception {
        dbWriter.deleteDatabase(employeesDatabase);
        dbWriter.createDatabase(employeesDatabase);
        assertTrue(dbWriter.databaseExists(employeesDatabase));
    }

    @Test
    public void testDeleteDatabase() throws Exception {
        //assertFalse(dbWriter.databaseExists(studentDatabase));
        dbWriter.createDatabase(studentDatabase);
        dbWriter.deleteDatabase(studentDatabase);
        assertFalse(dbWriter.databaseExists(studentDatabase));
    }

    @Test
    public void testRemoveTable() throws Exception {
        dbWriter.deleteDatabase(reactionsDatabase);
        dbWriter.createDatabase(reactionsDatabase);
        dbWriter.createTable(reactionsDatabase, reactionsTable, tableFields);
        dbWriter.removeTable(reactionsDatabase, reactionsTable);
        assertFalse(dbWriter.tableExists(reactionsDatabase,reactionsTable));
    }

    @Test
    public void testCreateTable() throws Exception {
        dbWriter.deleteDatabase(reactionsDatabase);
        dbWriter.createDatabase(reactionsDatabase);
        dbWriter.createTable(reactionsDatabase, reactionsTable, tableFields);
        assertTrue(dbWriter.tableExists(reactionsDatabase, reactionsTable));
    }

    @Test
    public void testDatabaseExists() throws Exception {
        dbWriter.deleteDatabase(reactionsDatabase);
        assertFalse(dbWriter.databaseExists(reactionsDatabase));

        dbWriter.createDatabase(reactionsDatabase);
        assertTrue(dbWriter.databaseExists(reactionsDatabase));
    }

    @Test
    public void testWriteToDatabase() throws Exception {
        int beforeRecords = dbRecords.size();
        assertTrue(beforeRecords == 0);

        AttributeValuePair pair = new AttributeValuePair();
        pair.setKey("UNIQUE-ID");
        pair.setValue(Double.toString(Math.random()));
        AttributeValuePair pair2 = new AttributeValuePair();
        pair2.setKey("TYPES");
        pair2.setValue("Small-Molecule-Reactions");
        databaseRecord.addColumn(pair2);
        databaseRecord.addColumn(pair);
        AttributeValuePair pair3 = new AttributeValuePair();
        pair3.setKey("ATOM-MAPPINGS");
        pair3.setValue(":UNBALANCED");
        databaseRecord.addColumn(pair3);
        databaseRecord.addColumn(pair);

        dbRecords.put(databaseRecord);
        int afterRecords =  dbRecords.size();

        dbWriter.createDatabaseConnection(DatabaseConstants.DRIVER);
        dbWriter.createDatabase(reactionsDatabase);
        dbWriter.createTable(reactionsDatabase, reactionsTable, tableFields);
        dbWriter.writeToDatabase();

        assertTrue(afterRecords > beforeRecords);
    }
}
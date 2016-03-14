package checkpoint.andela.db;

import checkpoint.andela.log.LogBuffer;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by suadahaji on 3/13/16.
 */
public class DatabaseWriterTest {
    DatabaseWriter dbWriter;
    String employeesDatabase;
    String studentDatabase;
    String reactionsDatabase;
    String reactionsTable;
    ArrayList<String> tableFields;
    ArrayList<String> tables;

    @Before
    public void setUp() throws Exception {
        dbWriter = new DatabaseWriter();
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
    public void testCreateTable() throws Exception {
        dbWriter.deleteDatabase(reactionsDatabase);
        dbWriter.createDatabase(reactionsDatabase);
        dbWriter.createTable(reactionsDatabase, reactionsTable, tableFields);
        assertTrue(dbWriter.tableExists(reactionsDatabase, reactionsTable));
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
    public void testDatabaseExists() throws Exception {
        dbWriter.deleteDatabase(reactionsDatabase);
        assertFalse(dbWriter.databaseExists(reactionsDatabase));

        dbWriter.createDatabase(reactionsDatabase);
        assertTrue(dbWriter.databaseExists(reactionsDatabase));

    }



}
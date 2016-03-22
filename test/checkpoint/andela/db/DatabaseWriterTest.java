package checkpoint.andela.db;

import checkpoint.andela.Constants.Constants;
import checkpoint.andela.parser.AttributeValuePair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        dbRecords = new LinkedBlockingQueue<DatabaseRecord>();
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

        dbWriter.writeToDatabase();

        assertTrue(afterRecords > beforeRecords);
    }
}
package checkpoint.andela.parser;

import checkpoint.andela.Constants.Constants;
import checkpoint.andela.db.DatabaseBuffer;
import checkpoint.andela.db.DatabaseRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class FileParserTest {
    DatabaseBuffer databaseBuffer = DatabaseBuffer.getDbBufferInstance();
    private BlockingQueue<DatabaseRecord> parseToDbBuffer = databaseBuffer.getAllRecords();

    private String filePath;
    FileParser fileParser;

    @Before
    public void setUp() throws Exception {
        filePath = Constants.FILEPATH.toString();
        fileParser = new FileParser(parseToDbBuffer, filePath);
    }

    @Test
    public void testwritetoBuffer() throws Exception {
        String line1 = "LEFT - WATER";
        String line2 = "TYPES - Chemical-Reactions";

        String[] rowData = line1.split(" - ");
        String[] rowData2 = line2.split(" - ");
        assertTrue(rowData.length > 1);

        AttributeValuePair pair = new AttributeValuePair();
        AttributeValuePair pair2 = new AttributeValuePair();

        pair.setKey(rowData[0].trim());
        pair.setValue(rowData[1].trim());

        pair2.setKey(rowData2[0].trim());
        pair2.setValue(rowData2[1].trim());

        assertEquals(pair.getKey(), "LEFT");
        assertEquals(pair2.getValue(), "Chemical-Reactions");

        DatabaseRecord newDataRecord = new DatabaseRecord();

        newDataRecord.addColumn(pair);
        assertEquals(newDataRecord.getDbRecordSize(), 1);

        newDataRecord.addColumn(pair2);
        assertEquals(newDataRecord.getDbRecordSize(), 2);

        fileParser.writeToBuffer();
    }

}
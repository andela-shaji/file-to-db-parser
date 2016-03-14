package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseBuffer;
import checkpoint.andela.db.DatabaseConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by suadahaji on 3/13/16.
 */
public class FileParserTest {
    private BlockingQueue<DatabaseBuffer> dataRecords = new ArrayBlockingQueue<DatabaseBuffer>(10);
    private String filePath;
    FileParser fileParser;



    @Before
    public void setUp() throws Exception {
        filePath = DatabaseConstants.FILEPATH;
        fileParser = new FileParser(dataRecords, filePath);
    }
    @Test
    public void testFileParser() throws Exception {
        Thread fileParserThread = new Thread(fileParser);
        fileParserThread.start();
        assertTrue(fileParserThread.isAlive());

       assertTrue(dataRecords.size() > 0);
    }

    @Test
    public void testProcessLine() throws Exception {
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

        DatabaseBuffer newDataRecord = new DatabaseBuffer();

        newDataRecord.addRow(pair);
        assertEquals(newDataRecord.getdbRecordSize(), 1);

        newDataRecord.addRow(pair2);
        assertEquals(newDataRecord.getdbRecordSize(), 2);
    }

}
package checkpoint.andela.log;

import checkpoint.andela.db.DatabaseConstants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by suadahaji on 3/14/16.
 */
public class LogBufferTest {

    String fileParserLog;
    String databaseLog;
    String columnValue;
    LogBuffer logBuffer;
    LogWriter logWriter;




    @Before
    public void setUp() throws Exception {
        logBuffer = LogBuffer.getBuffer();

        fileParserLog = "FileParser";
        databaseLog = "DBWriter";

        columnValue = "RXN-8739";
        logBuffer.writeToLog(fileParserLog, columnValue);
        //logBuffer.writeToLog(databaseLog, columnValue);
    }


    @Test
    public void testWriteToFile() throws Exception {
        File file = new File(DatabaseConstants.LOGPATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        long lengthBefore = file.length();

        logWriter = new LogWriter(DatabaseConstants.LOGPATH);

        logWriter.writeToFile();

        long lengthAfter = file.length();

        assertTrue(lengthAfter > lengthBefore);

    }
    @Test
    public void testGetBuffer() throws Exception {

    }

    @Test
    public void testWriteToLog() throws Exception {

    }
}
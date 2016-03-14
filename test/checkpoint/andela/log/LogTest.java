package checkpoint.andela.log;

import checkpoint.andela.db.DatabaseConstants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by suadahaji.
 */
public class LogTest {

    String fileParserLog;
    String columnValue;
    LogBuffer logBuffer;
    LogWriter logWriter;

    @Before
    public void setUp() throws Exception {
        logBuffer = LogBuffer.getBuffer();
        fileParserLog = "FileParser";
        columnValue = "RXN-8739";
    }

    /*@Test
    public void testWriteToLog() throws Exception {
        logBuffer.getLogList();
        int logBufferSizeBefore = logBuffer.getLogListSize();

        logBuffer.writeToLog(fileParserLog, columnValue);

        int logBufferSizeAfter = logBuffer.getLogListSize();

        assertTrue(logBufferSizeAfter > logBufferSizeBefore);
    }*/

    @Test
    public void testWriteToFile() throws Exception {
        File file = new File(DatabaseConstants.LOGPATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        long lengthBefore = file.length();

        logWriter = new LogWriter(DatabaseConstants.LOGPATH);

        logWriter.run();

        long lengthAfter = file.length();

        assertTrue(lengthAfter > lengthBefore);

    }
}
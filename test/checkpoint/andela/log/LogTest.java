package checkpoint.andela.log;

import checkpoint.andela.Constants.Constants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by suadahaji.
 */
public class LogTest {

    LogWriter logWriter;
    String logPath;
    LogBuffer logBuffer;
    String currentLog;
    String columnValue;

    @Before
    public void setUp() throws Exception {
        currentLog = "FileParser";
        columnValue = "123456";
        logBuffer = new LogBuffer();
        logPath = Constants.LOGPATH.toString();
        logWriter = new LogWriter(logPath);
    }

    @Test
    public void testWriteToFile() throws Exception {
        logBuffer.writeToLogBuffer(currentLog, columnValue);
        logWriter.run();
    }
}
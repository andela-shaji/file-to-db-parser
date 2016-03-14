package checkpoint.andela.main;

import checkpoint.andela.db.DatabaseConstants;
import checkpoint.andela.db.DatabaseRecord;
import checkpoint.andela.parser.AttributeValuePair;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by suadahaji.
 */
public class MainTest {
    String logPath;
    String filePath;
    Main main;

    @Before
    public void setUp() throws Exception {
        logPath = DatabaseConstants.LOGPATH;
        filePath = DatabaseConstants.FILEPATH;
        main = new Main(filePath, logPath);
    }

    @Test
    public void testParseToDatabase() throws Exception{
        main.parseToDatabase();
    }
}
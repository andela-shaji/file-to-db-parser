package checkpoint.andela.main;

import checkpoint.andela.Constants.Constants;
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
        logPath = Constants.LOGPATH.toString();
        filePath = Constants.FILEPATH.toString();
        main = new Main(filePath, logPath);
    }

    @Test
    public void testParseToDatabase() throws Exception{
        main.parseToDatabase();
    }
}
package checkpoint.andela.main;

import checkpoint.andela.db.DatabaseBuffer;
import checkpoint.andela.db.DatabaseRecord;
import checkpoint.andela.db.DatabaseWriter;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.FileParser;

import java.util.concurrent.*;

/**
 * Created by suadahaji.
 */
public class Main {
    private String logPath;
    private String filePath;

    DatabaseBuffer databaseBuffer = DatabaseBuffer.getDbBufferInstance();
    BlockingQueue<DatabaseRecord> parseToDbBuffer = databaseBuffer.getAllRecords();

    public Main(String filePath, String logPath) throws Exception {
        setFilePath(filePath);
        setLogPath(logPath);
    }

    public void parseToDatabase() throws InterruptedException {
        Thread fileParserThread = new Thread(new FileParser(parseToDbBuffer, filePath));
        Thread dbWriterThread = new Thread(new DatabaseWriter(parseToDbBuffer));
        Thread logWriterThread = new Thread(new LogWriter(logPath));

        fileParserThread.run();
        dbWriterThread.run();
        logWriterThread.run();
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}

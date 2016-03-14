package checkpoint.andela.main;

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

    public static BlockingQueue<DatabaseRecord> dbRecords = new ArrayBlockingQueue<DatabaseRecord>(5);
    private static Future newActivity = null;

    Runnable fileParserThread;
    Runnable dbWriterThread;
    Runnable logWriteThread;

    public Main(String filePath, String logPath) throws Exception {
        setFilePath(filePath);
        setLogPath(logPath);
    }

    public void parseToDatabase() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        fileParserThread = new FileParser(dbRecords, filePath);
        dbWriterThread = new DatabaseWriter(dbRecords);
        logWriteThread = new LogWriter(logPath);

        executor.submit(fileParserThread);
        executor.submit(dbWriterThread);
        executor.submit(logWriteThread);

        if (newActivity == null) {
            executor.awaitTermination(10, TimeUnit.SECONDS);
            executor.shutdown();
        }


    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}

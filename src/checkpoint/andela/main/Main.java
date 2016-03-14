package checkpoint.andela.main;

import checkpoint.andela.db.DatabaseBuffer;
import checkpoint.andela.db.DatabaseWriter;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.FileParser;

import java.util.concurrent.*;

/**
 * Created by suadahaji.
 */
public class Main {
    private String logPath;
    private String dbPath;

    public static BlockingQueue<DatabaseBuffer> dbRecords = new ArrayBlockingQueue<DatabaseBuffer>(1);
    private static Future newActivity = null;

    Runnable fileParserThread;
    Runnable dbWriterThread;
    Runnable logWriteThread;

    public Main(String dbPath, String logPath) throws Exception {
        setDbPath(dbPath);
        setLogPath(logPath);
    }

    public void parseToDatabase() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        fileParserThread = new FileParser(dbRecords, dbPath);
        dbWriterThread = new DatabaseWriter();
        logWriteThread = new LogWriter(logPath);

        executor.submit(fileParserThread);
        executor.submit(dbWriterThread);
        executor.submit(logWriteThread);

        if (newActivity == null) {
            executor.awaitTermination(60, TimeUnit.SECONDS);
            executor.shutdown();
        }


    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
}

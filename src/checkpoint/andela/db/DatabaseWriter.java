package checkpoint.andela.db;

import checkpoint.andela.log.LogBuffer;

import java.util.concurrent.BlockingQueue;

public class DatabaseWriter extends DatabaseManager implements Runnable {

    DatabaseBuffer databaseBuffer = DatabaseBuffer.getDbBufferInstance();
    private BlockingQueue<DatabaseRecord> parseToDbBuffer = databaseBuffer.getAllRecords();
    LogBuffer logBuffer = LogBuffer.getLogBufferInstance();

    public DatabaseWriter(BlockingQueue<DatabaseRecord> dbRecords) {
        this.parseToDbBuffer = dbRecords;
    }

    public void writeToDatabase() throws InterruptedException {
        while (!isRecordEmpty()) {
            DatabaseRecord record = getRecord();
            insertTableQuery(record);
            logBuffer.writeToLogBuffer("DBWriter", record.getUniqueId());
        }
    }

    public DatabaseRecord getRecord() throws InterruptedException {
        return parseToDbBuffer.take();
    }

    public boolean isRecordEmpty() {
        return parseToDbBuffer.isEmpty();
    }

    @Override
    public void run() {
        try {
            writeToDatabase();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }
}
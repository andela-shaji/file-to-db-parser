package checkpoint.andela.db;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by suadahaji.
 */
public class DatabaseBuffer {
    private static BlockingQueue<DatabaseRecord> databaseRecords = new LinkedBlockingQueue<DatabaseRecord>();

    private static DatabaseBuffer bufferInstance = null;

    public DatabaseBuffer() {}

    public static DatabaseBuffer getDbBufferInstance() {
        if (bufferInstance == null) {
            bufferInstance = new DatabaseBuffer();
        }
        return bufferInstance;
    }

    public BlockingQueue<DatabaseRecord> getAllRecords() {
        return databaseRecords;
    }
}
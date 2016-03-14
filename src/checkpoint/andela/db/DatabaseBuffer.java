package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValuePair;

import java.util.Hashtable;

/**
 * Created by suadahaji.
 */
public class DatabaseBuffer {
    private Hashtable<String, String> databaseRecord;

    public DatabaseBuffer() {
        databaseRecord = new Hashtable<String, String>();
    }

    public void addRow(AttributeValuePair pair) {
        databaseRecord.put(pair.getKey(), pair.getValue());
    }

    public Hashtable<String, String> getRecord() {
        return databaseRecord;
    }

    public int getdbRecordSize() {
        return databaseRecord.size();
    }

    public String getUniqueId() {
        return databaseRecord.get("UNIQUE-ID");
    }
}

package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValuePair;

import java.util.Hashtable;

/**
 * Created by suadahaji on 3/10/16.
 */
public class DatabaseRecord {

    private Hashtable<String, String> databaseRecord;

    public DatabaseRecord() {
        databaseRecord = new Hashtable<String, String>();
    }

    public void addColumn(AttributeValuePair pair) {
        databaseRecord.put(pair.getKey(), pair.getValue());
    }

    public Hashtable<String, String> getRecord() {
        return databaseRecord;
    }

    public int getDbRecordSize() {
        return databaseRecord.size();
    }

    public String getUniqueId() {
        return databaseRecord.get("UNIQUE-ID");
    }

}
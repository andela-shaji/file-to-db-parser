package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseBuffer;
import checkpoint.andela.db.DatabaseRecord;
import checkpoint.andela.log.LogBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji on 3/10/16.
 */
public class FileParser implements Runnable {

    private String filePath;

    private FileReader fileReader;

    DatabaseBuffer databaseBuffer = DatabaseBuffer.getDbBufferInstance();
    private BlockingQueue<DatabaseRecord> records = databaseBuffer.getAllRecords();

    private BufferedReader bufferedReader;

    LogBuffer logBuffer = LogBuffer.getLogBufferInstance();

    public FileParser(BlockingQueue<DatabaseRecord> records, String filePath) {
        this.records = records;
        this.filePath = filePath;
    }

    public BufferedReader readFile() throws IOException {

        fileReader = new FileReader(new File(filePath));

        bufferedReader = new BufferedReader(fileReader);

        return bufferedReader;
    }

    @Override
    public void run() {
        writeToBuffer();
    }

    public void writeToBuffer() {
        try {
            DatabaseRecord newDataRecord = new DatabaseRecord();
            readFile();
            String contentLine = bufferedReader.readLine();
            while (!isNull(contentLine)) {
                if (!hasComment(contentLine) && !hasDelimiter(contentLine)) {
                    processLine(contentLine, newDataRecord);
                } else if (hasDelimiter(contentLine)) {
                    String uniqueId = newDataRecord.getUniqueId();
                    records.put(newDataRecord);
                    logBuffer.writeToLogBuffer("FileParser", uniqueId);
                    newDataRecord = new DatabaseRecord();
                }
                contentLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line, DatabaseRecord newDbRecord) {
        String[] rowData = line.split(" - ");
        if (rowData.length == 2) {
            AttributeValuePair pair = new AttributeValuePair();
            pair.setKey(rowData[0].trim());
            pair.setValue(rowData[1].trim());
            newDbRecord.addColumn(pair);
        }
    }

    private boolean hasComment(String currentLine) {
        if (currentLine.startsWith("#")) {
            return true;
        }
        return false;
    }

    private boolean isNull(String currentLine) {
        return currentLine == null;
    }

    private boolean hasDelimiter(String currentLine) {
        if (currentLine.startsWith("//")) {
            return true;
        }
        return false;
    }

}
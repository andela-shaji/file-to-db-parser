package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseBuffer;
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

    private BlockingQueue<DatabaseBuffer> dataRecords;

    private BufferedReader bufferedReader;

    LogBuffer logBuffer = LogBuffer.getBuffer();

    public FileParser(BlockingQueue<DatabaseBuffer> dataRecords, String filePath) {
        this.dataRecords = dataRecords;
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
            DatabaseBuffer newDataRecord = new DatabaseBuffer();
            readFile();
            String contentLine = bufferedReader.readLine();
            while (!isNull(contentLine)) {
                if (!hasComment(contentLine) && !hasDelimiter(contentLine)) {
                    processLine(contentLine, newDataRecord);
                } else if (hasDelimiter(contentLine)) {
                    String uniqueId = newDataRecord.getUniqueId();
                    dataRecords.put(newDataRecord);
                    logBuffer.writeToLog("FileParser", uniqueId);
                }
                contentLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line, DatabaseBuffer newDbRecord) {
        String[] rowData = line.split(" - ");
        if (rowData.length == 2) {
            AttributeValuePair pair = new AttributeValuePair();
            pair.setKey(rowData[0].trim());
            pair.setValue(rowData[1].trim());
            newDbRecord.addRow(pair);
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
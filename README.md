# A Simple File to DB Parser

This is a multithreaded application, which reads in data from a text document, parses the document and saves the data into a mysql database while generating an action log at the same time.

A thread reads in the data into a buffer(temporary storage), another writes it into the database, and the third writes into a log file simultaneously. Each thread logs its action and records time it takes to complete its task.

## Classes
----

## checkpoint.andela.parser.FileParser
This class implements the Runnable class to read in and parse the attached document(reactant.dat) into a buffer.
```sh
 public BufferedReader readFile() throws IOException {

        fileReader = new FileReader(new File(filePath));

        bufferedReader = new BufferedReader(fileReader);

        return bufferedReader;
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
                    dataRecords.put(newDataRecord);
                    logBuffer.writeToLog("FileParser", uniqueId);
                }
                contentLine = bufferedReader.readLine();
            }
       } catch (Exception e) {
            e.printStackTrace();
       }
 }
```

## checkpoint.andela.db.DatabaseWriter

This class is responsible for collecting data written to the buffer and writing them to the database. 

```sh
public void writeToDatabase() throws InterruptedException {
        while (!isRecordEmpty()) {
            DatabaseRecord getRecord = getRecord();
            logBuffer.writeToLog("DBWriter", getRecord.getUniqueId());
            insertTableQuery(getRecord);
        }
 }
```

## checkpoint.andela.log.LogWriter
This thread picks from the logs written from the log buffer to write a single text file.
```sh
public void writeToDatabase() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            while (!logBuffer.isEmpty()) {
                bufferedWriter.write("\n" + logBuffer.getLogBuffer());
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

The log could look like this:
```sh
FileParser Thread (2015-09-01 10:20:32)---- wrote UNIQUE ID RXN-8739 to buffer
DBWriter Thread (2015-09-01 10:20:33) ----- collected UNIQUE ID RXN1001 from buffer
```

## Test 
Tests for the various packages have also been created.

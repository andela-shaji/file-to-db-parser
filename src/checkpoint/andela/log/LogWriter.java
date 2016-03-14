package checkpoint.andela.log;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji on 3/10/16.
 */
public class LogWriter implements Runnable {

    private LogBuffer logBuffer = LogBuffer.getBuffer();

    private BufferedWriter bufferedWriter;

    private String filePath;


    public LogWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        writeToFile();
    }

    public void writeToFile() {
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

package checkpoint.andela.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji.
 */
public class LogWriter extends LogBuffer implements Runnable {
    private BlockingQueue<String> logBuffer = LogBuffer.getBuffer();

    private BufferedWriter bufferedWriter;

    private String filePath;

    private LogWriter() {
    }

    public LogWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {

    }

    private void writeToFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            bufferedWriter = new BufferedWriter(new FileWriter(file));
            while (!logBuffer.isEmpty()) {
                bufferedWriter.write(getLogBuffer());
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private String getLogBuffer() throws InterruptedException {
        return logBuffer.take();
    }
}

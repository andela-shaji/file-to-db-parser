package checkpoint.andela.log;

import java.io.*;

/**
 * Created by suadahaji.
 */
public class LogWriter implements Runnable {

    private LogBuffer logBuffer = LogBuffer.getLogBufferInstance();

    private BufferedWriter bufferedWriter;

    private String filePath;

    public LogWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            writeToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            while (!logBuffer.isEmpty()) {
                bufferedWriter.write("\n" + logBuffer.getLogList());
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
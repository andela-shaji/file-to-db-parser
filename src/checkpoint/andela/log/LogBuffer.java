package checkpoint.andela.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by suadahaji.
 */
public class LogBuffer {
    private static BlockingQueue<String> logBuffer =  new LinkedBlockingQueue<String>();

    private static LogBuffer bufferInstance = null;

    private String logTime;

    public LogBuffer() {}

    public static LogBuffer getLogBufferInstance() {
        if (bufferInstance == null) {
            bufferInstance = new LogBuffer();
        }
        return bufferInstance;
    }

    public BlockingQueue<String> getLogBuffer() {
        return logBuffer;
    }

    public void writeToLogBuffer(String currentLog, String columnValue) {
        Date date = new Date();

        logTime = new SimpleDateFormat("dd/MM/yy HH:mm").format(date);
        try {
            logBuffer.put(currentLog + " Thread (" + logTime  +  getActivity(currentLog) + "UNIQUE-ID " + columnValue + getTarget(currentLog));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public String getLogList() throws InterruptedException {
        return logBuffer.take();
    }

    public boolean isEmpty() {
        return logBuffer.isEmpty();
    }

    private String getTarget(String currentLog) {
        if (currentLog.equals("FileParser")) {
            return " to buffer.";
        }
        return " from buffer.";
    }

    private String getActivity(String activity) {
        if (activity.equals("FileParser")) {
            return ")---- wrote ";
        }
        return ") --- collected ";
    }
}
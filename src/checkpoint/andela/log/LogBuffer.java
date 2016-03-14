package checkpoint.andela.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji.
 */
public class LogBuffer {
    private static BlockingQueue<String> logBuffer =  new ArrayBlockingQueue<String>(1);

    private String logTime;

    public LogBuffer() {}

    public static BlockingQueue<String> getBuffer () {
        return logBuffer;
    }

    public void writeToLog(String currentLog, String columnValue) {
        Date date = new Date();

        logTime = new SimpleDateFormat("dd/MM/yy HH:mm").format(date);
        try {
            logBuffer.put(currentLog + " Thread (" + logTime + getActivity(currentLog) + "UNIQUE-ID " + columnValue + getTarget(currentLog));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private String getTarget(String currentLog) {
        if (currentLog.equals("FileParser")) {
            return " to buffer.";
        }
        return " from buffer to database";
    }

    private String getActivity(String activity) {
        if (activity.equals("FileParser")) {
            return ")---- wrote ";
        }
        return ") --- collected ";
    }
}

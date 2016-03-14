package checkpoint.andela.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by suadahaji on 3/10/16.
 */
public class LogBuffer {
    private static BlockingQueue<String> logList =  new ArrayBlockingQueue<String>(1);

    private static LogBuffer buffer;

    private String logTime;

    private LogBuffer() {}

    public static LogBuffer getBuffer () {
        if (buffer == null) {
            buffer = new LogBuffer();
        }
        return buffer;
    }

    public void writeToLog(String currentLog, String columnValue) {
        Date date = new Date();

        logTime = new SimpleDateFormat("dd/MM/yy HH:mm").format(date);
        try {
            logList.put(currentLog + " Thread (" + logTime + getActivity(currentLog) + "UNIQUE-ID " + columnValue + getTarget(currentLog));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public String getLogBuffer() throws InterruptedException {
        return logList.take();
    }

    public BlockingQueue<String> getLogList() {
        return logList;
    }

    public boolean isEmpty() {
        return logList.isEmpty();
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

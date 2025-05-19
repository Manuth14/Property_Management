package lk.propertymanagement.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class LoggerFile {

    public static final java.util.logging.Logger loger = java.util.logging.Logger.getLogger("App");

    public static void createLogger(String text) {

        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
            // Specify the full path to the log file
            String logFilePath = "C:/New folder/" + text + date + ".log"; // Change this to your desired path

            // Create parent directories if they don't exist
            java.io.File logFile = new java.io.File(logFilePath);
            logFile.getParentFile().mkdirs();

            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());

            loger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setException(Exception e) {

        loger.log(Level.WARNING, "Warning", e);
    }

    public static void setMessageLogger(String text) {
        loger.log(Level.SEVERE, text);
    }
}

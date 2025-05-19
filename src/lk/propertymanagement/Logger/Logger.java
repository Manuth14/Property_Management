/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.propertymanagement.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author HP
 */
public class Logger {
    public static final java.util.logging.Logger loger = java.util.logging.Logger.getLogger("App");

    public static void createLogger(String text) {

        try {
            
            String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
            FileHandler fileHandler = new FileHandler(" " +text + date + ".log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            loger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLogger(Exception e) {

        loger.log(Level.INFO, "Warning", e);
    }
}

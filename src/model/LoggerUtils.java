/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author User
 */
public class LoggerUtils {

    private static Logger logger;

    static {

        logger = Logger.getLogger("BubbleBayLogger");

        try {

            FileHandler fileHandler = new FileHandler("Bubble_Bay.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            if (logger.getHandlers().length == 0) {
                logger.addHandler(fileHandler);
            }

            logger.setUseParentHandlers(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Logger getLogger() {
        return logger;
    }
    
}

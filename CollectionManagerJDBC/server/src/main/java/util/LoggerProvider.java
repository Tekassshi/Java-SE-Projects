package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider {
    private static Logger logger = LoggerFactory.getLogger("Server");

    public static Logger getLogger() {
        return logger;
    }
}
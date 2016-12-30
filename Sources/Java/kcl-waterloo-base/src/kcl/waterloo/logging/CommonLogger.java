/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.logging;

import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import kcl.waterloo.gui.images.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * A CommonLogger is used by all internal logging operations in the core
 * Waterloo code. It uses the slf4j logging facade. It has no dependencies on
 * any specific external logging framework.
 *
 * CommonLogger also provides some static methods that maintains a count of
 * background processes such as potentially lengthy file saves.
 *
 * @author Malcolm Lidierth
 */
public class CommonLogger implements Logger {

    private final Logger logger;

    private static final AtomicInteger taskCounter = new AtomicInteger(0);
    private static String lastMessage = "";
    private final static String tStr = "[TRACE] - ";
    private final static String iStr = "[INFO] - ";
    private final static String dStr = "[DEBUG] - ";
    private final static String wStr = "[WARN] - ";
    private final static String eStr = "[ERROR] - ";

    private static class Singleton {

        private static final JButton taskMonitor = new JButton();
        private static final Singleton instance = new Singleton();
        private static final Icon icon = Images.getIcon("ajax-loader.gif");

        private Singleton() {
            taskMonitor.setHorizontalTextPosition(SwingConstants.LEFT);
            taskMonitor.setText("Scheduled Tasks ... 0");
        }
    }

    /**
     * Logger for the CommonLogger
     */
    private static final CommonLogger commonLoggerLogger = new CommonLogger(CommonLogger.class);

    /**
     * Create a org.slf4j.Logger instance for the requested class
     *
     * @param clzz the class name as a String.
     */
    public CommonLogger(String clzz) {
        this.logger = LoggerFactory.getLogger(clzz);
        update();
    }

    public CommonLogger(Class clzz) {
        this.logger = LoggerFactory.getLogger(clzz.getName());
        update();
    }

    /**
     * @return the taskMonitor
     */
    public static JButton getTaskMonitor() {
        return Singleton.taskMonitor;
    }

    public static void incTaskCount() {
        synchronized (Singleton.instance) {
            taskCounter.incrementAndGet();
            update();
        }
    }

    public static void decTaskCount() {
        synchronized (Singleton.instance) {
            int k = taskCounter.decrementAndGet();
            if (k < 0) {
                taskCounter.getAndSet(0);
            }
            update();
            if (k < 0) {
                commonLoggerLogger.warn("Attempt to set task counter to <0 ignored");
            }
        }
    }

    public static int getTaskCount() {
        synchronized (Singleton.instance) {
            return taskCounter.get();
        }
    }

    private static void update() {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                getTaskMonitor().setText(String.format("Scheduled Tasks ... %d", taskCounter.get()));
                if (taskCounter.get() <= 0) {
                    getTaskMonitor().setIcon(null);
                } else {
                    getTaskMonitor().setIcon(Singleton.icon);
                }
            }

        });
    }

    // Logger methods
    @Override
    public String getName() {
        return "CommonLogger [".concat(logger.getName()).concat("]");
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String string) {
        lastMessage = tStr.concat(string);
        logger.trace(string);
    }

    @Override
    public void trace(String string, Object o) {
        lastMessage = tStr.concat(string);
        logger.trace(string, o);
    }

    @Override
    public void trace(String string, Object o, Object o1) {
        lastMessage = tStr.concat(string);
        logger.trace(string, o, o1);
    }

    @Override
    public void trace(String string, Object... os) {
        lastMessage = tStr.concat(string);
        logger.trace(string, os);
    }

    @Override
    public void trace(String string, Throwable thrwbl) {
        lastMessage = tStr.concat(string);
        logger.trace(string, thrwbl);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String string) {
        lastMessage = tStr.concat(string);
        logger.trace(marker, string);
    }

    @Override
    public void trace(Marker marker, String string, Object o) {
        lastMessage = tStr.concat(string);
        logger.trace(marker, string, o);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1) {
        lastMessage = tStr.concat(string);
        logger.trace(marker, string, o, o1);
    }

    @Override
    public void trace(Marker marker, String string, Object... os) {
        lastMessage = tStr.concat(string);
        logger.trace(marker, string, os);
    }

    @Override
    public void trace(Marker marker, String string, Throwable thrwbl) {
        lastMessage = tStr.concat(string);
        logger.trace(marker, string, thrwbl);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String string) {
        lastMessage = dStr.concat(string);
        logger.debug(string);
    }

    @Override
    public void debug(String string, Object o) {
        lastMessage = dStr.concat(string);
        logger.debug(string, o);
    }

    @Override
    public void debug(String string, Object o, Object o1) {
        lastMessage = dStr.concat(string);
        logger.debug(string, o, o1);
    }

    @Override
    public void debug(String string, Object... os) {
        lastMessage = dStr.concat(string);
        logger.debug(string, os);
    }

    @Override
    public void debug(String string, Throwable thrwbl) {
        lastMessage = dStr.concat(string);
        logger.debug(string, thrwbl);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String string) {
        lastMessage = dStr.concat(string);
        logger.debug(marker, string);
    }

    @Override
    public void debug(Marker marker, String string, Object o) {
        lastMessage = dStr.concat(string);
        logger.debug(marker, string, o);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1) {
        lastMessage = dStr.concat(string);
        logger.debug(marker, string, o, o1);
    }

    @Override
    public void debug(Marker marker, String string, Object... os) {
        lastMessage = dStr.concat(string);
        logger.debug(marker, string, os);
    }

    @Override
    public void debug(Marker marker, String string, Throwable thrwbl) {
        lastMessage = dStr.concat(string);
        logger.debug(marker, string, thrwbl);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String string) {
        lastMessage = iStr.concat(string);
        logger.info(string);
    }

    @Override
    public void info(String string, Object o) {
        lastMessage = iStr.concat(string);
        logger.info(string, o);
    }

    @Override
    public void info(String string, Object o, Object o1) {
        lastMessage = iStr.concat(string);
        logger.info(string, o, o1);
    }

    @Override
    public void info(String string, Object... os) {
        lastMessage = iStr.concat(string);
        logger.info(string, os);
    }

    @Override
    public void info(String string, Throwable thrwbl) {
        lastMessage = iStr.concat(string);
        logger.info(string, thrwbl);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void info(Marker marker, String string) {
        lastMessage = iStr.concat(string);
        logger.info(marker, string);
    }

    @Override
    public void info(Marker marker, String string, Object o) {
        lastMessage = iStr.concat(string);
        logger.info(marker, string, o);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1) {
        lastMessage = iStr.concat(string);
        logger.info(marker, string, o, o1);
    }

    @Override
    public void info(Marker marker, String string, Object... os) {
        lastMessage = iStr.concat(string);
        logger.info(marker, string, os);
    }

    @Override
    public void info(Marker marker, String string, Throwable thrwbl) {
        lastMessage = iStr.concat(string);
        logger.info(marker, string, thrwbl);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String string) {
        lastMessage = wStr.concat(string);
        logger.warn(string);
    }

    @Override
    public void warn(String string, Object o) {
        lastMessage = wStr.concat(string);
        logger.warn(string, o);
    }

    @Override
    public void warn(String string, Object o, Object o1) {
        lastMessage = wStr.concat(string);
        logger.warn(string, o, o1);
    }

    @Override
    public void warn(String string, Object... os) {
        lastMessage = wStr.concat(string);
        logger.warn(string, os);
    }

    @Override
    public void warn(String string, Throwable thrwbl) {
        lastMessage = wStr.concat(string);
        logger.warn(string, thrwbl);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String string) {
        lastMessage = wStr.concat(string);
        logger.warn(marker, string);
    }

    @Override
    public void warn(Marker marker, String string, Object o) {
        lastMessage = wStr.concat(string);
        logger.warn(marker, string, o);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1) {
        lastMessage = wStr.concat(string);
        logger.warn(marker, string, o, o1);
    }

    @Override
    public void warn(Marker marker, String string, Object... os) {
        lastMessage = wStr.concat(string);
        logger.warn(marker, string, os);
    }

    @Override
    public void warn(Marker marker, String string, Throwable thrwbl) {
        lastMessage = wStr.concat(string);
        logger.warn(marker, string, thrwbl);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String string) {
        lastMessage = eStr.concat(string);
        logger.error(string);
    }

    @Override
    public void error(String string, Object o) {
        lastMessage = eStr.concat(string);
        logger.error(string, o);
    }

    @Override
    public void error(String string, Object o, Object o1) {
        lastMessage = eStr.concat(string);
        logger.error(string, o, o1);
    }

    @Override
    public void error(String string, Object... os) {
        lastMessage = eStr.concat(string);
        logger.error(string, os);
    }

    @Override
    public void error(String string, Throwable thrwbl) {
        lastMessage = eStr.concat(string);
        logger.error(string, thrwbl);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void error(Marker marker, String string) {
        lastMessage = eStr.concat(string);
        logger.error(marker, string);
    }

    @Override
    public void error(Marker marker, String string, Object o) {
        lastMessage = eStr.concat(string);
        logger.error(marker, string, o);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1) {
        lastMessage = eStr.concat(string);
        logger.error(marker, string, o, o1);
    }

    @Override
    public void error(Marker marker, String string, Object... os) {
        lastMessage = eStr.concat(string);
        logger.error(marker, string, os);
    }

    @Override
    public void error(Marker marker, String string, Throwable thrwbl) {
        lastMessage = eStr.concat(string);
        logger.error(marker, string, thrwbl);
    }

    /**
     * Returns the last String sent to the logger.
     *
     * @return a String
     */
    public static String getLastMessage() {
        return lastMessage;
    }
}

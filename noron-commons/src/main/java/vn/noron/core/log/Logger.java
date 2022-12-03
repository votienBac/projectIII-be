package vn.noron.core.log;

import org.slf4j.LoggerFactory;
import vn.noron.core.sercurity.AlertSecurityProvider;

public final class Logger {
    private final org.slf4j.Logger log;
    private static AlertSecurityProvider alertSecurityProvider;

    public static void setSecurityProvider(AlertSecurityProvider securityProvider) {
        alertSecurityProvider = securityProvider;
    }

    public Logger(Class<?> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    public void info(String msg) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo())
            log.info(msg);
    }

    public void info(boolean showLog, String msg) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo() || showLog)
            log.info(msg);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. </p>
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void info(String format, Object arg) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo())
            log.info(format, arg);
    }

    public void info(boolean showLog, String format, Object arg) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo() || showLog)
            log.info(format, arg);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void info(String format, Object arg1, Object arg2) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo())
            log.info(format, arg1, arg2);
    }

    public void info(boolean showLog, String format, Object arg1, Object arg2) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo() || showLog)
            log.info(format, arg1, arg2);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void info(String format, Object... arguments) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo())
            log.info(format, arguments);
    }

    public void info(boolean showLog, String format, Object... arguments) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo() || showLog)
            log.info(format, arguments);
    }

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void info(String msg, Throwable t) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo())
            log.info(msg, t);
    }

    public void info(boolean showLog, String msg, Throwable t) {
        if (alertSecurityProvider != null && alertSecurityProvider.showLogInfo() || showLog)
            log.info(msg, t);
    }


    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    public void error(String msg) {
        log.error(msg);
    }

    /**
     * Log a message at the ERROR level according to the specified format
     * and argument.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level. </p>
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void error(String format, Object arg) {
        log.error(format, arg);
    }

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void error(String format, Object arg1, Object arg2) {
        log.error(format, arg1, arg2);
    }

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void error(String format, Object... arguments) {
        log.error(format, arguments);
    }

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void error(String msg, Throwable t) {
        log.error(msg, t);
    }
}


// General utility functions

// AppLog.log


package ca.yyx.hu.utils;

import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Locale;


public final class AppLog {

    private static final int LOG_LEVEL = Log.DEBUG;

    public static final String TAG = "CAR.HU.J";
    public static final boolean LOG_VERBOSE = LOG_LEVEL <= Log.VERBOSE;


    public static void logd(String msg) {
        log(Log.DEBUG, format(msg));
    }

    public static void logd(final String msg, final Object... params) {
        log(Log.DEBUG, format(msg, params));
    }

    public static void loge(String msg) {
        Log.e(TAG, format(msg));
    }

    public static void loge(String msg, Throwable tr) {
        Log.e(TAG, format(msg), tr);
    }

    public static void loge(Throwable tr) {
        Log.e(TAG, tr.getMessage(), tr);
    }

    public static void loge(String msg, final Object... params) {
        Log.e(TAG, format(msg, params));
    }

    public static void logv(String msg, final Object... params) {
        log(Log.VERBOSE, format(msg, params));
    }

    private static void log(int priority, String msg)
    {
        if (priority >= LOG_LEVEL)
        {
            Log.println(priority, TAG, msg);
        }
    }

    private static String format(final String msg, final Object... array) {
        String formatted;
        if (array == null || array.length == 0) {
            formatted = msg;
        } else {
            try {
                formatted = String.format(Locale.US, msg, array);
            } catch (IllegalFormatException ex) {
                loge("IllegalFormatException: formatString='%s' numArgs=%d", msg, array.length);
                formatted = msg + " (An error occurred while formatting the message.)";
            }
        }
        final StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String string = "<unknown>";
        for (int i = 2; i < stackTrace.length; ++i) {
            final String className = stackTrace[i].getClassName();
            if (!className.equals(AppLog.class.getName())) {
                final String substring = className.substring(1 + className.lastIndexOf(46));
                string = substring.substring(1 + substring.lastIndexOf(36)) + "." + stackTrace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s | %s", Thread.currentThread().getId(), string, formatted);
    }

}

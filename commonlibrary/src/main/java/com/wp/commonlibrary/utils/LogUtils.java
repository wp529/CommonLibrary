package com.wp.commonlibrary.utils;


import android.util.Log;

import com.wp.commonlibrary.BuildConfig;

/**
 * 日志工具类
 */
public final class LogUtils {
    private static boolean debug = BuildConfig.DEBUG;
    private static final String TAG = "WP";

    public static void v(String msg) {
        if (debug) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (debug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }
}

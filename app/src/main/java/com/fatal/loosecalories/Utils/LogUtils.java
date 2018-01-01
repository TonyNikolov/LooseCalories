package com.fatal.loosecalories.Utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility methods used to log application for better debugging.
 */
public class LogUtils {
    private static final int MAX_MSG_LENGTH = 4000;

    public static void log(String tag, String msg) {
        log(Log.INFO, tag, msg);
    }

    public static void log(int type, String tag, String msg) {
//        if (BuildConfig.IS_DEBUG) {
            if (Util.isStringNotNull(msg)) {
                if (msg.length() > MAX_MSG_LENGTH) {
                    Log.println(type, tag, msg.substring(0, MAX_MSG_LENGTH));
                    log(type, tag, msg.substring(MAX_MSG_LENGTH, msg.length()));
                } else {
                    Log.println(type, tag, msg);
                }
            } else {
                Log.println(type, tag, "null");
            }
//        }
    }


}
package com.liaobao.Util;

import android.util.Log;

public class LogUtil {

    public static boolean isShowLog = true;//true:打印log，false:不打印log

    public static void e(String message) {
//        if (!isShowLog) return;
        Log.e("zjp", message);
    }

}

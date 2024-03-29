package com.fftime.ffmob.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by tj on 2019/4/16.
 */
public class HandlerUtils {
    private static final String TAG = "HandlerUtils";
    private static final Handler sUiHandler = new Handler(
            Looper.getMainLooper());

    // Working thread.
    private static final HandlerThread mWorkingThread = new HandlerThread(TAG);
    private static Handler sHandler = null;

    static {
        // Initialize working thread handler
        mWorkingThread.start();
        sHandler = new Handler(mWorkingThread.getLooper());
    }

    /**
     * We do not want some one initialize this interface.
     */
    private HandlerUtils() {

    }

    /**
     * Get ui thread Handler.
     *
     * @return
     */
    public static Handler getUiThreadHandler() {
        return sUiHandler;
    }

    /**
     * Get working thread Handler.
     *
     * @return
     */
    public static Handler getWorkingThreadHandler() {
        return sHandler;
    }
}

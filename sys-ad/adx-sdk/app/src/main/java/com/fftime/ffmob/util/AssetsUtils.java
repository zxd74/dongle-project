package com.fftime.ffmob.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public final class AssetsUtils {

    private static final String TAG= AssetsUtils.class.getSimpleName();

    public static InputStream getResourceAsStream(Context ctx,String path){
        try {
            return ctx.getAssets().open(path);
        } catch (IOException ex) {
            Log.e(TAG,ex.getMessage());
        }
        return null;
    }
}

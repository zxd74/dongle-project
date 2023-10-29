package com.fftime.ffmob.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public final class BitmapUtils {

    public static Bitmap toBitmap(InputStream in) {
        if (in == null) return null;
        return BitmapFactory.decodeStream(in);
    }
}

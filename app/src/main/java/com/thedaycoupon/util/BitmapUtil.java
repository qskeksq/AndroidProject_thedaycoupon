package com.thedaycoupon.util;

import android.graphics.Bitmap;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-12-03.
 */
public class BitmapUtil {

    public static void saveBitmapToFilePNGThread(final Handler handler, final File file, final Bitmap bitmap) {
        new Thread() {
            @Override
            public void run() {
                saveBitmapToFilePNG(file, bitmap);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    public static void saveBitmapToFileJPEGThread(final Handler handler, final File file, final Bitmap bitmap) {
        new Thread() {
            @Override
            public void run() {
                saveBitmapToFileJPEG(file, bitmap);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private static void saveBitmapToFilePNG(File file, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 100, true);
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.close();
        } catch (Exception e) {
            Crashlytics.logException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Crashlytics.logException(e);
                }
            }
        }
    }

    private static void saveBitmapToFileJPEG(File file, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (Exception e) {
            Crashlytics.logException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Crashlytics.logException(e);
                }
            }
        }
    }


}

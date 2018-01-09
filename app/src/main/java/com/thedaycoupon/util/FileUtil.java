package com.thedaycoupon.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017-12-03.
 */

public class FileUtil {


    public static File getFilesDir(Context context) {
        String state = Environment.getExternalStorageState();
        File file = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = context.getExternalFilesDir(null);
        } else {
            file = context.getFilesDir();
        }
        return file;
    }

    public static File getPNGFile(Context context, String name) {
        return new File(FileUtil.getFilesDir(context), name + ".png");
    }

    public static File getJPEGFile(Context context, String name) {
        return new File(FileUtil.getFilesDir(context), name + ".jpeg");
    }
}

package com.thedaycoupon.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017-12-04.
 */

public class RemoteUtil {

    public static MultipartBody.Part prepareFilePart(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return part;
    }

    public static String getMimeType(Context context, File file) {
        return context.getContentResolver().getType(Uri.fromFile(file));
    }


}

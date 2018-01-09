package com.thedaycoupon.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-11-11.
 */

public class NoticeUtil {

    public static void makeToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

}

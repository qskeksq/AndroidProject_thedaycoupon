package com.thedaycoupon.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 2017. 12. 14..
 */
public class DialogUtil {

    public static void showDialog(Context context, String title, final ISimpleDialog iDialogUtil, final int id) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimplePositiveButton(id);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimpleNegativeButton(id);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        iDialogUtil.onSimpleCanceled(id);
                    }
                })
                .show();
    }

    public static void showDialog(Context context, String title, String message, final ISimpleDialog iDialogUtil, final int id) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimplePositiveButton(id);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimpleNegativeButton(id);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        iDialogUtil.onSimpleCanceled(id);
                    }
                })
                .show();
    }

    public static void showDialog(Context context, String message, String positiveStr, String negativeStr, final ISimpleDialog iDialogUtil, final int id) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimplePositiveButton(id);
                    }
                })
                .setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onSimpleNegativeButton(id);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        iDialogUtil.onSimpleCanceled(id);
                    }
                })
                .show();
    }

    public static void showDialog(Context context, String title, final String[] array, final IArrayDialog iDialogUtil, final int id) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iDialogUtil.onItemSelected(id, i, array);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        iDialogUtil.onItemCanceled(id);
                    }
                })
                .show();
    }

    public static void showDialog(final View customView, AlertDialog.Builder builder, String positiveStr, String negativeStr, final ISimpleDialog iSimpleDialog, final int id) {

        builder.setView(customView)
                .setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iSimpleDialog.onSimplePositiveButton(id);
                        // 하나의 뷰를 재활용 할 때 두 개의 부모를 가질 수 없기 때문에 부모뷰를 떼어내준다
                        ((ViewGroup) customView.getParent()).removeView(customView);
                    }
                })
                .setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iSimpleDialog.onSimpleNegativeButton(id);
                        ((ViewGroup) customView.getParent()).removeView(customView);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        iSimpleDialog.onSimpleCanceled(id);
                    }
                })
                .show();
    }

    public static void onBackPressed(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage("작성을 취소하시겠습니까?")
                .setPositiveButton("계속 작성하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("뒤로가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .show();
    }

    public interface ISimpleDialog {
        void onSimplePositiveButton(int id);

        void onSimpleNegativeButton(int id);

        void onSimpleCanceled(int id);
    }

    public interface ICustomDialog {
        void onCustomPositiveButton(View customView, View v);

        void onCustomNegativeButton(View customView, View v);
    }

    public interface IArrayDialog {
        void onItemSelected(int id, int which, String[] array);

        void onItemCanceled(int id);
    }

}

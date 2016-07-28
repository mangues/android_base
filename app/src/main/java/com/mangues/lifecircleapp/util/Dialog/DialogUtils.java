package com.mangues.lifecircleapp.util.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.mangues.lifecircleapp.R;

import java.util.List;

/**
 * Created by boredream on 15/10/25.
 */
public class DialogUtils {

    public static ProgressDialog createProgressDialog(Context context) {
        return createProgressDialog(context, null, context.getString(R.string.loading));
    }

    public static ProgressDialog createProgressDialog(Context context, String title, String msg) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 提示信息dialog
     *
     * @param context
     * @param title   标题名称,内容为空时即不设置标题
     * @param msg     提示信息内容
     * @return
     */
    public static AlertDialog showMsgDialog(Context context, String title,
                                            String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        AlertDialog dialog = builder.setMessage(msg)
                .setNegativeButton("确定", null).show();
        return dialog;
    }

    /**
     * 确认dialog
     *
     * @param context
     * @param title           标题名称,内容为空时即不设置标题
     * @param msg             确认信息内容
     * @param onClickListener 确定按钮监听
     * @return
     */
    public static AlertDialog showConfirmDialog(Context context, String title,
                                                String msg, DialogInterface.OnClickListener onClickListener) {
        return showConfirmDialog(context,title,msg,"确认","取消",onClickListener);
    }

    public static AlertDialog showConfirmDialog(Context context, String title,
                                                String msg, String ok, String cancel, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        AlertDialog dialog = builder.setMessage(msg)
                .setPositiveButton(ok, onClickListener)
                .setNegativeButton(cancel, null).show();
        return dialog;
    }
    /**
     * 列表型dialog
     *
     * @param context
     * @param title           标题名称,内容为空时即不设置标题
     * @param items           所有item选项的名称
     * @param onClickListener 确定按钮监听
     * @return
     */
    public static AlertDialog showListDialog(Context context, String title,
                                             List<String> items, DialogInterface.OnClickListener onClickListener) {
        return showListDialog(context, title,
                items.toArray(new String[items.size()]), onClickListener);
    }

    /**
     * 列表型dialog
     *
     * @param context
     * @param title           标题名称,内容为空时即不设置标题
     * @param items           所有item选项的名称
     * @param onClickListener 确定按钮监听
     * @return
     */
    public static AlertDialog showListDialog(Context context, String title,
                                             String[] items, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        AlertDialog dialog = builder
                .setItems(items, onClickListener)
                .setNegativeButton("取消", null).show();
        return dialog;
    }

    /**
     * 单选框dialog
     *
     * @param context
     * @param title            标题名称,内容为空时即不设置标题
     * @param items            所有item选项的名称
     * @param defaultItemIndex 默认选项
     * @param onClickListener  确定按钮监听
     * @return
     */
    public static AlertDialog showSingleChoiceDialog(Context context,
                                                     String title, String[] items, int defaultItemIndex,
                                                     DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        AlertDialog dialog = builder
                .setSingleChoiceItems(items, defaultItemIndex, onClickListener)
                .setNegativeButton("取消", null).show();
        return dialog;
    }

    /**
     * 复选框对话框
     *
     * @param context
     * @param title                      标题名称,内容为空时即不设置标题
     * @param items                      所有item选项的名称
     * @param defaultCheckedItems        初始化选择,和items同长度,true代表对应位置选中,如{true, true,
     *                                   false}代表第一二项选中,第三项不选中
     * @param onMultiChoiceClickListener 多选监听
     * @param onClickListener            确定按钮监听
     * @return
     */
    public static AlertDialog showMultiChoiceDialog(Context context,
                                                    String title, String[] items, boolean[] defaultCheckedItems,
                                                    DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener,
                                                    DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        AlertDialog dialog = builder
                .setMultiChoiceItems(items, defaultCheckedItems,
                        onMultiChoiceClickListener)
                .setPositiveButton("确定", null).setNegativeButton("取消", null)
                .show();
        return dialog;
    }


    public static AlertDialog showSingleInputDialog(Context context,
                                                    String title, String msg, String data, int inputType,
                                                    final OnSingleInputDialogClickListener onClickListener) {
        View view = View.inflate(context, R.layout.dialog_input, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input);
        editText.setText(data);
        editText.setInputType(inputType);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setView(view)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickListener.onClick(dialog, which, editText.getText().toString());
            }
        });
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        AlertDialog dialog = builder.show();
        return dialog;
    }

    public static AlertDialog showSingleInputDialog(Context context,
                                                    String title, String msg, String data,
                                                    final OnSingleInputDialogClickListener onClickListener) {
        int inputType = InputType.TYPE_CLASS_TEXT;
        return showSingleInputDialog(context, title, msg, data, inputType, onClickListener);
    }

    public static AlertDialog showSinglePasswordInputDialog(Context context,
                                                            String title, String msg, String data,
                                                            final OnSingleInputDialogClickListener onClickListener) {
        int inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        return showSingleInputDialog(context, title, msg, data, inputType, onClickListener);
    }
}

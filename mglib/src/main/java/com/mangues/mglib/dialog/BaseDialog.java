package com.mangues.mglib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mangues.mglib.R;

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context, R.style.dialog_tip);
    }

    public BaseDialog(Context context, int theme) {
        super(context, R.style.dialog_tip);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }
}

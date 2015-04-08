package com.hl5pma.rxexample;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    public static void hideSoftKeyboard(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}

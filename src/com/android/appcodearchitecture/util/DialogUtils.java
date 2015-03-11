package com.android.appcodearchitecture.util;

import com.android.appcodearchitecture.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * 改变系统默认对话框线条颜色
 * @author wanghao
 * @since 2015-3-10 上午11:58:12
 */
public class DialogUtils {

	private static void dialogTitleLineColor(Context context, Dialog dialog, int color) {
        String dividers[] = {
                "android:id/titleDividerTop", "android:id/titleDivider"
        };

        for (int i = 0; i < dividers.length; ++i) {
            int divierId = context.getResources().getIdentifier(dividers[i], null, null);
            View divider = dialog.findViewById(divierId);
            if (divider != null) {
                divider.setBackgroundColor(color);
            }
        }
    }

    public static void dialogTitleLineColor(Context context, Dialog dialog) {
        if (dialog != null) {
            dialogTitleLineColor(context, dialog, context.getResources().getColor(R.color.green));
        }
    }
}

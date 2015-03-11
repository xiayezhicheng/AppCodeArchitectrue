package com.android.appcodearchitecture.activity;

import com.android.appcodearchitecture.R;
import com.android.appcodearchitecture.view.PopupWindowView;

import android.widget.PopupWindow;

public class BaseFragmentActivity extends UmengFragmentActivity{

	/**
     * 载入动画
     */
    private PopupWindowView.LoadingPopupWindow mDialogProgressPopWindow = null;

    private void initDialogLoading() {
        if (mDialogProgressPopWindow == null) {
            PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    hideProgressDialog();
                }
            };

            mDialogProgressPopWindow = PopupWindowView.initProgressDialog(this, onDismissListener);
        }
    }

    public void showDialogLoading(String title) {
        initDialogLoading();
        PopupWindowView.showProgressDialog(this, mDialogProgressPopWindow, title);
    }

    public void showDialogLoading() {
        showDialogLoading(getResources().getString(R.string.loading_message));
    }

    public void hideProgressDialog() {
        if (mDialogProgressPopWindow != null) {
        	PopupWindowView.hideDialog(mDialogProgressPopWindow);
        }
    }
}

package com.android.appcodearchitecture.view;

import com.android.appcodearchitecture.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class PopupWindowView {

	 private static final String TAG = PopupWindowView.class.getSimpleName();

	 private static AnimationDrawable loadingLogoAnimation;
	 
	    /**
	     * 初始化进度条dialog
	     *
	     * @param activity
	     * @return
	     */
	    public static LoadingPopupWindow initProgressDialog(Activity activity, OnDismissListener onDismissListener) {
	        if (activity == null || activity.isFinishing()) {
	            return null;
	        }

	        // 获得背景（6个图片形成的动画）
	        //AnimationDrawable animDance = (AnimationDrawable) imgDance.getBackground();

	        //final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
	        final LoadingPopupWindow popupWindow = new LoadingPopupWindow(activity);
	        ColorDrawable cd = new ColorDrawable(-0000);
	        popupWindow.setBackgroundDrawable(cd);
	        popupWindow.setTouchable(true);
	        popupWindow.setOnDismissListener(onDismissListener);

	        popupWindow.setFocusable(true);
	        //animDance.start();
	        return popupWindow;
	    }

	    /**
	     * 显示进度条对话框
	     *
	     * @param activity
	     * @param popupWindow
	     * @param title
	     */
	    public static void showProgressDialog(Activity activity, LoadingPopupWindow popupWindow, String title) {
	        if ((activity == null || activity.isFinishing()) || (popupWindow == null)) {
	            return;
	        }

	        final LoadingPopupWindow tmpPopupWindow = popupWindow;
	        View popupView = popupWindow.getContentView();
	        if (popupView != null) {
	            TextView tvTitlename = (TextView) popupView.findViewById(R.id.loading_title);
	            if (tvTitlename != null && !title.isEmpty()) {
	                tvTitlename.setText(title);
	            }
	        }

	        if (popupWindow != null && !popupWindow.isShowing()) {
	            final View rootView1 = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
	            rootView1.post(new Runnable() {

	                @Override
	                public void run() {
	                    try {
	                        tmpPopupWindow.showAtLocation(rootView1, Gravity.CENTER, 0, 0);
	                        tmpPopupWindow.startAnimation();
	                    } catch (Exception e) {
	                       e.printStackTrace();
	                    }
	                }
	            });

	        }
	    }

	    /**
	     * 隐藏对话框
	     *
	     * @param popupWindow
	     */
	    public static void hideDialog(final PopupWindow popupWindow) {
	        if (popupWindow != null) {
	            popupWindow.getContentView().post(new Runnable() {
	                @Override
	                public void run() {
	                    if (popupWindow != null && popupWindow.isShowing())
	                        try {
	                            popupWindow.dismiss();
	                        } catch (Exception e) {
	                        }
	                }
	            });
	        }
	    }

	    /**
	     * 立即关闭对话框， 在对话框是用来确认是否关闭某个Activity的时候上面的方法有概率会报错
	     *
	     * @param popupWindow
	     */
	    public static void hideDialogNow(PopupWindow popupWindow) {
	        if (popupWindow != null) {
	            popupWindow.dismiss();
	        }
	    }

	    public static class LoadingPopupWindow extends PopupWindow {

	        ImageView loadingLogo;

	        public LoadingPopupWindow(Activity activity) {
	            super(activity.getLayoutInflater().inflate(R.layout.view_loading, null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
	            this.loadingLogo = (ImageView) getContentView().findViewById(R.id.loading_logo);

	            if (loadingLogoAnimation == null) {
	                loadingLogoAnimation = (AnimationDrawable)loadingLogo.getDrawable();
	            }
	        }

	        public void startAnimation() {
	        	loadingLogoAnimation.start();
	        }
	        
	        public void stopAnimation() {
	        	loadingLogoAnimation.stop();
	        }
	    }
}

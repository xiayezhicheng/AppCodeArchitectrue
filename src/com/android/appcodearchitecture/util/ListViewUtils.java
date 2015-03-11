package com.android.appcodearchitecture.util;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.ListView;

public class ListViewUtils {
	
	 private ListViewUtils() {

	    }

	    /**
	     * @param listView
	     */
	    public static void smoothScrollListViewToTop(final ListView listView) {
	        if (listView == null) {
	            return;
	        }
	        smoothScrollListView(listView, 0);
	        listView.postDelayed(new Runnable() {

	            @Override
	            public void run() {
	                listView.setSelection(0);
	            }
	        }, 200);
	    }

	    /**
	     * @param listView
	     * @param position
	     * @param offset
	     * @param duration
	     */
		@SuppressLint("NewApi")
		public static void smoothScrollListView(ListView listView, int position) {
	        if (VERSION.SDK_INT > 11) {
	            listView.smoothScrollToPositionFromTop(0, 0);
	        } else {
	            listView.setSelection(position);
	        }
	    }
}

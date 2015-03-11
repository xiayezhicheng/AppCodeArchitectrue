package com.android.appcodearchitecture.thirdparty.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;


public class PtrCartoonFrameLayout extends PtrFrameLayout {

	private PtrCartoonHeader mHeader;

	public PtrCartoonFrameLayout(Context context) {
		super(context);
		initViews();
	}

	public PtrCartoonFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	public PtrCartoonFrameLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	private void initViews() {
		// mHeader = new PtrClassicDefaultHeader(getContext());
		mHeader = new PtrCartoonHeader(getContext());
		setHeaderView(mHeader);
		addPtrUIHandler(mHeader);
	}

	public PtrCartoonHeader getHeader() {
		return mHeader;
	}

	/**
	 * Specify the last update time by this key string
	 * 
	 * @param key
	 */
	public void setLastUpdateTimeKey(String key) {
		if (mHeader != null) {
			mHeader.setLastUpdateTimeKey(key);
		}
	}

	/**
	 * Using an object to specify the last update time.
	 * 
	 * @param object
	 */
	public void setLastUpdateTimeRelateObject(Object object) {
		if (mHeader != null) {
			mHeader.setLastUpdateTimeRelateObject(object);
		}
	}

}

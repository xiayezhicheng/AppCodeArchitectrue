package com.android.appcodearchitecture.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具
 * @author wanghao
 * @since 2015-1-27 下午5:58:55
 */
public class ToastUtils {

	public static void showShort(Context context, int resourceId){
		Toast.makeText(context, context.getResources().getString(resourceId), Toast.LENGTH_SHORT).show();
	}
	
	public static void showLong(Context context, int resourceId){
		Toast.makeText(context, context.getResources().getString(resourceId), Toast.LENGTH_LONG).show();
	}
	
	public static void showStringShort(Context context, String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
	
	public static void showStringLong(Context context, String content){
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();
	}
	
	

}

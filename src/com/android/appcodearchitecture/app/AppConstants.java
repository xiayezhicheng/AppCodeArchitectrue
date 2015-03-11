package com.android.appcodearchitecture.app;

import java.io.File;

import android.os.Environment;

public class AppConstants {

	/**
	 * 存放崩溃Log的目录
	 */
	public static String CRASH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +  File.separator 
			+ CustomApplcation.getContext().getPackageName()+  File.separator +"crash" + File.separator;
	
}

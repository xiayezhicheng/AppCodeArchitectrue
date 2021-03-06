package com.android.appcodearchitecture.app;

import android.app.Application;
import android.content.Context;

import com.android.appcodearchitecture.util.CrashUtils;
import com.android.appcodearchitecture.util.SharedPreferencesUtils;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 自定义的Application，配置了UIL
 * 
 * @author wanghao
 * @since 2015-1-27 上午11:01:36
 */
public class CustomApplcation extends Application {
	public static CustomApplcation mInstance;
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Stetho.initialize(
			      Stetho.newInitializerBuilder(this)
			        .enableDumpapp(
			            Stetho.defaultDumperPluginsProvider(this))
			        .enableWebKitInspector(
			            Stetho.defaultInspectorModulesProvider(this))
			        .build());
		
		mInstance = this;
		context = getApplicationContext();
		//崩溃记录
		CrashUtils crashUtils = CrashUtils.getInstance();
		crashUtils.init(context);
        //初始化SharedPreferences
        SharedPreferencesUtils.getInstance().Builder(context);
		// Occasional EOFException when reading cached file
		// 1. Try to load lots of previously cached images in a ListView by
		// quickly scrolling back and forth
		// 2. Some images will fail to load
		System.setProperty("http.keepAlive", "false");
		initImageLoader(context);
	}

	/**
	 * 初始化UIL
	 * 
	 * @author wanghao
	 * @since 2014-11-17 下午1:37:16
	 * @param context
	 * @return void
	 */
	private void initImageLoader(Context context) {

		// 磁盘缓存：如配置了diskCacheSize和diskCacheFileCount，就使用的是LruDiscCache，否则使用的是UnlimitedDiscCache
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(10 * 1024 * 1024))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 20 * 1000))
//				.writeDebugLogs()// 发布版本时删除
				.build();
		// 用configuration初始化ImageLoader
		ImageLoader.getInstance().init(configuration);

	}

	/**
	 * Application单例
	 * @author wanghao
	 * @since 2015-1-27 上午11:06:15
	 * @return
	 * @return CustomApplcation
	 */
	public static CustomApplcation getInstance() {
		return mInstance;
	}

	/**
	 * 应用的上下文
	 * @author wanghao
	 * @since 2015-1-27 下午4:11:08
	 * @return
	 * @return Context
	 */
	public static Context getContext() {
		return context;
	}
	
}

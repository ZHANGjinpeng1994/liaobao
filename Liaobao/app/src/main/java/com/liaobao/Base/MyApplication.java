package com.liaobao.Base;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.liaobao.Util.Bomb.IM.DemoMessageHandler;
import com.liaobao.Util.Bomb.MyBmobconfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import cn.bmob.newim.BmobIM;


/**
 * <br>author:jinpneg</br>
 * <br>Time：2017/7/3 21:37</br>
 */
public class MyApplication extends Application {
	// 建立请求队列
	public static RequestQueue queue;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());        //初始化图片加载器相关配置
		// 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
		BGASwipeBackManager.getInstance().init(this);
		//Bmob初始化
		MyBmobconfig.getInstance(this);
		//只有主进程运行的时候才需要初始化
		if (getApplicationInfo().packageName.equals(getMyProcessName())){
			//im初始化
			BmobIM.init(this);
			//注册消息接收器
			BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
		}
		queue = Volley.newRequestQueue(getApplicationContext());
	}
	public static RequestQueue getRequestQueue() {
		return queue;
	}
	
	public  void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.discCacheFileCount(60)// 缓存文件的最大个数
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
				.build();
		ImageLoader.getInstance().init(config);
	}
	/**
	 * 获取当前运行的进程名
	 * @return
	 */
	public static String getMyProcessName() {
		try {
			File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
			BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
			String processName = mBufferedReader.readLine().trim();
			mBufferedReader.close();
			return processName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

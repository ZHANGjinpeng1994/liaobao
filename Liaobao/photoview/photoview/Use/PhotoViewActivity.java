package com.liaobao.photoview.Use;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.liaobao.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private TextView mTvSaveImage;
    private List<String> Urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader(this);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
//        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
//        mTvSaveImage = (TextView) findViewById(R.id.tv_save_image_photo);
//        mTvSaveImage.setOnClickListener(this);
    }
    private void initData() {

//        Intent intent = getIntent();
//        currentPosition = intent.getIntExtra("currentPosition", 0);

        Urls=new ArrayList<>();
        Urls.add("http://img.mukewang.com/55237dcc0001128c06000338.jpg");
        Urls.add("http://img.mukewang.com/550b86560001009406000338.jpg");
        Urls.add("http://img.mukewang.com/55249cf30001ae8a06000338.jpg");
        Urls.add("http://file.bmob.cn/M00/D8/8F/oYYBAFSFh_WAB_uIAAB4PTlS6rI0966668");
        Urls.add("http://file.bmob.cn/M00/D7/56/oYYBAFSBhJSAIdIJAACoNxTDcM44444974");
        Urls.add("http://file.bmob.cn/M00/D9/45/oYYBAFSGZvKAdpqWAACKHHYyClU0459660");
        Urls.add("http://file.bmob.cn/M00/D9/45/oYYBAFSGZreAEyRSAADfcLFM62U5428980");
        Urls.add("http://file.bmob.cn/M00/D9/44/oYYBAFSGZlyAXZjwAAC5ynS9Zww4985471");
        Urls.add("http://file.bmob.cn/M00/D9/44/oYYBAFSGZYqAUYxAAADSNZMdbOs5036914");
        Urls.add("http://file.bmob.cn/M00/D9/43/oYYBAFSGZRCATj2XAADDaZB1Nbo6433800");
        Urls.add("http://file.bmob.cn/M00/D9/41/oYYBAFSGZJKAWXyDAADPOI8iH6Q3523485");
        Urls.add("http://file.bmob.cn/M00/D8/8F/oYYBAFSFiE6AbAu2AACfguCRGHY3486277");
        Urls.add("http://file.bmob.cn/M00/D7/57/oYYBAFSBhd-Aa1IiAAC86WAfmc89917549");
        Urls.add("http://file.bmob.cn/M00/D7/55/oYYBAFSBgiKAC2aGAAC4We1hZmk1405980");
        Urls.add("http://file.bmob.cn/M00/D7/56/oYYBAFSBgrmAXQ8BAAD665llCTc5545260");
        Urls.add("http://file.bmob.cn/M00/D7/56/oYYBAFSBg-CAePQKAAC38mflwMY8903422");
        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0, false);
//        mTvImageCount.setText(currentPosition+1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
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
}

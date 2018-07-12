package com.liaobao.photoview.Use;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liaobao.Base.AppBaseActivity;
import com.liaobao.R;
import com.liaobao.Util.SystembarTools;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends Activity implements View.OnClickListener {

    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private MyImageAdapter adapter;
    private List<String> Urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystembarTools.setLopStatBar(this,R.color.transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
    }

    private void initData() {
        Intent intent = getIntent();
        String[] url = intent.getStringArrayExtra("urls");
        Urls = new ArrayList<>();
        if (url != null) {
            for (int i = 0; i < url.length; i++) {
                Urls.add(url[i]);
            }
        }
        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0, false);
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
}

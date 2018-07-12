package com.liaobao.UI.lazyfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.liaobao.R;
import com.liaobao.Util.SystembarTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiaotianActivity extends AppCompatActivity {
    static final int VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private List<InfoEntity> infoEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        infoEntities.add(new InfoEntity(getResources().getString(R.string.laoyao), "0"));
        infoEntities.add(new InfoEntity(getResources().getString(R.string.laowang), "1"));
        mSectionsPagerAdapter.init(infoEntities,this);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // 若设置了该属性 则viewpager会缓存指定数量的Fragment
        mViewPager.setOffscreenPageLimit(VIEWPAGER_OFF_SCREEN_PAGE_LIMIT);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

}

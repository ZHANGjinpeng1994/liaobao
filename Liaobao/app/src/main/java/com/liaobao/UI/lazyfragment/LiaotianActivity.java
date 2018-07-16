package com.liaobao.UI.lazyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.liaobao.R;
import com.liaobao.UI.MainActivity;
import com.liaobao.UI.Wode.denglu_Activity;
import com.liaobao.Util.Bomb.IM.User;
import com.liaobao.Util.LogUtil;
import com.liaobao.Util.StringUtil;
import com.liaobao.Util.SystembarTools;
import com.trycatch.mysnackbar.showTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobUser;

public class LiaotianActivity extends AppCompatActivity {
    static final int VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private List<InfoEntity> infoEntities = new ArrayList<>();
    protected showTools ST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ST=new showTools(this);

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

        final User user = BmobUser.getCurrentUser(User.class);
        if(user!=null&&!StringUtil.getIsNull(user.getObjectId())){
            LogUtil.e(user.getAvatar()+"===user.getObjectId()="+user.getObjectId());
        }else{
            ST.show("请登录您的账号",0);
            startActivity(new Intent(this,denglu_Activity.class));
        }
    }
}

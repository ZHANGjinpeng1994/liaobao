package com.liaobao.UI.Wode;


import android.os.Bundle;
import android.view.View;

import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.view.TitleBarUI;

/**
 * 我的
 * <br> author: JinPeng </br>
 * <br> Time: 2018/7/16 14:13 </br>
 */
public class Wode_Activity extends MYBaseActivity implements  View.OnClickListener{
    private TitleBarUI title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        init();
    }

    private void init() {
        initTitle();
        initview();
        initdata();
        initclick();
    }

    private void initclick() {
    }
    private void initview() {

    }
    private void initdata() {
    }
    private void initTitle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("我的");
        title_bar.setLeftImageResources(R.mipmap.ic_back);
        title_bar.setListener(new TitleBarUI.TitleBarListener() {
            @Override
            public void zuobian() {
                finish();
            }
            @Override
            public void youbian() {
            }
            @Override
            public void zhongjian() {
            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()){
        }
    }


}

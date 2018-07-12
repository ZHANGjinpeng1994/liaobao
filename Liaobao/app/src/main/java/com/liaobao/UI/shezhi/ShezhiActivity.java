package com.liaobao.UI.shezhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.view.TitleBarUI;


public class ShezhiActivity extends MYBaseActivity {

    private TitleBarUI title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        inititle();
        click();
    }
    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }
    private void click() {
        findViewById(R.id.zhuti).setOnClickListener(this);
        findViewById(R.id.liaotianjilu).setOnClickListener(this);
    }

    /**
     * 初始化标题
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/4 21:58</br>
     */
    private void inititle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("系统设置");
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.zhuti:
                startActivity(new Intent(ShezhiActivity.this,ShezhiQipaoActivity.class));
                break;
            case R.id.liaotianjilu:
                startActivity(new Intent(ShezhiActivity.this,LiaotianjiluActivity.class));
                break;
        }
    }
}

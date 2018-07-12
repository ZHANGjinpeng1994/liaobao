package com.liaobao.UI.Guanyu;

import android.os.Bundle;

import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.view.TitleBarUI;

public class GuanyuActivity extends MYBaseActivity {

    private TitleBarUI title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyu);
        inititle();
    }
    /**
     * 初始化标题
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/4 21:58</br>
     */
    private void inititle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("关于");
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
}

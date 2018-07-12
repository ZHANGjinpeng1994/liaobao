package com.liaobao.UI.shezhi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.liaobao.R;
import com.liaobao.Util.LogUtil;
import com.liaobao.view.TitleBarUI;

/**
 * 聊天记录
 * <br>author:jinpneg</br>
 * <br>Time：2017/8/6 16:52</br>
 */
public class LiaotianjiluActivity extends AppCompatActivity {

    private TitleBarUI title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liaotianjilu);
        inititle();
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
    }
    /**
     * 初始化标题
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/4 21:58</br>
     */
    private void inititle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("聊天记录");
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

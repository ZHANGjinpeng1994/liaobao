package com.liaobao.UI.shezhi;

import android.os.Bundle;

import com.liaobao.Base.AppBaseActivity;
import com.liaobao.R;
import com.liaobao.adapter.Qipaoadapter;
import com.liaobao.entity.Qipao;
import com.liaobao.view.TitleBarUI;
import com.liaobao.view.XListView;

import java.util.List;

public class ShezhiQipaoActivity extends AppBaseActivity {

    private TitleBarUI title_bar;
    private XListView listview;
    private Qipaoadapter qipaoadapter;
    private List<Qipao> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi_qipao);
        inititle();
        initview();
        initdata();
    }

    private void initdata() {
//        list=new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new Qipao());
//        }
//        qipaoadapter=new Qipaoadapter(this,list);
//        listview.setAdapter(qipaoadapter);
    }

    private void initview() {
//        listview= (XListView) findViewById(R.id.listview);
//        listview.setPullRefreshEnable(false);
//        listview.setPullLoadEnable(false);
    }

    /**
     * 初始化标题
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/4 21:58</br>
     */
    private void inititle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("气泡设置");
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

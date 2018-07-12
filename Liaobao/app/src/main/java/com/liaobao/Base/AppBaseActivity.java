package com.liaobao.Base;

import android.app.Activity;
import android.app.Service;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liaobao.R;
import com.liaobao.Util.SystemBarTintManager;
import com.liaobao.Util.SystembarTools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trycatch.mysnackbar.showTools;


import net.tsz.afinal.FinalBitmap;

import java.io.File;

/**
 * <br> author: JinPeng </br>
 * <br> Time: 2017/5/10 17:05 </br>
 */
public class AppBaseActivity extends Activity implements OnClickListener {

    public FinalBitmap finalImageLoader;
    public ImageLoader imageLoader;
    protected showTools ST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystembarTools.setLopStatBar(this,R.color.fen_color1);
        super.onCreate(savedInstanceState);

        finalImageLoader = FinalBitmap.create(this);
        finalImageLoader.configDiskCachePath(new File(Environment.getExternalStorageDirectory(), "qrobot/images/cache").getAbsolutePath());
        imageLoader = ImageLoader.getInstance();
        ST=new showTools(this);

    }

    /**
     * 隐藏软键盘
     * hideSoftInputView
     * @param
     * @return void
     * @throws
     * @Title: hideSoftInputView
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 弹出输入法窗口
     */
    public void showSoftInputView(final EditText et) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) et.getContext().getSystemService(Service.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
    }

    /**
     * 如果子类支持点击左上角返回按钮返回，则在子类的onClick方法中需添加super.onClick(View view);
     */
    @Override
    public void onClick(View v) {
    }

}

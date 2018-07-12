package com.trycatch.mysnackbar;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者: Administrator on 2017/7/22 23:42.
 * 邮箱: likaileeopen@163.com
 */
public class showTools {
    private  Activity Context;
    private  TSnackbar snackBar;
    public  showTools(Activity Context){
        this.Context=Context;
    }
    private void newsnackbar(){
        final ViewGroup viewGroup = (ViewGroup) Context.findViewById(android.R.id.content).getRootView();
        snackBar = TSnackbar.make(viewGroup,"",TSnackbar.LENGTH_SHORT, 0);
    }
    /**
     * <br>author:jinpneg</br>
     * <br>Time：2017/7/22 23:46</br>
     * type=0成功 1警告 2异常
     */
    public void show(String text,int type){
        newsnackbar();
        snackBar.setDuration(1000);
        switch (type){
            case 0:
                snackBar.setPromptThemBackground(Prompt.SUCCESS).setText(text).show();
                break;
            case 1:
                snackBar.setPromptThemBackground(Prompt.WARNING).setText(text).show();
                break;
            case 2:
                snackBar.setPromptThemBackground(Prompt.ERROR).setText(text).show();
                break;
        }

    }
    public void show(View view,String text, int type){
        snackBar = TSnackbar.make(view,"",TSnackbar.LENGTH_SHORT, 0);
        snackBar.setDuration(1000);
        switch (type){
            case 0:
                snackBar.setPromptThemBackground(Prompt.SUCCESS).setText(text).show();
                break;
            case 1:
                snackBar.setPromptThemBackground(Prompt.WARNING).setText(text).show();
                break;
            case 2:
                snackBar.setPromptThemBackground(Prompt.ERROR).setText(text).show();
                break;
        }

    }
}

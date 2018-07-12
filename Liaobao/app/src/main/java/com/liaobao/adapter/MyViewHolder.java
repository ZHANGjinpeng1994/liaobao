package com.liaobao.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者: Administrator on 2017/7/1 13:09.
 * 邮箱: likaileeopen@163.com
 */
public class MyViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;
    public MyViewHolder(View itemView) {
        super(itemView);
        mViews= new SparseArray<View>();
    }

    //通过viewId获取控件
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    /**
     * 设置TextView的值
     */
    public MyViewHolder setText(int viewId,String text){
        TextView tv= getView(viewId);
        tv.setText(text);
        return this;
    }
    public MyViewHolder setImageResource(int viewId,int resId){
        ImageView view= getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    public MyViewHolder setImageBitamp(int viewId,Bitmap bitmap){
        ImageView view= getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    public MyViewHolder setImageURI(int viewId,String uri){
        ImageView view= getView(viewId);
       //Imageloader.getInstance().loadImg(view,uri);
        return this;
    }
}

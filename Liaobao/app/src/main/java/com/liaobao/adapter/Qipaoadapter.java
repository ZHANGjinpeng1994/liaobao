package com.liaobao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaobao.Base.BaseListAdapter;
import com.liaobao.Base.BaseRecyclerViewAdapter;
import com.liaobao.R;
import com.liaobao.entity.Qipao;

import java.util.List;

public class Qipaoadapter extends BaseListAdapter<Qipao> {
    private Context context;

    public Qipaoadapter(Context context, List<Qipao> list) {
        super(context, list);
        this.context=context;
    }

//    @Override
//    protected void bindData(MyViewHolder holder, Qipao data, int position) {
//
//    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qipao,null);
        }
        return convertView;
    }
}

package com.liaobao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.liaobao.Base.BaseRecyclerViewAdapter;
import com.liaobao.R;
import com.liaobao.Util.Bomb.IM.Conversation;
import com.liaobao.Util.DateTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ConversationAdapter extends BaseRecyclerViewAdapter<Conversation> {

    public ConversationAdapter(Context context, List<Conversation> datas, int layoutId) {
        super(context, datas, layoutId);
    }
    @Override
    protected void bindData(MyViewHolder holder, Conversation con, int position) {
        holder.setText(R.id.tv_recent_msg,con.getLastMessageContent());
        holder.setText(R.id.tv_recent_time, DateTools.getChatTime(false,con.getLastMessageTime()));
        //会话图标
        Object obj = con.getAvatar();
        if(obj instanceof String){
            String avatar=(String)obj;
//            holder.setImageView(avatar, R.mipmap.head, R.id.iv_recent_avatar);
        }else{
            int defaultRes = (int)obj;
//            holder.setImageView(null, defaultRes, R.id.iv_recent_avatar);
        }
        //会话标题
        holder.setText(R.id.tv_recent_name, con.getcName());
        //查询指定未读消息数
        long unread = con.getUnReadCount();
        TextView tv_recent_unread=holder.getView(R.id.tv_recent_unread);
        if(unread>0){
            tv_recent_unread.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
        }else{
            tv_recent_unread.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(getOnClickListener(position));
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
    }
    /**
     * 获取指定position的Item
     * @param position
     * @return
     */
    public Conversation getItem(int position) {
        int more = getItemCount() - datas.size();
        return datas.get(position - more);
    }
    /**
     * 删除数据
     * @param position
     */
    public void remove(int position) {
        int more = getItemCount() - datas.size();
        datas.remove(position - more);
        notifyDataSetChanged();
    }
    /**
     * 绑定数据
     * @param datas
     * @return
     */
    public ConversationAdapter bindDatas(Collection<Conversation> datas) {
        this.datas = datas == null ? new ArrayList<Conversation>() : new ArrayList<Conversation>(datas);
        notifyDataSetChanged();
        return this;
    }
    protected OnRecyclerViewListener listener;
    /**
     * 设置点击/长按等事件监听器
     * @param onRecyclerViewListener
     */
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.listener = onRecyclerViewListener;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(position);
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (listener != null && v != null) {
                    listener.onItemLongClick(position);
                }
                return true;
            }
        };
    }
}
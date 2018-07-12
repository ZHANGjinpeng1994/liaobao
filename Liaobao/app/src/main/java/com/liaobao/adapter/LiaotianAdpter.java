package com.liaobao.adapter;


import android.content.Context;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaobao.Base.BaseListAdapter;
import com.liaobao.R;
import com.liaobao.Util.DateTools;
import com.liaobao.Util.LogUtil;
import com.liaobao.Util.StringUtil;
import com.liaobao.config.APP;
import com.liaobao.entity.Msg;
import com.liaobao.view.CircleImageView;

import net.tsz.afinal.FinalBitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天适配器
 * <br>author:jinpneg</br>
 * <br>Time：2017/6/4 17:35</br>
 */
public class LiaotianAdpter extends BaseListAdapter<Msg> {
    //文本
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    //图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    //语音
    private final int TYPE_SEND_VOICE = 4;
    private final int TYPE_RECEIVER_VOICE = 5;
    private FinalBitmap finalImageLoader;
    private OnClickMsgListener onClickMsgListener;
    private Animation animation;
    public LiaotianAdpter(Context context, List<Msg> list) {
        super(context, list);
        finalImageLoader = FinalBitmap.create(context);
        animation = AnimationUtils.loadAnimation(context, R.anim.list_anim_xiala);
    }
    public LiaotianAdpter(Context context, List<Msg> list, OnClickMsgListener onClickMsgListener) {
        super(context, list);
        finalImageLoader = FinalBitmap.create(context);
        this.onClickMsgListener=onClickMsgListener;
        animation = AnimationUtils.loadAnimation(context, R.anim.list_anim_xiala);
    }
    @Override
    public int getItemViewType(int position) {
        Msg msg = list.get(position);
        switch (msg.getType()) {
            case APP.MSG_TYPE_TEXT:
                return msg.getIsComing() == 0 ? TYPE_RECEIVER_TXT : TYPE_SEND_TXT;
            case APP.MSG_TYPE_IMG:
                return msg.getIsComing() == 0 ? TYPE_RECEIVER_IMAGE : TYPE_SEND_IMAGE;
            default:
                return -1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 10;
    }

    /**
     * 根据消息类型，使用对应布局
     *
     * @param msg
     * @param position
     * @return
     */
    private View createViewByType(Msg msg, int position) {
        switch (msg.getType()) {
            case APP.MSG_TYPE_TEXT://文本
                return getItemViewType(position) == TYPE_RECEIVER_TXT ? createView(R.layout.item_chat_text_rece) : createView(R.layout.item_chat_text_sent);
            case APP.MSG_TYPE_IMG://图片
                return getItemViewType(position) == TYPE_RECEIVER_IMAGE ? createView(R.layout.item_chat_image_rece) : createView(R.layout.item_chat_image_sent);
            default:
                return null;
        }
    }

    private View createView(int id) {
        return mInflater.inflate(id, null);
    }

    @Override
    public View bindView(final int position, View convertView, ViewGroup parent) {
        final Msg msg = list.get(position);
        if (convertView == null) {
            convertView = createViewByType(msg, position);
        }

        CircleImageView head_view = ViewHolder.get(convertView, R.id.head_view);//头像
        TextView chat_time = ViewHolder.get(convertView, R.id.chat_time);//时间
        TextView tv_text = ViewHolder.get(convertView, R.id.tv_text);//文本
        ImageView iv_image = ViewHolder.get(convertView, R.id.iv_image);//图片

        String msg2str=null;
        if(position>0){
            try{
                Msg msg2=list.get(position-1);
                msg2str=msg2.getDate();
            }catch (Exception e){
            }
        }
        String date=DateTools.weekdate(msg.getDate(),msg2str);
        if(StringUtil.getIsNull(date)){
            chat_time.setVisibility(View.GONE);
        }else{
            chat_time.setVisibility(View.VISIBLE);
            chat_time.setText(date);//时间
        }

        // 如果是第一次加载该view，则使用动画
        if (msg.isanim_start==1) {
            convertView.startAnimation(animation);
            msg.isanim_start=0;
        }
        switch (msg.getType()) {
            case APP.MSG_TYPE_TEXT://文本
//                tv_text.setText(ExpressionUtil.prase(mContext, tv_text, msg.getContent()));
                tv_text.setText(msg.getContent());
                Linkify.addLinks(tv_text, Linkify.ALL);
                tv_text.setOnClickListener(new onClick(position));
                tv_text.setOnLongClickListener(new onLongCilck(position));
                break;
            case APP.MSG_TYPE_IMG://图片
                LogUtil.e("=====dddddddddddddssssss===="+msg.getContent());
                finalImageLoader.display(iv_image, msg.getContent());
                iv_image.setOnClickListener(new onClick(position));
                iv_image.setOnLongClickListener(new onLongCilck(position));
                break;
        }

        return convertView;
    }

    /**
     * 屏蔽listitem的所有事件
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }



    /**
     * 点击监听
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/6 22:47</br>
     */
    class onClick implements View.OnClickListener {
        int position;

        public onClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View arg0) {
            if(onClickMsgListener!=null)
            onClickMsgListener.click(position);
        }

    }

    /**
     * 长按监听
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/6 22:47</br>
     */
    class onLongCilck implements View.OnLongClickListener {
        int position;

        public onLongCilck(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View arg0) {
            if(onClickMsgListener!=null)
            onClickMsgListener.longClick(position);
            return true;
        }
    }

    public interface OnClickMsgListener {
        void click(int position);

        void longClick(int position);
    }
}

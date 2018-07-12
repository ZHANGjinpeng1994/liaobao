package com.liaobao.UI.lazyfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liaobao.R;
import com.liaobao.UI.Xiaohuoban.HuobanActivity;
import com.liaobao.Util.Bomb.IM.Conversation;
import com.liaobao.Util.Bomb.IM.PrivateConversation;
import com.liaobao.Util.LogUtil;
import com.liaobao.adapter.ConversationAdapter;
import com.liaobao.adapter.OnRecyclerViewListener;
import com.liaobao.db.NewFriend;
import com.liaobao.db.NewFriendManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;


public class PlaceHolderFragment extends BaseFragment {
    private static Context context;
    Handler handler = new Handler();
    ProgressBar progressBar;
    InfoEntity info;
    private RecyclerView rc_view;
    private SwipeRefreshLayout sw_refresh;
    private ConversationAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NewFriendManager newFriendManager;


    private static final String ARG_INFO_ENTITY = "arg_info_entity";
    private static final int DELAY_TIME = 1000;

    public PlaceHolderFragment() {
    }

    public static PlaceHolderFragment newInstance(InfoEntity infoEntity,Context cont) {
        context=cont;
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INFO_ENTITY, infoEntity);
        fragment.setArguments(args);
        if (infoEntity != null) {
            fragment.setTitle(infoEntity.getTitle());
        }
        return fragment;
    }

    @Override
    public void initVariables(Bundle bundle) {
        info = bundle.getParcelable(ARG_INFO_ENTITY);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        initview(rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        // 模拟耗时操作 比如网络请求
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isPrepared()) {
                    Log.w("initData", "目标已被回收");
                    return;
                }
                if (!TextUtils.isEmpty(info.getShowNumber())) {
                }
                progressBar.setVisibility(View.GONE);
            }
        }, DELAY_TIME);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {}

    private void initview(View view) {
        rc_view= (RecyclerView) view.findViewById(R.id.rc_view);
        sw_refresh= (SwipeRefreshLayout) view.findViewById(R.id.sw_refresh);
        initdata(context);
        onclick();
    }

    private void initdata(Context context) {
        adapter = new ConversationAdapter(context,null,R.layout.item_conversation);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(context);
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);

        newFriendManager= NewFriendManager.getInstance(context);
    }

    private void onclick() {
        sw_refresh.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sw_refresh.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                adapter.getItem(position).onClick(context);
            }

            @Override
            public boolean onItemLongClick(int position) {
                adapter.getItem(position).onLongClick(context);
                return true;
            }
        });
    }
    /**
     查询本地会话
     */
    public void query(){
//        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.bindDatas(getConversations());
        sw_refresh.setRefreshing(false);
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     * @return
     */
    private List<Conversation> getConversations(){
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        LogUtil.e(item.getConversationTitle()+"=="+item.getConversationId()+"=="+item.getMessages().size()+"==");
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //添加新朋友会话-获取好友请求表中最新一条记录
        if(newFriendManager!=null){
            List<NewFriend> friends = newFriendManager.getAllNewFriend();
            if(friends!=null && friends.size()>0){
                NewFriend nf=friends.get(0);
                LogUtil.e(nf.getUid()+"=msg="+nf.getMsg()+"=="+nf.getId());
//            conversationList.add(new NewFriendConversation(friends.get(0)));
            }
        }
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }
}

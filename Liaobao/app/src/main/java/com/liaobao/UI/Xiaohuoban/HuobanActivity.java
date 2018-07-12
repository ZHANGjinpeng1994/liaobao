package com.liaobao.UI.Xiaohuoban;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.Util.Bomb.IM.Conversation;
import com.liaobao.Util.Bomb.IM.PrivateConversation;
import com.liaobao.Util.LogUtil;
import com.liaobao.adapter.ConversationAdapter;
import com.liaobao.adapter.OnRecyclerViewListener;
import com.liaobao.db.NewFriend;
import com.liaobao.db.NewFriendManager;
import com.liaobao.view.TitleBarUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;

/**
 * 小伙伴页面
 * <br>author:jinpneg</br>
 * <br>Time：2017/7/30 16:50</br>
 */
public class HuobanActivity extends MYBaseActivity implements  View.OnClickListener {
    private TitleBarUI title_bar;
    private RecyclerView rc_view;
    private SwipeRefreshLayout sw_refresh;
    private ConversationAdapter adapter;
    private LinearLayoutManager layoutManager;

    private NewFriendManager newFriendManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huoban);
        init();
    }
    private void init() {
        iniTitle();
        initview();
        initdata();
        onclick();
    }
    private void iniTitle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("小伙伴");
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

    private void initview() {
        rc_view= (RecyclerView) findViewById(R.id.rc_view);
        sw_refresh= (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
    }

    private void initdata() {
        adapter = new ConversationAdapter(this,null,R.layout.item_conversation);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);

        newFriendManager=NewFriendManager.getInstance(this);
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
                adapter.getItem(position).onClick(HuobanActivity.this);
            }

            @Override
            public boolean onItemLongClick(int position) {
                adapter.getItem(position).onLongClick(HuobanActivity.this);
//                adapter.remove(position);
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
        LogUtil.e("刷新list==="+list.size());
        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        LogUtil.e(item.getConversationTitle()+"=="+item.getConversationId()+"=="+item.getMessages().size()+"==");
                        for (int i = 0; i <10 ; i++) {
                            conversationList.add(new PrivateConversation(item));
                        }
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

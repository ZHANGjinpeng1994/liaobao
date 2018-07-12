package com.liaobao.UI.lazyfragment;

import android.content.Context;
import android.content.Intent;
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

import com.liaobao.R;
import com.liaobao.UI.Xiaohuoban.ChatActivity;
import com.liaobao.Util.Bomb.IM.Friend;
import com.liaobao.Util.Bomb.IM.User;
import com.liaobao.Util.Bomb.IM.UserModel;
import com.liaobao.adapter.ConversationAdapter;
import com.liaobao.adapter.FriendAdapter;
import com.liaobao.adapter.OnRecyclerViewListener;
import com.liaobao.db.NewFriendManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class LianXiRenFragment extends BaseFragment {
    private static Context context;
    Handler handler = new Handler();
    ProgressBar progressBar;
    InfoEntity info;
    private RecyclerView rc_view;
    private SwipeRefreshLayout sw_refresh;
    private FriendAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NewFriendManager newFriendManager;


    private static final String ARG_INFO_ENTITY = "arg_info_entity";
    private static final int DELAY_TIME = 1000;

    public LianXiRenFragment() {
    }

    public static LianXiRenFragment newInstance(InfoEntity infoEntity, Context cont) {
        context=cont;
        LianXiRenFragment fragment = new LianXiRenFragment();
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
        adapter = new FriendAdapter(getActivity(), null, R.layout.item_lianxiren);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
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
                {
                    Friend friend = adapter.getItem(position);
                    User user = friend.getFriendUser();
                    BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
                    //启动一个会话，实际上就是在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中

                    Intent intent = new Intent();
                    intent.setClass(context, ChatActivity.class);
                    Bundle bundle = new Bundle();
                    if(BmobIM.getInstance()!=null){
                        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, null);
                        bundle.putSerializable("c", c);
                    }
                    if (bundle != null) {
                        intent.putExtra(context.getPackageName(), bundle);
                    }
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(final int position) {
                if (position == 0) {
                    return true;
                }
                UserModel.getInstance().deleteFriend(adapter.getItem(position),
                        new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
//                                    toast("好友删除成功");
//                                    adapter.remove(position);
                                } else {
//                                    toast("好友删除失败：" + e.getErrorCode() + ",s =" + e.getMessage());
                                }
                            }
                        });

                return true;
            }
        });
    }
    /**
     * 查询本地会话
     */
    public void query() {
        UserModel.getInstance().queryFriends(
                new FindListener<Friend>() {
                    @Override
                    public void done(List<Friend> list, BmobException e) {
                        if (e == null) {
                            List<Friend> friends = new ArrayList<Friend>();
                            friends.clear();
                            //添加首字母
                            for (int i = 0; i < list.size(); i++) {
                                Friend friend = list.get(i);
                                String username = friend.getFriendUser().getUsername();
//                                String pinyin = Pinyin.toPinyin(username.charAt(0));
//                                friend.setPinyin(pinyin.substring(0, 1).toUpperCase());
                                friends.add(friend);
                            }
                            adapter.bindDatas(friends);
                            adapter.notifyDataSetChanged();
                            sw_refresh.setRefreshing(false);
                        } else {

                            adapter.bindDatas(null);
                            adapter.notifyDataSetChanged();
                            sw_refresh.setRefreshing(false);
                        }
                    }
                }
        );
    }
}

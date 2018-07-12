package com.liaobao.UI.Wode;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.Util.Bomb.IM.User;
import com.liaobao.Util.Bomb.MyBmobconfig;
import com.liaobao.Util.LogUtil;
import com.liaobao.Util.StringUtil;
import com.liaobao.view.TitleBarUI;

import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by zhangjinpeng on 2017/1/4.
 */
public class denglu_Activity extends MYBaseActivity implements  View.OnClickListener{
    private CheckBox jizhu_CB;
    private EditText passEditView;
    private EditText usernameEditView;
    private TextView zhuceTextView;
    private TitleBarUI title_bar;
    private MyBmobconfig myBmobconfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        myBmobconfig=MyBmobconfig.getInstance(this);
        init();
    }

    private void init() {
        initTitle();
        initview();
        initdata();
        initclick();
    }

    private void initclick() {
        findViewById(R.id.denglu_TV).setOnClickListener(this);
        zhuceTextView.setOnClickListener(this);
        passEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!isShoudongXiugai)
                jizhu_CB.setChecked(s.length()>0);
            }
        });
        jizhu_CB.setOnClickListener(this);
    }
    private void initview() {
        passEditView= (EditText) findViewById(R.id.passEditView);
        usernameEditView= (EditText) findViewById(R.id.usernameEditView);
        zhuceTextView= (TextView) findViewById(R.id.zhuceTextView);
        zhuceTextView.setText("注册账号");
        jizhu_CB= (CheckBox) findViewById(R.id.jizhu_CB);

    }
    private void initdata() {
    }
    private void initTitle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("登录");
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
    boolean isShoudongXiugai=false;
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.denglu_TV:
                String username=usernameEditView.getText().toString().trim();
                String password=passEditView.getText().toString().trim();
                denglu_JS(username,password);
                break;
            case R.id.zhuceTextView:
                zhijiepeizhi();
                break;
            case R.id.jizhu_CB:
                isShoudongXiugai=true;
                break;
        }
    }

    private void zhijiepeizhi() {
        Intent intent=new Intent();
        intent.setClass(this,Zhuce_Activity.class);
        startActivity(intent);
    }

    private void denglu_JS(final String username, final String password){
        if(StringUtil.getIsNull(username)){
            ST.show("用户名不能为空",1);
            return;
        }
        if(!StringUtil.issPhoneNumberNO(username)){
            ST.show("请输入正确的手机号",1);
            return;
        }
        if(StringUtil.getIsNull(password)){
            ST.show("密码不能为空",1);
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ST.show("登录成功",0);
                    bombim();
                    zhuceTextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1000);
                } else {
                    ST.show("登录失败",1);

                }
            }
        });
//        Map<String,String> MAP=new HashMap();
//        MAP.put("name",username);
//        MAP.put("password",password);
//        VolleyRequestUtil.RequestPost(this, APP.Bomb_URL+APP.Denglu, "",
//                MAP,new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener){
//                    @Override
//                    public void onMySuccess(String result) {
//                        LogUtil.e("Success="+result);
//                        try {
//                            JSONObject J=new JSONObject(result);
//                            int code=J.getInt("code");
//                            String msg= J.getString("msg");
//                            if(code==0){
//                                String datas=J.getString("data");
//                                JSONObject data= new JSONObject(datas);
//                                String objectId=null,username=null;
//                                if(data!=null){
//                                    JSONArray results= (JSONArray) data.get("results");
//                                    if(results!=null&&results.length()>0){
//                                        JSONObject res=results.getJSONObject(0);
//                                        objectId=res.getString("objectId");
//                                        username=res.getString("username");
//                                    }
//                                }
//                                if(StringUtil.getIsNull(objectId)){
//                                    ST.show("登录失败",1);
//                                }else{
//                                    ST.show(msg,0);
//                                    BmobUser.getCurrentUser(User.class);
//                                    zhuceTextView.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            finish();
//                                        }
//                                    },1000);
//                                }
//                            }else{
//                                ST.show(""+msg,1);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            ST.show("登录失败",1);
//                        }
//                    }
//                    @Override
//                    public void onMyError(VolleyError error) {
//                        ST.show("请检查您的网络",1);
//                    }
//                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0xaaaa){//上传缓存界面返回
            if(resultCode==-1){
                zhijie_denglu();
            }
        }
    }
    private BmobIMUserInfo info;
    /**
     * 连接im
     * <br>author:jinpneg</br>
     * <br>Time：2017/7/29 19:18</br>
     */
    private void bombim() {
        final User user = BmobUser.getCurrentUser(User.class);
        if(user==null){
            return ;
        }
        if(!StringUtil.getIsNull(user.getObjectId())){
            try{
                BmobIM.connect(user.getObjectId(), new ConnectListener() {
                    @Override
                    public void done(String uid, BmobException e) {
                        if (e == null) {
                            //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
//                            EventBus.getDefault().post(new RefreshEvent());
                            /**
                             * FIXME 连接成功后再进行修改本地用户信息的操作，并查询本地用户信息
                             */
                            BmobIM.getInstance().
                                    updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                                            user.getUsername(), user.getAvatar()));
                            info = BmobIM.getInstance().
                                    getUserInfo(BmobUser.getCurrentUser().getObjectId());
                        } else {
                        }
                    }
                });
                //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
                BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                    @Override
                    public void onChange(ConnectionStatus status) {
                    }
                });
            }catch (Exception e){
                info = new BmobIMUserInfo(user.getObjectId(),user.getUsername(),user.getAvatar());
            }
        }
    }
    /**
     * 消息发送监听器
     */
    public MessageSendListener listener =new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            LogUtil.e("onProgress："+value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            LogUtil.e("onStart："+msg);
//            adapter.addMessage(msg);
//            edit_msg.setText("");
//            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            LogUtil.e("done："+msg);
//            adapter.notifyDataSetChanged();
//            edit_msg.setText("");
//            scrollToBottom();
//            if (e != null) {
//                toast(e.getMessage());
//            }
        }
    };

    /**
     * @author: JinPeng
     * @Time: 2017/2/17 11:25
     * 直接配置走登录流程
     */
    private void zhijie_denglu() {
    }

}

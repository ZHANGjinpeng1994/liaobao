package com.liaobao.UI;

import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.SpeechUtility;
import com.iosdialog.view.ActionSheetBottomDialog;
import com.liaobao.Base.AppBaseActivity;
import com.liaobao.R;
import com.liaobao.Util.LogUtil;
import com.liaobao.Util.SysUtils;
import com.liaobao.Util.praseTools;
import com.liaobao.adapter.LiaotianAdpter;
import com.liaobao.config.APP;
import com.liaobao.db.ChatMsgManager;
import com.liaobao.entity.Answer;
import com.liaobao.entity.Msg;
import com.liaobao.speech.SpeechRecognizerUtil;
import com.liaobao.speech.SpeechSynthesizerUtil;
import com.liaobao.view.DropdownListView;
import com.liaobao.view.TitleBarUI;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 聊天主页面
 * <br>author:jinpneg</br>
 * <br>Time：2017/6/4 16:42</br>
 */
public class LiaotianActivity extends AppBaseActivity implements DropdownListView.OnRefreshListenerHeader
,LiaotianAdpter.OnClickMsgListener ,SpeechRecognizerUtil.RecoListener{

    private DropdownListView mListView;
    private LiaotianAdpter mLvAdapter;
    private List<Msg> list;
    private EditText input;
    private TextView send;
    private ImageView image_voice,image_add;
    private TitleBarUI title_bar;
    private ChatMsgManager chatMsgManager;

    private SimpleDateFormat sd;
    private int offset;
    //发送者和接收者
    private final String from = "liaobao";
    private final String to = "master";//发送者为自己

    private FinalHttp fh;
    private AjaxParams ajaxParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpeechUtility.createUtility(this, "appid="+ "59395831");

        setContentView(R.layout.activity_liaotian);
        sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        chatMsgManager=new ChatMsgManager(this);
        fh = new FinalHttp();

        initview();
        initData();
        initSpeech();
    }
    private void initSpeech() {
        speechRecognizerUtil = new SpeechRecognizerUtil(this);
        speechRecognizerUtil.setRecoListener(this);
        speechSynthesizerUtil = new SpeechSynthesizerUtil(this);
    }
    private void initview() {
        inititle();
        mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
        SysUtils.setOverScrollMode(mListView);
        input = (EditText) findViewById(R.id.input_sms);
        send = (TextView) findViewById(R.id.send_sms);
        image_voice = (ImageView) findViewById(R.id.image_voice);//语音
        image_add=(ImageView) findViewById(R.id.image_add);//添加
        image_add.setOnClickListener(this);
        image_voice.setOnClickListener(this);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    send.setVisibility(View.VISIBLE);
                    image_voice.setVisibility(View.GONE);
                } else {
                    send.setVisibility(View.GONE);
                    image_voice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        send.setOnClickListener(this); // 发送

        mListView.setOnRefreshListenerHead(this);
    }

    /**
     * 初始化标题
     * <br>author:jinpneg</br>
     * <br>Time：2017/6/4 21:58</br>
     */
    private void inititle() {
        title_bar= (TitleBarUI) findViewById(R.id.title_bar);
        title_bar.setZhongjianText("聊宝");
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

    @Override
    public void onClick(View v) {
//        super.onClick(v);
        Log.d("zjp", "onClick: "+v.getId());
        switch (v.getId()){
            case R.id.send_sms:
                final String content = input.getText().toString();
                Log.d("zjp", "onClick: "+content);
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                Msg msg = getChatInfoTo(content, APP.MSG_TYPE_TEXT);
                msg.setMsgId(chatMsgManager.insert(msg));
                msg.isanim_start=1;
                list.add(msg);
                offset = list.size();
                mLvAdapter.notifyDataSetChanged();
                mListView.setSelection(list.size());
                input.setText("");
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponse(APP.MSG_TYPE_TEXT, content);
                    }
                },500);
                break;
            case R.id.image_add:
                Msg msg2 = getChatInfoTo("http://tx.haiqq.com/uploads/allimg/150405/031R31a3-11.jpg", APP.MSG_TYPE_IMG);
                msg2.setMsgId(chatMsgManager.insert(msg2));
                msg2.isanim_start=1;
                list.add(msg2);
                offset = list.size();
                mLvAdapter.notifyDataSetChanged();
                mListView.setSelection(list.size());
                input.setText("");
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponse(APP.MSG_TYPE_TEXT, "笑话");
                    }
                },300);
            break;
            case R.id.image_voice://点击语音按钮
//                if (!TextUtils.isEmpty(voice_type) && voice_type.equals("1")) {//以语音形式发送
//                    speechRecognizerUtil.say(input, false);
//                } else {//以文本形式发送
                    speechRecognizerUtil.say(input, true);
//                }
                break;
        }
    }

    /**
     * 发送的信息
     * from为收到的消息，to为自己发送的消息
     *
     * @return
     */
    private Msg getChatInfoTo(String message, String msgtype) {
        String time = sd.format(new Date());
        Msg msg = new Msg();
        msg.setFromUser(from);
        msg.setToUser(to);
        msg.setType(msgtype);
        msg.setIsComing(1);
        msg.setContent(message);
        msg.setDate(time);
        return msg;
    }

    public void initData() {
        offset = 0;
//        list=new ArrayList<>();
        list = chatMsgManager.queryMsg(from, to, offset);
        offset = list.size();
        mLvAdapter = new LiaotianAdpter(this, list,this);
        mListView.setAdapter(mLvAdapter);
        mListView.setSelection(list.size());
    }
    /**
     * 下拉加载更多
     */
    @Override
    public void onRefresh() {
        List<Msg> list = chatMsgManager.queryMsg(from, to, offset);
        if (list.size() <= 0) {
            mListView.setSelection(0);
            mListView.onRefreshCompleteHeader();
            return;
        }
        this.list.addAll(0, list);
        offset = this.list.size();
        mListView.onRefreshCompleteHeader();
        mLvAdapter.notifyDataSetChanged();
        mListView.setSelection(list.size());
    }

    private void getResponse(final String msgtype, String info) {
        ajaxParams=new AjaxParams();
        ajaxParams.put("key",APP.ROBOT_KEY);
        ajaxParams.put("info",info);
        fh.post(APP.ROBOT_URL,ajaxParams, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                Answer answer = praseTools.praseMsgText((String) o);
                String responeContent;
                if (answer == null) {
                    responeContent = "网络错误";
                    changeList(msgtype, responeContent);
                } else {
                    switch (answer.getCode()) {
                        case "40001"://参数key错误
                        case "40002"://请求内容info为空
                        case "40004"://当天请求次数已使用完
                        case "40007"://数据格式异常
                        case "100000"://文本
                            responeContent = answer.getText();
                            changeList(msgtype, responeContent);
                            break;
                        case "200000"://链接
                            responeContent = answer.getText() + answer.getUrl();
                            changeList(msgtype, responeContent);
                            break;
                        case "302000"://新闻
                        case "308000"://菜谱
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                LogUtil.e("Failure>>" + strMsg);
//                changeList(msgtype, "网络连接失败");
            }
        });
    }
    private void changeList(String msgtype, String responeContent) {
        Msg msg = new Msg();
        msg.setIsComing(0);
        msg.setContent(responeContent);
        msg.setType(msgtype);
        msg.setFromUser(from);
        msg.setToUser(to);
        msg.setDate(sd.format(new Date()));
        msg.setMsgId(chatMsgManager.insert(msg));
        msg.isanim_start=1;
        list.add(msg);
        offset = list.size();
        mLvAdapter.notifyDataSetChanged();
//        if (msg.getType().equals(APP.MSG_TYPE_TEXT)) {
//            String speech_type = PreferencesUtils.getSharePreStr(this, APP.IM_SPEECH_TPPE);
//            if (!TextUtils.isEmpty(speech_type) && speech_type.equals("1")) {
////                speechSynthesizerUtil.speech(msg.getContent());
//            }
//        }

    }
    @Override
    public void click(int position) {//点击
        Msg msg = list.get(position);
        switch (msg.getType()) {
            case APP.MSG_TYPE_TEXT://文本
                break;
            case APP.MSG_TYPE_IMG://图片
                break;
            case APP.MSG_TYPE_VOICE://语音

                break;
        }
    }

    @Override
    public void longClick(int position) {//长按
        Msg msg = list.get(position);
        switch (msg.getType()) {
            case APP.MSG_TYPE_TEXT://文本
                clip(msg, position);
                break;
            case APP.MSG_TYPE_IMG://图片
                break;
            case APP.MSG_TYPE_VOICE://语音
//                delonly(msg, position);
                break;
        }
    }
    // 语音听写工具
    SpeechRecognizerUtil speechRecognizerUtil;
    // 语音合成工具
    SpeechSynthesizerUtil speechSynthesizerUtil;
    /**
     * 带复制文本的操作
     */
    void clip(final Msg msg, final int position) {
        new ActionSheetBottomDialog(this)
                .builder()
                .addSheetItem("复制", ActionSheetBottomDialog.SheetItemColor.Blue, new ActionSheetBottomDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        ClipboardManager cmb = (ClipboardManager) LiaotianActivity.this.getSystemService(LiaotianActivity.CLIPBOARD_SERVICE);
                        cmb.setText(msg.getContent());
//                        ToastUtil.showToast(ChatActivity.this, "已复制到剪切板");
                    }
                })
                .addSheetItem("朗读", ActionSheetBottomDialog.SheetItemColor.Blue, new ActionSheetBottomDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        speechSynthesizerUtil.speech(msg.getContent());
                    }
                })
                .addSheetItem("删除", ActionSheetBottomDialog.SheetItemColor.Blue, new ActionSheetBottomDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        list.remove(position);
                        offset = list.size();
                        mLvAdapter.notifyDataSetChanged();
                        chatMsgManager.deleteMsgById(msg.getMsgId());
                    }
                })
                .show();
    }
    /**
     * 发送语音
     *
     * @param content
     */
    void sendMsgVoice(String content) {
//        String[] _content = content.split(Const.SPILT);
//        Msg msg = getChatInfoTo(content, Const.MSG_TYPE_VOICE);
//        msg.setMsgId(msgDao.insert(msg));
//        listMsg.add(msg);
//        offset = listMsg.size();
//        mLvAdapter.notifyDataSetChanged();
//        getFromMsg(Const.MSG_TYPE_TEXT, _content[1]);
    }

    @Override
    public void recoComplete(String text) {
        LogUtil.e("=="+text);
        input.setText(""+text);
    }
}

package com.liaobao.config;

import android.os.Environment;

/**
 * 项目静态资源
 * <br>author:jinpneg</br>
 * <br>Time：2017/6/4 17:58</br>
 */
public class APP {
    public static final String FILE_VOICE_CACHE = Environment.getExternalStorageDirectory() + "/liaobao/voice/";
    public static final String MSG_TYPE_TEXT="msg_type_text";//文本消息
    public static final String MSG_TYPE_IMG="msg_type_img";//图片
    public static final String MSG_TYPE_VOICE="msg_type_voice";//语音

    public static final String ROBOT_URL="http://www.tuling123.com/openapi/api";
    public static final String ROBOT_KEY="ffc78a94dc78462e990530721411d881";

    public static final String Bomb_Key="c23e00f8c821d81b";
    public static final String Bomb_URL="http://cloud.bmob.cn/"+Bomb_Key+"/";
    public static final String Denglu="denglu";
    public static final String SignUp="userSignUp";

    //是否是debug模式
    public static final boolean DEBUG=true;
    //好友请求：未读-未添加->接收到别人发给我的好友添加请求，初始状态
    public static final int STATUS_VERIFY_NONE=0;
    //好友请求：已读-未添加->点击查看了新朋友，则都变成已读状态
    public static final int STATUS_VERIFY_READED=2;
    //好友请求：已添加
    public static final int STATUS_VERIFIED=1;

    public final static String  SPILT = "☆";
}

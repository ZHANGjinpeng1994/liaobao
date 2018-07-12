package com.liaobao.Util;

import com.liaobao.entity.Answer;

import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * json适配工具类
 * <br>author:jinpneg</br>
 * <br>Time：2017/6/6 21:58</br>
 */
public class praseTools {
    public static Answer praseMsgText(String result) {
        Answer answer;
        JSONObject jsonObject;
        try {
            answer = new Answer();
            jsonObject = (JSONObject) new JSONTokener(result).nextValue();
            answer.setJsoninfo(result);
            answer.setCode(jsonObject.optString("code"));
            answer.setText(jsonObject.optString("text").replace("图灵机器人", "小Q"));
            switch (answer.getCode()) {
                case "40001"://参数key错误
                case "40002"://请求内容info为空
                case "40004"://当天请求次数已使用完
                case "40007"://数据格式异常
                case "100000":
                    //因text 字段 各类型都会返回，answer.setText已做处理，因此这里不做处理
                    break;
                case "200000":
                    answer.setUrl(jsonObject.optString("url"));
                    break;
                case "302000":
                    break;
                case "308000":
                    break;
            }

        } catch (Exception e) {
            return null;
        }
        return answer;
    }
}

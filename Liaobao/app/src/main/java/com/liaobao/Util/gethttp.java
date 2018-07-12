package com.liaobao.Util;

import android.content.Intent;
import android.os.Message;

import com.liaobao.Util.LogUtil;
import com.liaobao.Util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * myhttp 工具
 */
public class gethttp {
    private void initdata(String s){
//        String[] sts=intent.getStringArrayExtra("urls");
//        Urls=new ArrayList<>();
//        if(sts!=null)
//        for (int i = 0; i <sts.length ; i++) {
//            Urls.add(""+sts[i]);
//        }
//        try {
//            JSONObject object=new JSONObject(s);
//            JSONArray jsonArray=object.getJSONArray("result");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject o=jsonArray.getJSONObject(i);
//                String IMG= o.getString("img");
//                Urls.add(IMG);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            JSONObject object=new JSONObject(s);
            JSONArray jsonArray=object.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o=jsonArray.getJSONObject(i);
                String IMG= o.getString("thumb");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void userLoginh(){
        String
                urlstr="http://m.image.so.com/?ch=车";
        StringBuffer str = null ;
        URL url;
        HttpURLConnection co = null;
        try {
            url = new URL(urlstr);
            LogUtil.e(urlstr);
            co=(HttpURLConnection) url.openConnection();
            co.setConnectTimeout(15000);//访问超时时间
            co.setReadTimeout(15000);//读取超时时间
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            String content ="ch="+"美女";
//                String content ="q="+ URLEncoder.encode("车","utf-8");
//                content+="&userPwd="+pwd+"&name="+ PhoneTools.getBrand()+PhoneTools.getModel();
            co.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                co.setRequestProperty("Content-Length", String.valueOf(content.getBytes("utf-8").length));
//			//post输出
            LogUtil.e(content);
            DataOutputStream out = new DataOutputStream(co.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            co.connect();
            int i=co.getResponseCode();
            LogUtil.e(""+i);
            if(i==200){
                InputStream in=co.getInputStream();
                byte[] b=new byte[1024];
                int inum=0;
                str = new StringBuffer();
                while((inum=in.read(b))!=-1){
                    str.append(new String(b,0,inum));
                }
                if(!StringUtil.getIsNull(str.toString())){
                    String s="<script type=\"text/json\"id=initData>";
                    String s2="\\]\\}</script>";
                    String data=str.toString();
                    if(data.contains(s)){
                        String[] ss=data.split(s);
                        if(ss!=null&&ss.length>1) {
//                            if(ss[1].contains(s2)){
                            String[] ss2 = ss[1].split(s2);
                            String SSSS = ss2[0] + "]}";
                            LogUtil.e("=="+SSSS);
//                            }
                        }
                    }
                }
            }else{
                LogUtil.e(co.getResponseMessage()+"==="+i);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtil.e("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("IOException");
        }finally{
            LogUtil.e("disconnect");
            co.disconnect();
            co=null;
        }
        if(str==null)return;
//             DebugLog.E_DPID(str.toString());
    }

    private void userLoginh2(){
        String strdata="车";
        try{
            strdata= URLEncoder.encode("美女","utf-8");
        }catch (Exception e){
        }

        String
                urlstr="http://m.image.so.com/i?q="+strdata+"#q="+strdata+"&grpmd5=0a5a8fbd135f8616618d5a5875f2a6aa&id=8ea6a7a96c79091ccb393ffe2a310c32";

        urlstr="http://stu.baidu.com/i?objurl=http://file.bmob.cn/M00/12/F9/oYYBAFUcAo-APEiUAAA3sXYDK6k387.jpg&filename=&fm=15&rt=0&pn=0&rn=9&pn=0&ct=1&stt=1&tn=insimijson";
        StringBuffer str = null ;
        URL url;
        HttpURLConnection co = null;
        try {
            url = new URL(urlstr);
            LogUtil.e(urlstr);
            co=(HttpURLConnection) url.openConnection();
            co.setConnectTimeout(15000);//访问超时时间
            co.setReadTimeout(15000);//读取超时时间
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            String content ="q="+"";
            co.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			//post输出
            DataOutputStream out = new DataOutputStream(co.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            co.connect();
            int i=co.getResponseCode();
            LogUtil.e(""+i);
            if(i==200){
                InputStream in=co.getInputStream();
                byte[] b=new byte[1024];
                int inum=0;
                str = new StringBuffer();
                while((inum=in.read(b))!=-1){
                    str.append(new String(b,0,inum));
                }
                LogUtil.e("==="+str.toString());
                if(!StringUtil.getIsNull(str.toString())){
                    String s="window.initData =";
                    String s2=" \\};</script>";
                    String data=str.toString();
                    if(data.contains(s)){
                        String[] ss=data.split(s);
                        if(ss!=null&&ss.length>1) {
                            String[] ss2 = ss[1].split(s2);
                            String SSSS = ss2[0] + "}";
                            LogUtil.e("=="+SSSS);
                        }
                    }
                }
            }else{
                LogUtil.e(co.getResponseMessage()+"==="+i);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtil.e("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("IOException");
        }finally{
            LogUtil.e("disconnect");
            co.disconnect();
            co=null;
        }
        if(str==null)return;
    }
}

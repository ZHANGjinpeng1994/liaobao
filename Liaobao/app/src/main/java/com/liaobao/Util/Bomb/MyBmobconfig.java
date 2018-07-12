package com.liaobao.Util.Bomb;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobTableSchema;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author: zhangjinpeng
 * @Time: 2017/7/19 10 19
 * 必须要在application初始化
 */
public class MyBmobconfig {
    public static String APPID = "0321e587250b3d3f846d8b9e329ec4bb";
    private Context context;
    private static MyBmobconfig instance;

    public static MyBmobconfig getInstance(Context context) {
        if(instance==null){
            instance=new MyBmobconfig(context);
        }
        return instance;
    }
    private MyBmobconfig(Context context){
        this.context=context;
        //提供以下两种方式进行初始化操作：
		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
		BmobConfig config =new BmobConfig.Builder(context)
		//设置appkey
		.setApplicationId(APPID)
		//请求超时时间（单位为秒）：默认15s
		.setConnectTimeout(30)
		//文件分片上传时每片的大小（单位字节），默认512*1024
		.setUploadBlockSize(1024*1024)
		//文件的过期时间(单位为秒)：默认1800s
		.setFileExpiration(5500)
		.build();
		Bmob.initialize(config);
        //第二：默认初始化
//        Bmob.initialize(context, APPID);
    }
    /**获取指定账户下的所有表的表结构信息
     * @method getAllTableSchema
     * @return void
     * @exception
     */
    public void getAllTableSchema(){
        Bmob.getAllTableSchema(new QueryListListener<BmobTableSchema>() {
            @Override
            public void done(List<BmobTableSchema> schemas, BmobException ex) {
                if(ex==null && schemas!=null && schemas.size()>0){
                    Log.e("zjp",""+schemas.get(0).getClassName()+"---"+schemas.get(0).getFields().toString());
                }else{
                    Log.e("zjp","获取所有表的表结构信息失败:" + ex.getLocalizedMessage()+"("+ex.getErrorCode()+")");
                }
            }
        });
    }

    /** 获取指定表的表结构信息
     * @method getTableSchema
     * @return void
     * @exception //_User
     */
    public void getTableSchema(String tablename){
        Bmob.getTableSchema(""+tablename, new QueryListener<BmobTableSchema>() {
            @Override
            public void done(BmobTableSchema schema, BmobException ex) {
                if(ex==null){
                    Log.e("zjp",""+schema.getClassName()+"---"+schema.getFields().toString());
                }else{
                    Log.e("zjp","获取用户表的表结构信息失败:" + ex.getLocalizedMessage()+"("+ex.getErrorCode()+")");
                }
            }
        });
    }
    public void queryData(String key){
        BmobQuery<UserConfig> query = new BmobQuery<UserConfig>();
        query.getObject(key,new QueryListener<UserConfig>(){
            @Override
            public void done(UserConfig userMode, BmobException ex) {
                if(ex==null){
                    Log.e("zjp",""+userMode.getTableName()+"---");
                }else{
                    Log.e("zjp","=====:" + ex.getLocalizedMessage()+"("+ex.getErrorCode()+")");
                }
            }
        });
    }
    public void adddata(){
        UserConfig p2 = new UserConfig();
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Log.e("zjp","添加数据成功，返回objectId为："+objectId);
                }else{
                    Log.e("zjp","创建数据失败：" + e.getMessage());
                }
            }
        });
    }
}

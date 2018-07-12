package com.liaobao.UI.Wode;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.iflytek.cloud.thirdparty.M;
import com.liaobao.Base.MYBaseActivity;
import com.liaobao.R;
import com.liaobao.Util.Bomb.MyBmobconfig;
import com.liaobao.Util.LogUtil;
import com.liaobao.Util.MyVolley.VolleyListenerInterface;
import com.liaobao.Util.MyVolley.VolleyRequestUtil;
import com.liaobao.Util.StringUtil;
import com.liaobao.config.APP;
import com.liaobao.view.TitleBarUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Zhuce_Activity extends MYBaseActivity implements OnClickListener{

	private EditText zhuce_zhanghao_ET,zhuce_mima_ET,zhuce_yanzhengma_ET;
	private TextView zhuce_yonghuxieyi_TV;
	private String  userMobile, password;
	private MyBmobconfig myBmobconfig;
	private TitleBarUI title_bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuce);
		myBmobconfig=MyBmobconfig.getInstance(this);
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
		title_bar.setZhongjianText("注册");
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
		zhuce_zhanghao_ET=(EditText) findViewById(R.id.zhuce_zhanghao_ET);
		zhuce_mima_ET=(EditText) findViewById(R.id.zhuce_mima_ET);
		zhuce_yanzhengma_ET=(EditText) findViewById(R.id.zhuce_yanzhengma_ET);
		zhuce_yonghuxieyi_TV=(TextView) findViewById(R.id.zhuce_yonghuxieyi_TV);

	}

	private void initdata() {
	}

	private void onclick() {
		findViewById(R.id.zhuce_queding_TV).setOnClickListener(this);
		zhuce_yonghuxieyi_TV.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zhuce_queding_TV://确定
			getJS();
			break;
		case R.id.zhuce_yonghuxieyi_TV:
			break;
		default:
			break;
		}
	}
	/**
	 * @author: JinPeng
	 * @Time:2017-1-19: 上午11:13:50
	 * 
	 */
	/**注册接口*/
	private void getJS(){
		userMobile=zhuce_zhanghao_ET.getText().toString().trim();
		password=zhuce_mima_ET.getText().toString().trim();
		String password2=zhuce_yanzhengma_ET.getText().toString().trim();
		if(StringUtil.getIsNull(userMobile)){
			ST.show("用户名不能为空",1);
			return;
		}
		if(!StringUtil.issPhoneNumberNO(userMobile)){
			ST.show("请输入正确的手机号",1);
			return;
		}
		if(StringUtil.getIsNull(password)){
			ST.show("密码不能为空",1);
			return;
		}
		if(StringUtil.getIsNull(password2)){
			ST.show("确认密码不能为空",1);
			return;
		}
		if(!password2.equals(password)){
			ST.show("两次密码不一致",1);
			return;
		}
		zhuce(userMobile,password);

	}
	private void zhuce(final String userMobile,final String password) {
//		myBmobconfig.adddata();
		Map<String,String> MAP=new HashMap();
		MAP.put("name",userMobile);
		MAP.put("password",password);
		VolleyRequestUtil.RequestPost(this, APP.Bomb_URL+APP.SignUp, "",
				MAP,new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener){
			@Override
			public void onMySuccess(String result) {
				LogUtil.e("Success="+result);
				try {
					JSONObject J=new JSONObject(result);
					int code=J.getInt("code");
					String msg= J.getString("msg");
					if(code==0){
						ST.show(""+msg,0);
					}else{
						ST.show(""+msg,1);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onMyError(VolleyError error) {
				ST.show("请检查您的网络",1);
			}
		});
	}
	/**
	 * @author: JinPeng
	 * @Time:2016-11-7: 上午11:07:36
	 * 注册完成后调转登录
	 */
	private void zhuce_finish(String name,String pwd) {
		Intent intent=new Intent();
		intent.putExtra("name",name);
		intent.putExtra("pwd", pwd);
		setResult(-1, intent);
		this.finish();
	}
}

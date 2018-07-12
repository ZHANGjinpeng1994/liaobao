package com.liaobao.view;





import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.liaobao.R;


public class TitleBarUI extends FrameLayout implements OnClickListener {

	private ImageView zuobianImageView;
	private ImageView youbianImageView;
	private TextView zuobianTextView;
	private TextView zhongjianTextView;
	private TextView youbianTextView;
	private ImageView xiansuotishiImageView;
	private TitleBarListener listener;
	private RelativeLayout ui_RelativeLayout;
	private int[] mColor = { 0x3300CBD9, 0x4400CBD9, 0x5500CBD9, 0x6600CBD9,
			0x7700CBD9, 0x8800CBD9, 0x8800CBD9, 0xAA00CBD9, 0xBB00CBD9,
			0xCC00CBD9, 0xDD00CBD9, 0xEE00CBD9, 0xFF00CBD9 };


	public interface TitleBarListener {
		public void zuobian();

		public void youbian();

		public void zhongjian();
	}

	public TitleBarUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ui_titlebar, this);
		init();
	}

	private void init() {
		initUI();
		click();
	}

	private void click() {
		// TODO Auto-generated method stub
		zuobianImageView.setOnClickListener(this);
		zuobianTextView.setOnClickListener(this);
		zhongjianTextView.setOnClickListener(this);
		youbianTextView.setOnClickListener(this);
		youbianImageView.setOnClickListener(this);
	}

	private void initUI() {
		zuobianImageView = (ImageView) findViewById(R.id.zuobianImageView);
		zuobianTextView = (TextView) findViewById(R.id.zuobianTextView);
		zhongjianTextView = (TextView) findViewById(R.id.zhongjianTextView);
		youbianTextView = (TextView) findViewById(R.id.youbianTextView);
		youbianImageView = (ImageView) findViewById(R.id.youbianImageView);
		xiansuotishiImageView = (ImageView) findViewById(R.id.xiansuotishiImageView);
		ui_RelativeLayout = (RelativeLayout) findViewById(R.id.ui_RelativeLayout);
	}

	public void setyoubian_Clickable(boolean isclice){
			youbianTextView.setClickable(isclice);
			youbianImageView.setClickable(isclice);
	} 
	
	/**
	 * 设置线索提示图标是否显示
	 * @param isVisibility
	 */
	
	public void setXiansuotishi(boolean isVisibility){
		if (isVisibility) {
			xiansuotishiImageView.setVisibility(View.VISIBLE);
		} else {
			xiansuotishiImageView.setVisibility(View.GONE);
		}
	}
	/**
	 * 设置边图片是否显示，默认图片显示为返回箭?
	 * 
	 * @param isVisibility
	 */
	public void setLeftImageisVisibility(boolean isVisibility) {
		if (isVisibility) {
			zuobianImageView.setVisibility(View.VISIBLE);
		} else {
			zuobianImageView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置�?��边图片是否显示，默认图片显示为返回箭�?
	 * 
	 * @param isVisibility
	 */
	public void setRightImageisVisibility(boolean isVisibility) {
		if (isVisibility) {
			youbianImageView.setVisibility(View.VISIBLE);
		} else {
			youbianImageView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边第一个textview是否显示，默认是返回但为隐藏
	 * 
	 * @param isVisibility
	 */
	public void setLeftTextViewVisibility(boolean isVisibility) {
		if (isVisibility) {
			zuobianTextView.setVisibility(View.VISIBLE);
		} else {
			zuobianTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置中间textview是否可见，默认可见，但无内容，可不设�?
	 * 
	 * @param isVisibility
	 */
	public void setZhongjianTextViewVisibility(boolean isVisibility) {
		if (isVisibility) {
			zhongjianTextView.setVisibility(View.VISIBLE);
		} else {
			zhongjianTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边textview是否可见，默认可见，但无内容，可不设�?
	 * 
	 * @param isVisibility
	 */
	public void setRightTextViewVisibility(boolean isVisibility) {
		if (isVisibility) {
			youbianTextView.setVisibility(View.VISIBLE);
		} else {
			youbianTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边图片，调用此方法无需再设置此控件是否可见
	 * 
	 * @param resId
	 */
	public void setLeftImageResources(int resId) {
		setLeftImageisVisibility(true);
		zuobianImageView.setImageResource(resId);
	}

	/**
	 * 设置右边图片，调用此方法无需再设置此控件是否可见
	 * 
	 * @param resId
	 */
	public void setRighttImageResources(int resId) {
		setRightImageisVisibility(true);
		youbianImageView.setImageResource(resId);
	}

	/**
	 * 设置左边textview文字内容，调用此方法无需再设置此控件可见
	 * 
	 * @param text
	 */
	public void setLeftText(String text) {
		setLeftTextViewVisibility(true);
		setLeftImageisVisibility(false);
		zuobianTextView.setText(text);
	}
	/**设置左边颜色*/
	public void setLeftTextcolor(String text,int color){
		setLeftText(text);
		zuobianTextView.setTextColor(color);
	}
	/**
	 * 设置中间textview文字内容，调用此方法无需再设置此控件是否可见
	 * 
	 * @param text
	 */
	public void setZhongjianText(String text) {
		setZhongjianTextViewVisibility(true);
		zhongjianTextView.setText(text);
		zhongjianTextView.setTextColor(0xffffffff);
	}

	/**
	 * 设置右边textview文字内容，调用此方法无需再设置此控件可见
	 * 
	 * @param text
	 */
	public void setRightText(String text) {
		setRightTextViewVisibility(true);
		youbianTextView.setText(text);
	}
	/**设置右边颜色*/
	public void setRightTextcolor(String text,int color){
		setRightText(text);
		youbianTextView.setTextColor(color);
	}
	/**
	 * 设置监听事件
	 */
	public void setListener(TitleBarListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (listener == null)
			return;
		switch (v.getId()) {
		case R.id.zuobianImageView:
			listener.zuobian();
			break;
		case R.id.zuobianTextView:
			listener.zuobian();
			break;
		case R.id.youbianTextView:
			listener.youbian();
			break;
		case R.id.youbianImageView:
			listener.youbian();
			break;
		case R.id.zhongjianTextView:
			listener.zhongjian();
			break;

		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				ui_RelativeLayout.setBackgroundColor(msg.arg1);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 设置背景颜色，不支持图片
	 * 
	 * @param alpha
	 */
	public void setBackGround(final int alpha) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < mColor.length; i++) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg = handler.obtainMessage();
					msg.what = 1;
					if (alpha > 100) {
						msg.arg1 = mColor[i];
					} else {
						msg.arg1 = mColor[mColor.length - 1 - i];
					}
					handler.sendMessage(msg);

				}

			}
		}).start();
	}
}
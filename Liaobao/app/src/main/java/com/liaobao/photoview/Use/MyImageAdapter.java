package com.liaobao.photoview.Use;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liaobao.R;
import com.liaobao.Util.LogUtil;
import com.liaobao.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class MyImageAdapter extends PagerAdapter {
    public static final String TAG = MyImageAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Activity activity;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions options;

    public MyImageAdapter(List<String> imageUrls, Activity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(0))
                .build();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View imageLayout = LayoutInflater.from(activity).inflate(R.layout.item_viewpager, container, false);
        String url = imageUrls.get(position);
        PhotoView photoView= (PhotoView) imageLayout.findViewById(R.id.image);
        final LinearLayout loading_progress= (LinearLayout) imageLayout.findViewById(R.id.loading);
        final TextView loadingText= (TextView) imageLayout.findViewById(R.id.progress);
        ((TextView)imageLayout.findViewById(R.id.weizhi_textview)).setText((position+1)+"/"+imageUrls.size());
//        ImageLoader.getInstance().displayImage((url),photoView);
//        imageLoader.displayImage(url, photoView, options);
        imageLoader.displayImage(url, photoView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                loading_progress.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "网络异常";
                        break;
                    case DECODING_ERROR:
                        message = "图片解析失败";
                        break;
                    case NETWORK_DENIED:
                        message = "图片加载失败";
                        break;
                    case OUT_OF_MEMORY:
                        message = "内存溢出";
                        break;
                    case UNKNOWN:
                        message = "未知错误";
                        break;
                }
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                loading_progress.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                loading_progress.setVisibility(View.GONE);
            }
        },new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current,int total) {
                //在这里更新 ProgressBar的进度信息
                int progress=0;
                if(current!=0&&current<=total){
                    progress=(int)((float) current/(float)total*100);
                    loadingText.setText(""+progress+"%");
                }
            }
        });
        container.addView(imageLayout);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });
        return imageLayout;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

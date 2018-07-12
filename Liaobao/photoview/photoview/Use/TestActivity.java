package com.liaobao.photoview.Use;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liaobao.R;
import com.liaobao.photoview.PhotoView;
import com.liaobao.photoview.PhotoViewAttacher;

public class TestActivity extends AppCompatActivity {

    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.mipmap.ic_launcher);
        mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.update();

    }

}

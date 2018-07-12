package com.liaobao.UI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liaobao.R;

import java.util.ArrayList;
import java.util.List;


public class GridActivity extends AppCompatActivity {

    private static RecyclerView recyclerview;
    private GridLayoutManager mLayoutManager;
    private GridAdapter GridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        initView();//初始化布局
        setListener();//设置监听事件

    }

    private void initView(){

        recyclerview=(RecyclerView)findViewById(R.id.grid_recycler);
        mLayoutManager=new GridLayoutManager(GridActivity.this,3,GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerview.setLayoutManager(mLayoutManager);
        List<String> datas=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if(i%2==0){
                datas.add("呵呵"+i);
            }else{
                datas.add("");
            }
        }
        GridAdapter=new GridAdapter(this,datas);
        recyclerview.setAdapter(GridAdapter);

    }

    private void setListener(){
        //swipeRefreshLayout刷新监听
    }


    public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

        private Context mContext;
        private List<String> datas;//数据
        //适配器初始化
        public GridAdapter(Context context,List<String> datas) {
            mContext=context;
            this.datas=datas;
        }

        @Override
        public int getItemViewType(int position) {
            //判断item类别，是图还是显示页数（图片有URL）
            if (!TextUtils.isEmpty(datas.get(position))) {
                return 0;
            } else {
                return 1;
            }
//            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //根据item类别加载不同ViewHolder
            if(viewType==0){
                View view = LayoutInflater.from(mContext
                ).inflate(R.layout.item_chat_text_sent, parent, false);//这个布局就是一个imageview用来显示图片
                MyViewHolder holder = new MyViewHolder(view);

                //给布局设置点击和长点击监听
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);

                return holder;
            }else{
                MyViewHolder2 holder2=new MyViewHolder2(LayoutInflater.from(
                        mContext).inflate(R.layout.item_chat_text_rece, parent,
                        false));//这个布局就是一个textview用来显示页数
                return holder2;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
            if(holder instanceof MyViewHolder){
                ((MyViewHolder) holder).iv.setText("页######################");
            }else if(holder instanceof MyViewHolder2){
                ((MyViewHolder2) holder).tv.setText(datas.get(position)+"页");
            }

        }

        @Override
        public int getItemCount() {
            return datas.size();//获取数据的个数
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
        //自定义ViewHolder，用于加载图片
        class MyViewHolder extends RecyclerView.ViewHolder
        {
            private TextView iv;

            public MyViewHolder(View view)
            {
                super(view);
                iv = (TextView) view.findViewById(R.id.tv_text);
            }
        }
        //自定义ViewHolder，用于显示页数
        class MyViewHolder2 extends RecyclerView.ViewHolder
        {
            private TextView tv;

            public MyViewHolder2(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_text);
            }
        }

        //添加一个item
        public void addItem(String meizi, int position) {
            datas.add(position, meizi);
            notifyItemInserted(position);
            recyclerview.scrollToPosition(position);//recyclerview滚动到新加item处
        }

        //删除一个item
        public void removeItem(final int position) {
            datas.remove(position);
            notifyItemRemoved(position);
        }
    }

}

package com.muhe.kindy.floatlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private MyAdapter mMyAdapter;
    private ArrayList<Bean> mData;

    private ListView mListView;
    private LinearLayout mFloatView;
    private TextView mFloatText;
    private TextView textView;
    private SlidebarView slidebarView;
    private HashMap<String,Integer> positionMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.id_listview);
        mFloatView = (LinearLayout) findViewById(R.id.id_float_view);
        mFloatText = (TextView) mFloatView.findViewById(R.id.id_tv_item);
        textView = (TextView) findViewById(R.id.textView);
        slidebarView = (SlidebarView) findViewById(R.id.slidebarView);

        positionMap = new HashMap<>();

        slidebarView.setTextView(textView);
        slidebarView.setOnTouchingLetterChangedListener(new SlidebarView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                Log.i("logger----",s+"    "+positionMap.get(s));
                mListView.setSelection(positionMap.get(s));
            }
        });

        // TODO 方便观察
//        ViewHelper.setX(mFloatText, 10);

        mData = new ArrayList<>();
        positionMap.put("热门", mData.size());
        mData.add(new Bean("热门", true));
        mData.add(new Bean("北京", false));
        mData.add(new Bean("上海",false));
        mData.add(new Bean("广州", false));
        mData.add(new Bean("深圳",false));

        positionMap.put("B", mData.size());
        mData.add(new Bean("B",true));
        for(int i=0; i<3; i++) {
            mData.add(new Bean("北京" + i, false));
        }

        positionMap.put("C",mData.size());
        mData.add(new Bean("C", true));
        for(int i=0; i<1; i++) {
            mData.add(new Bean("重庆" + i, false));
        }

        positionMap.put("D",mData.size());
        mData.add(new Bean("D",true));
        mData.add(new Bean("洞庭",false));

        positionMap.put("H",mData.size());
        mData.add(new Bean("H", true));
        for(int i=0; i<5; i++) {
            mData.add(new Bean("湖南" + i, false));
        }

        positionMap.put("S",mData.size());
        mData.add(new Bean("S", true));
        for(int i=0; i<20; i++) {
            mData.add(new Bean("上海" + i, false));
        }
        mMyAdapter = new MyAdapter(this, mData, mFloatView);
        mMyAdapter.setOnChangeFloatViewContentListener(mOnChangeFloatViewContentListener);
        mListView.setAdapter(mMyAdapter);
        mListView.setOnScrollListener(mMyAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("logger------", mData.get(i).name);
            }
        });


        slidebarView.setIndex(new String[]{"热门","B","C","D","H","S"});
    }

    public void addItem(View v) {
//        mData.add(4, new Bean("new item", false));
//        mMyAdapter.notifyDataSetChanged();
    }

    private OnChangeFloatViewContentListener<Bean> mOnChangeFloatViewContentListener = new OnChangeFloatViewContentListener<Bean>() {
        @Override
        public void onChangeFloatViewContent(Bean bean) {
            String txt = mFloatText.getText().toString();
            if(!txt.equalsIgnoreCase(bean.name)) {
                mFloatText.setText(bean.name);
            }
        }
    };
}
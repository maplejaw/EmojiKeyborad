package com.maplejaw.emojikeyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.maplejaw.library.MutilEmoticonKeyboard;

import java.util.ArrayList;
import java.util.List;

public class MutilActivity extends AppCompatActivity {
    private MutilEmoticonKeyboard mEmoticonKeyboard;
    private EditText mEditText;
    private ListView mListView;
    private MyAdapter mMyAdapter;
    private List<String> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutil);
        mEmoticonKeyboard= (MutilEmoticonKeyboard) this.findViewById(R.id.mEmoticonKeyboard);
        mEditText= (EditText) this.findViewById(R.id.editText);

        mEmoticonKeyboard.setupWithEditText(mEditText);

        mListView= (ListView) this.findViewById(R.id.listView);
        mListView.setAdapter(mMyAdapter=new MyAdapter(mList=new ArrayList<String>()));

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mEmoticonKeyboard.setVisibility(View.GONE);
                }
            }
        });

    }


    // 如果需要处理返回收起面板的话
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mEmoticonKeyboard.getVisibility() == View.VISIBLE) {
                mEmoticonKeyboard.setVisibility(View.GONE);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_send:
                String content=mEditText.getText().toString().trim();
                if(content.length()>0){
                    mList.add(content);
                    mMyAdapter.notifyDataSetChanged();
                    mEditText.getText().clear();
                }
                break;
            case R.id.iv_emoji:
                toggleKeyboard(view);
                break;
        }

    }


    class MyAdapter extends BaseAdapter {
        private List<String> contentList;
        public MyAdapter(List<String> l){
            this.contentList=l;
        }
        @Override
        public int getCount() {
            return contentList.size();
        }

        @Override
        public String getItem(int position) {
            return contentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if(convertView==null){
                textView= (TextView) getLayoutInflater().inflate(R.layout.item,parent,false);
                convertView=textView;
                convertView.setTag(textView);
            }else{
                textView= (TextView) convertView.getTag();
            }

            textView.setText(getItem(position));
            return convertView;
        }
    }



    private void showKeyboard() {
        mEditText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) mEditText.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEditText, 0);
    }

    private void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mEditText.clearFocus();
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public void toggleKeyboard(final View view) {
        if (mEmoticonKeyboard.getVisibility() == View.VISIBLE) {
            showKeyboard();
        } else {
            hideKeyboard();
            mEmoticonKeyboard.setVisibility(View.VISIBLE);
        }
    }




}

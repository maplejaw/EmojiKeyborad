package com.maplejaw.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maplejaw.library.bean.EmoticonSet;

import java.util.ArrayList;
import java.util.List;

public class MutilEmoticonKeyboard extends LinearLayout implements EmoticonKeyboard.OnTabChangeLisenter, View.OnClickListener {

    private RadioGroup mIndexGroup;
    private EmoticonKeyboard mEmoticonKeyboard;
    private List<EmojiSetEntity> mList=new ArrayList<EmojiSetEntity>();


    public MutilEmoticonKeyboard(Context context) {
        this(context,null);
    }
    public MutilEmoticonKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MutilEmoticonKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context,R.layout.include_mutil_emoji_panel,this);//映射
        initView();
        initEmoticonSet();

    }

    public  void setupWithEditText(EditText editText) {
        mEmoticonKeyboard.setupWithEditText(editText);

    }

    public void initView(){
        mEmoticonKeyboard= (EmoticonKeyboard) this.findViewById(R.id.mp_emoji_single_keyborad);
        mIndexGroup= (RadioGroup) this.findViewById(R.id.mp_emoji_single_keyborad_index);
        mEmoticonKeyboard.setOnTabChangeLisenter(this);
    }


    /**
     * 初始化索引
     * @param context
     */
    private void initIndexView(Context context) {
        RadioButton indexView;
        mIndexGroup.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            EmojiSetEntity entity=mList.get(i);
            indexView = new RadioButton(context);
            indexView.setButtonDrawable(0);
            indexView.setBackgroundResource(R.drawable.selector_index_page);
            indexView.setText(entity.getIndex());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            indexView.setPadding(20,10,20,10);
            indexView.setTag(i);
            mIndexGroup.addView(indexView, layoutParams);
            if (i == 0) {
                indexView.setChecked(true);
            }
            indexView.setOnClickListener(this);
        }


    }

    /**
     * 初始化表情集
     */
    private void initEmoticonSet(){
        mList.add(new EmojiSetEntity("默认",EmoticonSet.QQ));
        mList.add(new EmojiSetEntity("滑稽",EmoticonSet.TIEBA));
        mList.add(new EmojiSetEntity("emoji",EmoticonSet.EMOJI));
        mEmoticonKeyboard.fillMutilEmoticon(mList);
        initIndexView(getContext());
    }


    @Override
    public void onTabChange(int tabPosition) {
        ( (RadioButton)mIndexGroup.getChildAt(tabPosition)).setChecked(true);
    }



    @Override
    public void onClick(View v) {
        mEmoticonKeyboard.updateTabPosition((Integer) v.getTag());
    }
}

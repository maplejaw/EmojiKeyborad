package com.maplejaw.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.maplejaw.library.bean.Emojicon;
import com.maplejaw.library.bean.EmoticonSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmoticonKeyboard extends LinearLayout implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private RadioGroup mIndexGroup;
    private EditText mEditText;
    private int mIconSize = 60;
    private PagerAdapter mPagerAdapter;
    private int[] indexPoint;
    /**
     * 表情分页的结果集合
     */
    public List<List<Emojicon>> mPageEmojiList = new ArrayList<List<Emojicon>>();

    public EmoticonKeyboard(Context context) {
        this(context, null);
    }

    public EmoticonKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmoticonKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.include_emoji_panel, this);//映射
        initView(context);
        fillSingleEmoticon(EmoticonSet.EMOJI);
    }

    public void setupWithEditText(EditText editText) {
        setupWithEditText(editText, null);

    }

    public void setupWithEditText(EditText editText, Emojicon[] icons) {
        mEditText = editText;
        initInputFilter(mEditText);
        mIconSize = (int) editText.getTextSize();
        fillSingleEmoticon(icons);
    }

    private void initView(Context context) {
        mViewPager = (ViewPager) this.findViewById(R.id.mp_emoji_panel_viewpager);
        mIndexGroup = (RadioGroup) this.findViewById(R.id.mp_emoji_panel_index);
        mViewPager.setAdapter(mPagerAdapter = new PageSetAdapter(context, mPageEmojiList, this));
        mViewPager.addOnPageChangeListener(this);
    }




    /**
     * 初始化表情集
     */
    public void fillMutilEmoticon(List<EmojiSetEntity> l) {
        if (l == null || l.size() == 0) {
            return;
        }
        mPageEmojiList.clear();
        indexPoint=new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            EmojiSetEntity entity = l.get(i);
            Emojicon[] emojicons = entity.getEmojicons();
            List<Emojicon> list = Arrays.asList(emojicons);
            int pageCount = (int) Math.ceil(list.size() / 20 + 0.1);//每页20个
            indexPoint[i]=pageCount;
            for (int j = 0; j < pageCount; j++) {
                mPageEmojiList.add(getEachPageData(j, 20, list));
            }
        }
        mPagerAdapter.notifyDataSetChanged();
        mLastTabPosition=-1;
        updateIndexPosition(0);
    }

    /**
     * 初始化表情集
     */
    private void fillSingleEmoticon(Emojicon[] emojicons) {
        if (emojicons == null) {
            return;
        }
        List<Emojicon> list = Arrays.asList(emojicons);
        int pageCount = (int) Math.ceil(list.size() / 20 + 0.1);//每页20个
        indexPoint=new int[]{pageCount};
        mPageEmojiList.clear();
        for (int i = 0; i < pageCount; i++) {
            mPageEmojiList.add(getEachPageData(i, 20, list));
        }
        mPagerAdapter.notifyDataSetChanged();
         mLastTabPosition=-1;
         updateIndexPosition(0);
    }

    /**
     * 获取分页数据
     *
     * @param page
     * @return
     */
    private List<Emojicon> getEachPageData(int page, int pageSize, List<Emojicon> iconList) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;
        if (endIndex > iconList.size()) {
            endIndex = iconList.size();
        }
        List<Emojicon> list = new ArrayList<Emojicon>();
        list.addAll(iconList.subList(startIndex, endIndex));
        if (list.size() < pageSize) {
            for (int i = list.size(); i < pageSize; i++) {
                Emojicon object = Emojicon.createPlaceHolder();
                list.add(object);
            }
        }
        if (list.size() == pageSize) {
            Emojicon object = Emojicon.fromResource(R.mipmap.delete, "[删除]");
            list.add(object);
        }
        return list;
    }



    private void updateIndexPosition(int position){
        int tempPostion=0;
        for(int i=0;i<indexPoint.length;i++){
            tempPostion+=indexPoint[i];
            if(position<=tempPostion-1){
                int pointPostion= indexPoint[i]-(tempPostion-position);
                updateIndexView(getContext(),i,pointPostion);
                break;
            }
        }

    };

    public void updateTabPosition(int tabPosition){
        if(mLastTabPosition==tabPosition){
          return;
        }
        int tempPosition=0;
        for(int i=0;i<indexPoint.length;i++){
            if(tabPosition==i){
                mViewPager.setCurrentItem(tempPosition);
                break;
            }else {
                tempPosition+=indexPoint[i];
            }
        }
    }

    private int mLastTabPosition=-1;
    private void updateIndexView(Context context,int tabPosition,int pointPosition) {
        if(mLastTabPosition!=tabPosition){
            if(mTabChangeLisenter!=null&&mLastTabPosition!=-1){
                mTabChangeLisenter.onTabChange(tabPosition);
            }
            RadioButton indexView;
            mIndexGroup.removeAllViews();
            for (int i = 0; i < indexPoint[tabPosition]; i++) {
                indexView = new RadioButton(context);
                indexView.setButtonDrawable(R.drawable.selector_index_view);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 10;
                layoutParams.rightMargin = 10;
                mIndexGroup.addView(indexView, layoutParams);
            }
            mLastTabPosition=tabPosition;
        }
        ((RadioButton) mIndexGroup.getChildAt(pointPosition)).setChecked(true);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         updateIndexPosition(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private OnTabChangeLisenter mTabChangeLisenter;
    public interface OnTabChangeLisenter{
        void onTabChange(int tabPositon);
    }
    public void setOnTabChangeLisenter(OnTabChangeLisenter l){
        mTabChangeLisenter=l;
    }


    /**
     * 删除功能
     *
     * @param editText
     */
    public static void backspace(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//输入表情
        if (mEditText == null) {
            return;
        }
        Emojicon emojicon = (Emojicon) parent.getItemAtPosition(position);
        if (emojicon.getIcon() == 0) {
            return;
        }
        if (emojicon.getEmoji().equals("[删除]")) {
            backspace(mEditText);
            return;
        }
        int index = mEditText.getSelectionStart();
        Editable editable = mEditText.getText();
        editable.insert(index, emojicon.getEmoji());
    }


    /**
     * 过滤器，保证等转换表情
     *
     * @param editText
     */
    private void initInputFilter(final EditText editText) {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (TextUtils.isEmpty(source))
                    return null;
                return EmojiconHandler.handleAllEmoticon(getContext(), new SpannableString(source), mIconSize);

            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}

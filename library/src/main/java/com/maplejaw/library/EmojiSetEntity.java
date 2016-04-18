package com.maplejaw.library;

import com.maplejaw.library.bean.Emojicon;

/**
 * @author maplejaw
 * @version 1.0, 2016/4/14
 */
public class EmojiSetEntity {
    private String index;
    private Emojicon[] mEmojicons;

    public EmojiSetEntity(String index, Emojicon[] emojicons) {
        this.index = index;
        mEmojicons = emojicons;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Emojicon[] getEmojicons() {
        return mEmojicons;
    }

    public void setEmojicons(Emojicon[] emojicons) {
        mEmojicons = emojicons;
    }
}

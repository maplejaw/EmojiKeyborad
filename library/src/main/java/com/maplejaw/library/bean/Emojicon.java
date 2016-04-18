/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maplejaw.library.bean;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public class Emojicon {

    private int icon;//emoji对应的图片资源
    private String emoji; //emoji对应的转化过后的string，或者自定义的string



    public Emojicon(int icon, String emoji) {
        this.icon = icon;
        this.emoji = emoji;
    }


    private Emojicon() {
    }


    /**
     * 创建一个占位对象,用于填充空白处
     * @return Emojicon
     */
    public static Emojicon createPlaceHolder() {
        Emojicon emoji = new Emojicon();
        emoji.icon = 0;
        emoji.emoji="";
        return emoji;
    }



    /**
     * 实现自定义表情
     * @param icon 资源id
     * @param value 对应的值
     * @return Emojicon
     */
    public static Emojicon fromResource(int icon, String value) {
        Emojicon emoji = new Emojicon();
        emoji.icon = icon;
        emoji.emoji = value;
        return emoji;
    }


    /**
     * 实现unicode转表情，用于emoji表情
     * @param icon 资源id
     * @param codePoint 对应的unicode码
     * @return Emojicon
     */
    public static Emojicon fromCodePoint(int icon,int codePoint) {
        Emojicon emoji = new Emojicon();
        emoji.icon=icon;
        emoji.emoji = newString(codePoint);
        return emoji;
    }


    /**
     * 实现char转表情，用于emoji
     * @param icon 资源id
     * @param ch 对应的unicode码
     * @return Emojicon
     */
    public static Emojicon fromChar(int icon,char ch) {
        Emojicon emoji = new Emojicon();
        emoji.icon=icon;
        emoji.emoji = Character.toString(ch);
        return emoji;
    }


    public static  String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }


    public int getIcon() {
        return icon;
    }

    public String getEmoji() {
        return emoji;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Emojicon && emoji.equals(((Emojicon) o).emoji);
    }

    @Override
    public int hashCode() {
        return emoji.hashCode();
    }

}

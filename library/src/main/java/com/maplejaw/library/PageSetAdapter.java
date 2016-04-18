package com.maplejaw.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maplejaw.library.bean.Emojicon;

import java.util.List;

/**
 * 表情集
 */
public class PageSetAdapter extends PagerAdapter {

	private List<List<Emojicon>> mPageEmojiList;
	private Context mContext;
	private AdapterView.OnItemClickListener mOnItemClickListener;

	public PageSetAdapter(Context context, List<List<Emojicon>> list, AdapterView.OnItemClickListener l) {
		super();
		this.mContext=context;
		this.mPageEmojiList = list;
		this.mOnItemClickListener=l;

	}

	@Override
	public int getCount() {
		return mPageEmojiList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}



	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MeasureGridView view = new MeasureGridView(mContext);
		EmojiAdapter adapter = new EmojiAdapter(mContext, mPageEmojiList.get(position));
		view.setAdapter(adapter);
		view.setOnItemClickListener(mOnItemClickListener);
		view.setNumColumns(7);
		view.setBackgroundColor(Color.TRANSPARENT);
		view.setHorizontalSpacing(1);
		view.setVerticalSpacing(1);
		view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		view.setCacheColorHint(0);
		view.setPadding(5, 0, 5, 0);
		view.setSelector(new ColorDrawable(Color.TRANSPARENT));
		view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.CENTER);
		view.setVerticalScrollBarEnabled(false);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}


	/**
	 * 表情填充器
	 */
	static class EmojiAdapter extends BaseAdapter {

		private List<Emojicon> data;
		private LayoutInflater inflater;
		public EmojiAdapter(Context context, List<Emojicon> list) {
			this.inflater= LayoutInflater.from(context);
			this.data=list;
		}

		@Override
		public int getCount() {
			return this.data.size();
		}

		@Override
		public Emojicon getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Emojicon emoji=getItem(position);
			ViewHolder viewHolder;
			if(convertView == null) {
				viewHolder=new ViewHolder();
				convertView=inflater.inflate(R.layout.emoji_item, parent,false);
				viewHolder.emoji=(ImageView)convertView.findViewById(R.id.mp_emoji_icon);
				convertView.setTag(viewHolder);
			} else {
				viewHolder=(ViewHolder)convertView.getTag();
			}
			viewHolder.emoji.setImageResource(emoji.getIcon());//表情图像
			return convertView;
		}

		class ViewHolder {
			public ImageView emoji;
		}
	}



}
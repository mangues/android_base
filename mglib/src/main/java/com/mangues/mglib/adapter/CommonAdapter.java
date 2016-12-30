package com.mangues.mglib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 作者：mangues on 16/3/30 10:27
 * 邮箱：mangues@yeah.net
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected List<T> mDatas;
	protected Context mContext;
	protected int layoutid;
	public CommonAdapter(Context context, List<T> datas, int layoutid){
		this.mDatas = datas;
		this.mContext = context;
		this.layoutid = layoutid;
	}
	
	public CommonAdapter(Context context, List<T> datas){
		this.mDatas = datas;
		this.mContext = context;
		
	}
	
	public void add(T e) {
		this.mDatas.add(e);
	}
	
	public void addAll(List<T> list) {
		this.mDatas.addAll(list);
	}
	
	public void setAll(List<T> list) {
		this.mDatas = list;
	}
	

	public void remove(int position) {
		this.mDatas.remove(position);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder viewHolder = null;
		if (getLayout(getItem(position),position)!=0) {
			this.layoutid = getLayout(getItem(position),position);
		}
		viewHolder = ViewHolder.get(mContext, layoutid, parent, position, convertView);
		this.postion = position;
		convent(viewHolder,getItem(position),viewHolder.getConvertView());
		return viewHolder.getConvertView();
	}

	private int postion;
	public int getPosition() {
		return postion;

	}

	public abstract void convent(ViewHolder viewHolder, T item, View convertView);

	//设置adapter的xml样式id
	public abstract int getLayout(T item, int position);
}

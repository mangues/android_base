package com.mangues.lifecircleapp.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：mangues on 16/3/30 10:27
 * 邮箱：mangues@yeah.net
 */
public class ViewHolder {

	private int poision;
	private SparseArray<View> mView;
	private View convertView ;
	public ViewHolder(Context context, int layoutid, ViewGroup parent,
					  int position) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		this.poision = position;
		this.mView = new SparseArray<View>();
		convertView = mInflater.inflate(layoutid, parent, false);
		convertView.setTag(layoutid, this);
		
		
	}

	public static ViewHolder get(Context context, int layoutid,
								 ViewGroup parent, int position, View convertView) {
			if (convertView == null) {
				return new ViewHolder(context, layoutid, parent, position);
			} else {
				ViewHolder viewHolder = (ViewHolder) convertView.getTag(layoutid);
				viewHolder.poision = position;
				return viewHolder;
			}
	}
	public View getConvertView() {
		return convertView;
	}
	
	public <T extends View> T getView(int viewId){
		View view = mView.get(viewId);
		if(view == null){
			view = convertView.findViewById(viewId);
			mView.put(viewId, view);
		}
		return (T)view;
		
	}

}

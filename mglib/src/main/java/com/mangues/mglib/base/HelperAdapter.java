
/*
 * Copyright 2016. chenshufei
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

package com.mangues.mglib.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * 通用Adapter类，使用示例如下：
 * new Helper(xxxxActivity.this,xxxxLayoutId,xxxxDatas){
 *     public void inflateViewData(ViewHolder viewHolder, int position){
 *         viewHolder.setTextView(xxxTextViewId,"xxx文本值");
 *         viewHolder.setButton(xxxButtonId,"xxxx按钮显示文字")
 *     }
 * };
 * <br /> author: chenshufei
 * <br /> date: 15/8/17
 * <br /> email: chenshufei2@sina.com
 */
public abstract class HelperAdapter<T> extends BaseAdapter {

    protected List<T> datas;
    protected Context mContext;
    private int layoutId;
    protected ListView mListView;

    public HelperAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    public HelperAdapter(Context context, int layoutId, T[] datas){
        this(context,layoutId, Arrays.asList(datas));
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setListView(ListView view){
        mListView = view;
    }

    public void setDatas(T[] datas){
        setDatas(Arrays.asList(datas));
    }

    @Override
    public int getCount() {
        return datas == null? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, layoutId, position, convertView, parent);
        inflateViewData(viewHolder, position);
        return viewHolder.getContentView();
    }

    /**
     * 从ViewHolder中获取控件，并斌相应的值
     * @param viewHolder
     * @param position
     */
    protected abstract void inflateViewData(ViewHolder viewHolder, int position);

}
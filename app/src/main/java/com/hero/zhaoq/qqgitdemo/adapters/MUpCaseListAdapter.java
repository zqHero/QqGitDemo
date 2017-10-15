package com.hero.zhaoq.qqgitdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.utils.CollectionsUtils;
import com.hero.zhaoq.qqgitdemo.utils.DimensUtils;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 17:39
 * zhaoqiang:zhaoq_hero@163.com
 */

public class MUpCaseListAdapter extends BaseAdapter {

    private final Context context;
    private String[] data = null;
    public MUpCaseListAdapter(Context mContext) {
        this.context = mContext;
        data = context.getResources().getStringArray(R.array.up_case_array);
    }
    @Override
    public int getCount() {
        return data.length;
    }
    @Override
    public Object getItem(int position) {
        return data[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.text_view,null,false);
        TextView itemView = (TextView) convertView.findViewById(R.id.up_case_txt);
        itemView.setEnabled(false);
        itemView.setText(data[position]);
        //重置  高度：  item  高度为屏幕  的0.6  / 27 个字母
        convertView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ((DimensUtils.getScreenSize(context)[1] * 0.7) / 27)));
        return convertView;
    }

    public int getListIndectorPosition(String upCase) {
        return CollectionsUtils.Strs2AList(data).indexOf(upCase);
    }
}

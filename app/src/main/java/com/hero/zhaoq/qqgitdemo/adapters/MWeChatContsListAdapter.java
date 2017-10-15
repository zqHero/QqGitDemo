package com.hero.zhaoq.qqgitdemo.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.domain.Contact;
import com.hero.zhaoq.qqgitdemo.utils.CollectionsUtils;

import java.util.List;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 17:49
 * zhaoqiang:zhaoq_hero@163.com
 */
public class MWeChatContsListAdapter extends RecyclerView.Adapter<MWeChatContsListAdapter.ViewHolder> {

    private List<Contact> data;
    private Context context;

    public MWeChatContsListAdapter(Context context, @Nullable List<Contact> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_conts_and_dept_list, null, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MWeChatContsListAdapter.ViewHolder holder, final int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, data.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setNewData(List<Contact> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<Contact> getData() {
        return this.data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, section;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            section = (TextView) itemView.findViewById(R.id.tv_section);
        }

        //---
        public void setData(int position) {
            name.setText(data.get(position).getName());
            if (position == getPositionForSection(data.get(position).getPinyinElement().getPinyin().substring(0, 1).toUpperCase().charAt(0))) {
                section.setVisibility(View.VISIBLE);
                section.setText(data.get(position).getPinyinElement().pinyin.substring(0, 1).toUpperCase().charAt(0) + "");
            } else {
                section.setVisibility(View.GONE);
            }
        }
    }

    public int getPositionForSection(char section) {
        for (int index = 0; index < data.size(); index++) {
            Contact contact = data.get(index);
            if (section == contact.getPinyinElement().pinyin.substring(0, 1).toUpperCase().charAt(0)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 根据  大写字母 判断    recycleView 滑动位置：
     */
    public int getRecyListPosition(String upCase) {
        if (CollectionsUtils.isNoEmpty(this.data))
            for (int index = 0; index < this.data.size(); index++) {
                Contact userEntity = this.data.get(index);
                if (upCase.equals(userEntity.getPinyinElement().pinyin.substring(0, 1).toUpperCase().charAt(0) + "")) {
                    return index;
                }
            }
        return -1;
    }

    public void onDestory() {
        this.data = null;
    }
}

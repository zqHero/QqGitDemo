package com.hero.zhaoq.qqgitdemo.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.domain.Contact;
import com.hero.zhaoq.qqgitdemo.utils.CollectionsUtils;

import java.util.HashMap;
import java.util.List;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 15:45
 * zhaoqiang:zhaoq_hero@163.com
 */

public class MQQADapter extends RecyclerView.Adapter<MQQADapter.ViewHolder> {

    private HashMap<String, List<Contact>> dataMaps;

    private List<String> keys;
    private List<List<Contact>> values;

    private Context context;

    public MQQADapter(Context context, @Nullable HashMap<String, List<Contact>> dataMaps) {
        this.context = context;
        this.dataMaps = dataMaps;
        if (dataMaps != null) {
            keys = CollectionsUtils.getKeysListFromMap(dataMaps);
            values = CollectionsUtils.getValueListFromMap(dataMaps);
        }
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_depatment_list, null, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    public void setNewData(HashMap<String, List<Contact>> departmentEntityList) {
        this.dataMaps = departmentEntityList;
        if (dataMaps != null) {
            keys = CollectionsUtils.getKeysListFromMap(dataMaps);
            values = CollectionsUtils.getValueListFromMap(dataMaps);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, count;
        private ImageView arrowImg;
        private LinearLayout list_view;
        private View titleLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.parent_title);
            count = (TextView) itemView.findViewById(R.id.num_count);
            arrowImg = (ImageView) itemView.findViewById(R.id.arrow);
            list_view = (LinearLayout) itemView.findViewById(R.id.list_view);
            titleLayout = itemView.findViewById(R.id.title_layout);
        }

        public void setData(int position) {
            //绑定数据
            title.setText(keys.get(position));//部门名称
            count.setText("(" + values.get(position).size() + ")人");
            initListView(list_view, values.get(position));

            titleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isopen = titleLayout.getTag() == null ? false : (boolean) titleLayout.getTag();
                    list_view.setVisibility(isopen ? View.GONE : View.VISIBLE);
                    arrowImg.setImageResource(!isopen ? R.mipmap.icon_arrow_down : R.mipmap.icon_arrow_left);
                    titleLayout.setTag(!isopen);
                }
            });
        }
    }

    //初始化  内部 listView
    private void initListView(LinearLayout list_view, List<Contact> userEntities) {
        if (list_view != null)
            list_view.removeAllViews();
        //循环  绑定数据
        for (final Contact item : userEntities) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_conts_and_dept_list, null, false);
            //绑定数据：
            ((TextView) itemView.findViewById(R.id.name)).setText(item.getName());
            (itemView.findViewById(R.id.tv_section)).setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //打开  联系人详情界面：
                    Toast.makeText(context, "点击了Item:" + item.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            list_view.addView(itemView);
        }
    }

    public void onDestory() {
        dataMaps = null;
        keys = null;
        values = null;
    }
}

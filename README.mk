
QQ聊天联系人界面效果仿写：

###1，先上效果图  ，因为未找到  比较好用的  gif录制工具 。所以上的都是静态图片：

![这里写图片描述](http://img.blog.csdn.net/20171015145147728?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20171015145210681?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20171015145224100?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



### 2，代码：

 主界面:  两个  Fragment交互：

```
class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup group;

    private Fragment[] fragments;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        group = (RadioGroup) findViewById(R.id.group);
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragments = new Fragment[2];
            fragments[0] = QQContactsFragment.newInstance();
            fragments[1] = WeChatContactsFragment.newInstance();
            FragmentTransaction tx = fm.beginTransaction();
            for (int i = 0; i < fragments.length; i++) {
                tx.add(R.id.container, fragments[i], "fragment" + i);
            }
            tx.commit();
        } else {
            for (int i = 0; i < fragments.length; i++) {
                fragments[i] = fm.findFragmentByTag("fragment" + i);
            }
        }
        group.setOnCheckedChangeListener(this);
        group.check(R.id.QQDemo);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.QQDemo:
                index = 0;
                break;
            case R.id.weChatDemo:
                index = 1;
                break;
            default:
                break;
        }

        FragmentTransaction tx = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (index == i){
                tx.show(fragments[i]);
            }else{
                tx.hide(fragments[i]);
            }
        }
        tx.commit();
    }
}

```

QQ 联系人界面适配器：比较简单：直接reCycle 适配    设置分组数据显示和不显示：

```

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

```
微信界面适配器：

```
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

```

侧边    字母索引适配器：  侧边索引  以及侧边索引通过   touchEvent 事件判断 并完成界面dialog的显示以及消失：


```
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
        //重置  高度：  item  高度为屏幕  的0.7  / 27 个字母
        convertView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ((DimensUtils.getScreenSize(context)[1] * 0.7) / 27)));
        return convertView;
    }

    public int getListIndectorPosition(String upCase) {
        return CollectionsUtils.Strs2AList(data).indexOf(upCase);
    }
}

```


检测  侧边索引的事件：

侧边索引其实是个listView，通过listView的  onTouchEvent 事件  判断   是否显示中间字母的  dialog。    ：

```
 listIndictor.setOnTouchListener(new View.OnTouchListener() {
            float itemH;
            float listHeightY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        listIndictor.setBackgroundColor(getResources().getColor(R.color.line_strong_divider));
                        listHeightY = listIndictor.getMeasuredHeight();
                        itemH = listHeightY / 27;

                        if (caseIndexDialog == null) {
                            caseIndexDialog = new CaseIndexDialog(getContext());
                        }
                        caseIndexDialog.show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() < listHeightY && event.getX() >= 0 && event.getY() > 0) {
                            //在移动范围内  计算  当前   点击listView 的位置：
                            int index = (int) (event.getY() / itemH);
                            if (lasView != null)
                                lasView.setEnabled(false);
                            curView = listIndictor.getChildAt(index).findViewById(R.id.up_case_txt);
                            curView.setEnabled(true);
                            lasView = curView;
                            //通知RecycleView  更新 实现联动：
                            String upCase = ((TextView) curView).getText().toString();
                            //固定滚动位置：
                            if (caseListAdapter.getListIndectorPosition(upCase) != -1)
                                ((LinearLayoutManager) mRecycleView.getLayoutManager())
                                        .scrollToPositionWithOffset(contsListAdapter.getRecyListPosition(upCase), 0);
                            caseIndexDialog.setUpCase(upCase);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        listIndictor.setBackgroundColor(getResources().getColor(R.color.translance));
                        curView.setEnabled(false);
                        caseIndexDialog.dismiss();
                        break;
                }
                return true;
            }
        });
```

Maybe  你更需要下面的   地址：

csdn:

github:https://github.com/229457269/QqGitDemo



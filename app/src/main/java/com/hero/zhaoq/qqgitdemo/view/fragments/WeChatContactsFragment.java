package com.hero.zhaoq.qqgitdemo.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.VirtualData;
import com.hero.zhaoq.qqgitdemo.adapters.MWeChatContsListAdapter;
import com.hero.zhaoq.qqgitdemo.adapters.MUpCaseListAdapter;
import com.hero.zhaoq.qqgitdemo.domain.Contact;
import com.hero.zhaoq.qqgitdemo.view.widget.CaseIndexDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeChatContactsFragment extends Fragment implements OnRefreshListener {


    private RecyclerView mRecycleView;
    private CaseIndexDialog caseIndexDialog;

    public WeChatContactsFragment() {
    }

    public static Fragment newInstance() {
        return new WeChatContactsFragment();
    }

    private MWeChatContsListAdapter contsListAdapter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private TextView tvItemTitle;
    private ListView listIndictor;

    private MUpCaseListAdapter caseListAdapter;
    public int mCurrentPosition;
    private View lasView, curView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_we_chat_contacts, container, false);
        swipeToLoadLayout = (SwipeToLoadLayout) rootView.findViewById(R.id.swipeToLoadLayout);
        tvItemTitle = (TextView) rootView.findViewById(R.id.tv_item_title);

        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setOnRefreshListener(this);

        mRecycleView = (RecyclerView) rootView.findViewById(R.id.swipe_target);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        contsListAdapter = new MWeChatContsListAdapter(getContext(), new ArrayList<Contact>());
        mRecycleView.setAdapter(contsListAdapter);

        listIndictor = (ListView) rootView.findViewById(R.id.list_indictor);
        caseListAdapter = new MUpCaseListAdapter(getContext());
        listIndictor.setAdapter(caseListAdapter);
        listIndictor.setSelected(false);

        caseIndexDialog = new CaseIndexDialog(getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contsListAdapter.setNewData(VirtualData.getContactData());
        initListener();
    }

    private void initListener() {
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

        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (swipeToLoadLayout.isRefreshing()) swipeToLoadLayout.setRefreshing(false);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当滑动的时候获取当前滑动的下一个item的视图
                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    tvItemTitle.setVisibility(View.VISIBLE);
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (mCurrentPosition != 0) {
                        String upCase = contsListAdapter.getData().get(mCurrentPosition).getPinyinElement().getPinyin()
                                .substring(0, 1).toUpperCase().charAt(0) + "";
                        tvItemTitle.setText(upCase);
                    }
                }
                if (mCurrentPosition == 0) {
                    tvItemTitle.setVisibility(View.INVISIBLE);
                    return;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mRecycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                caseListAdapter.notifyDataSetChanged();
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 3000);
    }

}

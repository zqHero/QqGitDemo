package com.hero.zhaoq.qqgitdemo.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.VirtualData;
import com.hero.zhaoq.qqgitdemo.adapters.MQQADapter;
import com.hero.zhaoq.qqgitdemo.domain.Contact;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QQContactsFragment extends Fragment implements OnRefreshListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView listView;
    private MQQADapter adapter;

    public static Fragment newInstance() {
        return new QQContactsFragment();
    }

    public QQContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qqcontacts, container, false);
        swipeToLoadLayout = (SwipeToLoadLayout) rootView.findViewById(R.id.swipeToLoadLayout);
        listView = (RecyclerView) rootView.findViewById(R.id.swipe_target);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MQQADapter(getContext(), new HashMap<String, List<Contact>>());
        listView.setAdapter(adapter);

        swipeToLoadLayout.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setNewData(VirtualData.getData());
    }

    @Override
    public void onRefresh() {
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setNewData(VirtualData.getData());
                swipeToLoadLayout.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.onDestory();
        adapter = null;
    }

}

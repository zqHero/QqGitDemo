package com.hero.zhaoq.qqgitdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.hero.zhaoq.qqgitdemo.view.fragments.QQContactsFragment;
import com.hero.zhaoq.qqgitdemo.view.fragments.WeChatContactsFragment;

/**
 * qq  联系人  列表   Demo：
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

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

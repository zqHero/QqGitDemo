package com.hero.zhaoq.qqgitdemo.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hero.zhaoq.qqgitdemo.R;
import com.hero.zhaoq.qqgitdemo.domain.Contact;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 23:29
 * zhaoqiang:zhaoq_hero@163.com
 */

//字母 索引的  dialog:
public class CaseIndexDialog extends Dialog {

    private Context context;
    private TextView uncase;

    public CaseIndexDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        this.context = context;
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(context).inflate(R.layout.content_view, null, false);
        uncase = (TextView) rootView.findViewById(R.id.upcase_txt);
        setContentView(rootView);
    }

    public void setUpCase(String upCase) {
        uncase.setText(upCase);
    }

    @Override
    public void show() {
        super.show();
         /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        Window window = getWindow();
        WindowManager m = ((AppCompatActivity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.5); // 改变的是dialog框在屏幕中的位置而不是大小
        p.width = (int) (d.getWidth() * 0.25); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }


}

package com.hero.zhaoq.qqgitdemo.domain;

import android.text.TextUtils;

import com.hero.zhaoq.qqgitdemo.utils.PinYinUtil;

import java.io.Serializable;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 15:48
 * zhaoqiang:zhaoq_hero@163.com
 */

public class Contact implements Serializable {

    private String name;
    private String phone;

    private String pinyinName; //Not-null value..

    //所属  分组
    private String groupName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", Name='" + groupName + '\'' +
                ", pinyinElement='" + pinyinElement + '\'' +
                '}';
    }

    private PinYinUtil.PinYinElement pinyinElement = new PinYinUtil.PinYinElement();

    public PinYinUtil.PinYinElement getPinyinElement() {
        return pinyinElement;
    }

}

package com.hero.zhaoq.qqgitdemo;

import android.util.Log;
import android.util.SparseArray;

import com.hero.zhaoq.qqgitdemo.domain.Contact;
import com.hero.zhaoq.qqgitdemo.utils.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 14:57
 * zhaoqiang:zhaoq_hero@163.com
 */
//虚拟  数据类：
public class VirtualData {


    static String[] departMent = new String[]{"大学..", "初中", "xiaoXue", "laoXiang", "dasd123--", "美国Friends"};
    static String[] stringNames = new String[]{"张三", "李四", "王二", "麻子", "翠花", "店小二",
            "孙猴子", "牛魔王", "嫦娥", "猪八戒", "xxx", "[]dxcz", "0,injh", "12123", "demi3---"};
    static String[] phones = new String[]{"120807152", "123408998", "1827800902", "123334354545"};

    /**
     * 遍历  联系人  获取数据结构
     */
    public static HashMap<String, List<Contact>> getData() {

        HashMap<String, List<Contact>> map = new HashMap<>();

        SparseArray<Contact> contactSparseArray = new SparseArray<>();
        for (int i = 0; i < 100; i++) {
            Contact contact = new Contact();
            contact.setName(stringNames[(int) (Math.random() * stringNames.length)]);
            contact.setPhone(phones[(int) (Math.random() * phones.length)]);
            contact.setGroupName(departMent[(int) (Math.random() * departMent.length)]);
            //TODO   拼音注入：
            PinYinUtil.getPinYin(contact.getName(), contact.getPinyinElement());

            contactSparseArray.put(i, contact);
        }

        List<String> groupStrs = new ArrayList<>();
        for (int i = 0; i < contactSparseArray.size(); i++) {
            Contact contact = contactSparseArray.get(i);
            if (!groupStrs.contains(contact.getGroupName())) {
                groupStrs.add(contact.getGroupName());
            }
        }

        //添加 数据进去
        for (String groupName : groupStrs) {
            List<Contact> contactList = new ArrayList<>();
            for (int i = 0; i < contactSparseArray.size(); i++) {
                if (contactSparseArray.get(i).getGroupName() == groupName) {
                    contactList.add(contactSparseArray.get(i));
                }
            }
            map.put(groupName, contactList);
        }
        //进行   排序：
//        getContactMapSorted(map);
        return map;
    }

//    /**
//     * 对   map 排序：
//     */
//    private static HashMap<String, List<Contact>> getContactMapSorted(HashMap<String, List<Contact>> map) {
//        ArrayList<Map.Entry<String, List<Contact>>> datas = new ArrayList<>(map.entrySet());
//        Collections.sort(datas, new Comparator<Map.Entry<String, List<Contact>>>() {
//            @Override
//            public int compare(Map.Entry<String, List<Contact>> o1, Map.Entry<String, List<Contact>> o2) {
//                PinYinUtil.PinYinElement pinYinElement1 = new PinYinUtil.PinYinElement();
//                PinYinUtil.PinYinElement pinYinElement2 = new PinYinUtil.PinYinElement();
//
//                PinYinUtil.getPinYin(o1.getKey(), pinYinElement1);
//                PinYinUtil.getPinYin(o2.getKey(), pinYinElement2);
//
//                if (pinYinElement2.getPinyin().startsWith("#")) {
//                    return -1;
//                } else if (pinYinElement1.getPinyin().startsWith("#")) {
//                    return 1;
//                } else {
//                    return o1.getKey().compareToIgnoreCase(o2.getKey());
//                }
//            }
//        });
//        return map;
//    }

    //--------------------------
    public static List<Contact> getContactData() {
        ArrayList<Contact> contactSparseArray = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Contact contact = new Contact();
            contact.setName(stringNames[(int) (Math.random() * stringNames.length)] + "--=-=--==" + i);
            contact.setPhone(phones[(int) (Math.random() * phones.length)]);
            contact.setGroupName(departMent[(int) (Math.random() * departMent.length)]);

            //TODO   拼音注入：
            PinYinUtil.getPinYin(contact.getName(), contact.getPinyinElement());

            contactSparseArray.add(contact);
        }

        getContactSorted(contactSparseArray);
        return contactSparseArray;
    }

    /**
     * 根据   首字母进行排序
     *
     * @return
     */
    public static List<Contact> getContactSorted(List<Contact> users) {
        Collections.sort(users, new Comparator<Contact>() {
            @Override
            public int compare(Contact entity1, Contact entity2) {
                if (entity2.getPinyinElement().getPinyin().startsWith("#")) {
                    return -1;
                } else if (entity1.getPinyinElement().getPinyin().startsWith("#")) {
                    return 1;
                } else {
                    if (entity1.getPinyinElement().getPinyin() == null) {
                        PinYinUtil.getPinYin(entity1.getName(), entity1.getPinyinElement());
                    }
                    if (entity2.getPinyinElement().getPinyin() == null) {
                        PinYinUtil.getPinYin(entity2.getName(), entity2.getPinyinElement());
                    }
                    return entity1.getPinyinElement().getPinyin().compareToIgnoreCase(entity2.getPinyinElement().getPinyin());
                }
            }
        });
        return users;
    }
}

package com.example.administrator.myapplication.newspackage.bean;

import com.example.administrator.myapplication.common.base.BaseEntity;

/**
 * 国内新闻实体
 * Created by zhanghao on 2016/8/5.
 */
public class GuoNeiNewsBean extends BaseEntity {
    //    {
//        "ctime": "2016-08-05 04:25",
//            "title": "中国气象局:8月出现35度以上高温天气可能性很大",
//            "description": "网易国内",
//            "picUrl": "http://s.cimg.163.com/catchpic/1/19/195F6301DEADF8D8BBFC1A5DEB50E83A.jpg.119x83.jpg",
//            "url": "http://news.163.com/16/0805/04/BTM83F4000014AED.html#f=dlist"
//    }
    public String ctime;
    public String title;
    public String description;
    public String picUrl;
    public String url;

}

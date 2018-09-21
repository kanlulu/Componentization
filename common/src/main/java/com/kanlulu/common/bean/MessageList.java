package com.kanlulu.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:58
 */
public class MessageList {
    public List<Message> list;

    public class Message implements Serializable {
        public String type;//类型
        public String title;//标题
        public String content;//正文
        public long createtime;//时间
    }
}

package com.tmac.onsite.bean;

/**
 * Created by user on 16/10/10.
 */

public class MessageBean {

    private String msg_content;
    private String msg_num;
    private String msg_date;

    public MessageBean(String msg_content, String msg_num, String msg_date) {
        this.msg_content = msg_content;
        this.msg_num = msg_num;
        this.msg_date = msg_date;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_num() {
        return msg_num;
    }

    public void setMsg_num(String msg_num) {
        this.msg_num = msg_num;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "msg_content='" + msg_content + '\'' +
                ", msg_num='" + msg_num + '\'' +
                ", msg_date='" + msg_date + '\'' +
                '}';
    }
}

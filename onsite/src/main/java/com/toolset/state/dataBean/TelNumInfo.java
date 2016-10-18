package com.toolset.state.dataBean;


import com.toolset.dataManager.dataManagerdataBase;

/**
 * Created by pactera on 2016/10/13.
 */
public class TelNumInfo extends dataManagerdataBase {
    private String tel;
    private String passWord;
    private String imei;
    private String imsi;

    public TelNumInfo(String tel, String imei, String imsi){
        this.tel = tel;
        this.imei = imei;
        this.imsi = imsi;
    }

    public TelNumInfo(String tel, String passWord, String imei, String imsi){
        this.tel = tel;
        this.passWord = passWord;
        this.imei = imei;
        this.imsi = imsi;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

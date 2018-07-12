package com.liaobao.Util.Bomb;



import cn.bmob.v3.BmobObject;

/**
 * @author: zhangjinpeng
 * @Time: 2017/7/19 12 19
 * 用户信息表
 */
public class UserConfig extends BmobObject {
    private String backrul="";//背景图地址
    private String Birthday="";//生日
    private String IconUrl="";//头像地址
    private String Nickname="";//昵称
    private int Sex;//性别 0=女,1=男
    private int style;//样式设置
    private String Userid;//用户主键

    public String Phonenumer;
    public String password;

    public UserConfig(){
    }

    public String getBackrul() {
        return backrul;
    }

    public void setBackrul(String backrul) {
        this.backrul = backrul;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }
}

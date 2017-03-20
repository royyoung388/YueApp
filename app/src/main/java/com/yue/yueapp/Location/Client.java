package com.yue.yueapp.Location;
/**
 * Created by TTR on 2017/3/11 0011.
 */
import com.tencent.map.geolocation.TencentLocation;

import java.util.*;
public class Client {

    private String username;//用户名字
    private String name;//真实名字
    private String password;//密码
    private int ID;//标识符
    private int age;//年龄
    private int sex;//性别 0为男 1为女
    private String major;//专业
    private ArrayList<String> likes;//喜好
    private TencentLocation pos;
    public Client(String usn, String pass) {//构造函数
        username = usn;
        password = pass;
        likes = new ArrayList<>();
    }
    public Client() {//空构造函数
        likes = new ArrayList<>();
    }
    //Get Set函数封装
    public void setPos(TencentLocation pos1) {
        pos = pos1;
    }
    public void setSex(int sx) { this.sex = sx;}
    public void setUsername(String username1) {
        this.username = username1;
    }
    public void setName(String namestr) {
        name = namestr;
    }
    public void setPassword(String pass) {
        password = pass;
    }
    public void setID(int id) {
        ID = id;
    }
    public void setAge(int age1) {
        age = age1;
    }
    public void setMajor(String major1) {
        major = major1;
    }
    //设置喜好列表，一般用于第一次注册或者设置
    public void setLikes(ArrayList<String> list) {
        this.likes = list;
    }
    //后续添加的喜好
    public void addLikes(String str) {
        if (!likes.contains(str))
            likes.add(str);
    }
    //删除的喜好 通过名字
    public void deleteLikes(String str) {
        likes.remove(str);
    }
    //删除的喜好 通过下标
    public void deleteLikes(int index) {
        likes.remove(index);
    }
    public ArrayList<String> getLikes() {
        return this.likes;
    }
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getMajor() {
        return this.major;
    }
    public TencentLocation getPos() { return this.pos;}
    public int getSex() { return this.sex;}
    public int ID() {
        return this.ID;
    }
    public int getAge() {
        return this.age;
    }

    public String ListString() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < likes.size(); i++) {
            sb.append(likes.get(i));
            if (i < (likes.size() - 1))
                sb.append("#");
        }
        return sb.toString();
    }
    public String toString() {
        return username + '$' + password + '$' + name + '$' + age + '$' + sex + '$' + major + '$' + ListString();
    }
    //使用Client.parseClient(String)即可转换
    public static Client parseClient(String clstr) {
        Client ret = new Client();
        String[] str = clstr.split("$");
        ret.username = str[0];
        ret.password = str[1];
        ret.name = str[2];
        ret.age = Integer.parseInt(str[3]);
        ret.sex = Integer.parseInt(str[4]);
        ret.major = str[5];
        String[] likeString = str[6].split("#");
        for (String s:likeString)
            ret.likes.add(s);
        return ret;
    }
}

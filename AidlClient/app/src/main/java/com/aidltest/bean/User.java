package com.aidltest.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: yangjh
 * @createDate: 2019/3/13 19:58
 * @description:
 */
public class User implements Parcelable {
    private String name;
    private int age;
    private String id;
    private String imgPath;

    public User(String name, int age, String id, String imgPath) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.imgPath = imgPath;
    }

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        age = in.readInt();
        id = in.readString();
        imgPath = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.id);
        dest.writeString(this.imgPath);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
        id = in.readString();
        imgPath = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}


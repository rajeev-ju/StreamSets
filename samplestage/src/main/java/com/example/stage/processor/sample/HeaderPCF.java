package com.example.stage.processor.sample;

import com.streamsets.pipeline.api.Field;
import java.util.ArrayList;
import java.util.List;

public class HeaderPCF {
    private String name;
    private int age;
    private String father;
    private String friend;
    private int friend_age;
    private String add;

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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getFriend_age() {
        return friend_age;
    }

    public void setFriend_age(int friend_age) {
        this.friend_age = friend_age;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public List<Field> generateFields(){
        List<Field> fieldList = new ArrayList<>();
        fieldList.add(Field.create(this.name));
        fieldList.add(Field.create(this.age));
        fieldList.add(Field.create(this.father));
        fieldList.add(Field.create(this.friend));
        fieldList.add(Field.create(this.friend_age));
        fieldList.add(Field.create(this.add));
        return fieldList;
    }

    @Override
    public String toString() {
        return "HeaderPCF{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", father='" + father + '\'' +
                ", friend='" + friend + '\'' +
                ", friend_age='" + friend_age + '\'' +
                ", add='" + add + '\'' +
                '}';
    }
}

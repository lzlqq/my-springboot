package com.leo.springboot.normal;

import org.bson.types.ObjectId;

public class Person{

    private ObjectId id;

    private String name;

    private int age;

    private Address address;

    public Person(){
        super();
    }

    public Person(ObjectId id, String name, int age, Address address){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public ObjectId getId(){
        return id;
    }

    public void setId(ObjectId id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
    }

}
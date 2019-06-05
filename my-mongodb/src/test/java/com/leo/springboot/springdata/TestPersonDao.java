package com.leo.springboot.springdata;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leo.springboot.normal.Address;
import com.leo.springboot.normal.Person;
import com.leo.springboot.springdata.dao.PersonDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPersonDao{

    @Autowired
    private PersonDao personDao;

    @Test
    public void testSave(){
        Person person = new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666"));
        this.personDao.save(person);
    }

    @Test
    public void testQuery(){
        List<Person> personList = this.personDao.queryPersonListByName("张三");
        for (Person person : personList){
            System.out.println(person);
        }
    }

    @Test
    public void testQuery2(){
        List<Person> personList = this.personDao.queryPersonPageList(2, 2);
        for (Person person : personList){
            System.out.println(person);
        }
    }

    @Test
    public void testUpdate(){
        Person person = new Person();
        person.setId(new ObjectId("5c0956ce235e192520086736"));
        person.setAge(30);
        System.out.println(this.personDao.updateById(person));
    }

    @Test
    public void testDelete(){
        Person person = new Person();
        person.setId(new ObjectId("5c09ca05235e192d8887a389"));
        System.out.println(this.personDao.deleteById(person));
    }

}

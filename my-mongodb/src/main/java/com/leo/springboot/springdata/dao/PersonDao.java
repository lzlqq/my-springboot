package com.leo.springboot.springdata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.leo.springboot.normal.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class PersonDao{

    @Autowired
    MongoTemplate mongoTemplate;

    public Person save(Person person){
        return this.mongoTemplate.save(person);
    }

    public List<Person> queryPersonListByName(String name){
        Query query = Query.query(Criteria.where("name").is(name));
        return this.mongoTemplate.find(query, Person.class);
    }

    public List<Person> queryPersonPageList(Integer pageNo,Integer pageSize){
        Query query = new Query().limit(pageSize).skip((pageNo - 1) * pageSize);
        return this.mongoTemplate.find(query, Person.class);
    }

    public UpdateResult updateById(Person person){
        Query query = Query.query(Criteria.where("id").is(person.getId()));
        Update update = Update.update("age", person.getAge());
        return this.mongoTemplate.updateFirst(query, update, Person.class);
    }

    public DeleteResult deleteById(Person person){
        Query query = Query.query(Criteria.where("id").is(person.getId()));
        return this.mongoTemplate.remove(query, Person.class);
    }
}

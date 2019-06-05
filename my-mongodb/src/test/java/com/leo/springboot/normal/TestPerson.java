package com.leo.springboot.normal;

import static com.mongodb.client.model.Filters.eq;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


public class TestPerson {

	MongoCollection<Person> personCollection;

	@Before
	public void init() {
		// 定义对象的解码注册器
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		// 建立连接
		MongoClient mongoClient = MongoClients.create("mongodb://192.168.232.128:27017");
		// 选择数据库 并且 注册解码器
		MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb").withCodecRegistry(pojoCodecRegistry);
		// 选择表
		this.personCollection = mongoDatabase.getCollection("person", Person.class);
	}

	@Test
	public void testInsert() {
		Person person = new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666"));
		this.personCollection.insertOne(person);
		System.out.println("插入数据成功");
	}

	@Test
	public void testInserts() {
		List<? extends Person> documents = Arrays.asList(
				new Person(ObjectId.get(), "张三", 20, new Address("人民路", "上海市", "666666")),
				new Person(ObjectId.get(), "李四", 21, new Address("北京西路", "上海市", "666666")),
				new Person(ObjectId.get(), "王五", 22, new Address("南京东路", "上海市", "666666")),
				new Person(ObjectId.get(), "赵六", 23, new Address("陕西南路", "上海市", "666666")),
				new Person(ObjectId.get(), "孙七", 24, new Address("南京西路", "上海市", "666666")));
		this.personCollection.insertMany(documents);
		System.out.println("插入数据成功");
	}

	@Test
	public void testQuery() {
		/*
		 * this.personCollection.find(Filters.eq("name", "张三")).forEach((Consumer<?
		 * super Person>) person -> { System.out.println(person); });
		 */
		
        this.personCollection.find(eq("name", "张三"))
        .forEach((Consumer<? super Person>) person -> {
            System.out.println(person);
        });
	}

	@Test
	public void testUpdate() {
		UpdateResult updateResult = this.personCollection.updateMany(Filters.eq("name",  "张三"), Updates.set("age", 22));
		System.out.println(updateResult);
	}

	@Test
	public void testDelete() {
		  DeleteResult deleteResult =
	                this.personCollection.deleteMany(Filters.eq("name", "张三"));
	        System.out.println(deleteResult);
	}
}

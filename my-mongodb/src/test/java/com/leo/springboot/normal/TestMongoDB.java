package com.leo.springboot.normal;

import java.util.function.Consumer;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class TestMongoDB {

	MongoCollection<Document> collection;

	@Before
	public void init() {
		// 建立连接
		MongoClient mongoClient = MongoClients.create("mongodb://192.168.232.128:27017");

		// 选择数据库
		MongoDatabase database = mongoClient.getDatabase("testdb");

		// 选择表
		collection = database.getCollection("user");
	}

	// 查询age<=50并且id>=100的用户信息，并且按照id倒序排序，只返回id，age字段，不返回_id字段
	@Test
	public void testQuery() {
		collection.find(
				Filters.and(
						Filters.lte("age", 50), 
						Filters.gte("id", 100))
		).
		sort(Sorts.descending("id")).
		projection(Projections.fields(
				Projections.include("id","age"),
				Projections.excludeId()
				)).
		forEach((Consumer<? super Document>) document -> {
			System.out.println(document.toJson());
		});
	}
	
	@Test
	public void testInsert() {
        Document document = new Document();
        document.append("id", 9999);
        document.append("username", "张三");
        document.append("age", 30);
		this.collection.insertOne(document);
		
		this.collection.find(Filters.eq("id", 9999)).forEach((Consumer<? super Document>) document1 -> {
			System.out.println(document1.toJson());
		});
	}
	
	@Test
	public void testUpdate() {
		UpdateResult updateResult = this.collection.updateOne(Filters.eq("id", 9999), Updates.set("age", 40));
		System.out.println(updateResult);
		this.collection.find(Filters.eq("id", 9999)).forEach((Consumer<? super Document>) document1 -> {
			System.out.println(document1.toJson());
		});
	}
	
	@Test
	public void testDelete() {
		DeleteResult deleteResult = this.collection.deleteMany(Filters.eq("age",25));
		System.out.println(deleteResult);
	}
}

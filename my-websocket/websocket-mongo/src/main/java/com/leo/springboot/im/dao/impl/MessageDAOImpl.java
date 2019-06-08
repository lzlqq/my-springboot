package com.leo.springboot.im.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.leo.springboot.im.dao.MessageDAO;
import com.leo.springboot.im.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer rows) {
		// 用户A发送给用户B的条件
		Criteria criteriaFrom = new Criteria().andOperator(
				Criteria.where("from.id").is(fromId),
				Criteria.where("to.id").is(toId));
		// 用户B发送给用户A的条件
		Criteria criteriaTo = new Criteria().andOperator(
				Criteria.where("from.id").is(toId),
				Criteria.where("to.id").is(fromId));

		CriteriaDefinition criteria = new Criteria().orOperator(criteriaFrom, criteriaTo);
		Pageable pageRequest = PageRequest.of(page - 1, rows, Sort.by(Sort.Direction.ASC, "sendDate"));
		// 设置查询条件，分页
		Query query = Query.query(criteria).with(pageRequest);
		System.out.println(query);
		return mongoTemplate.find(query, Message.class);
	}

	@Override
	public Message findMessageById(String id) {
		return mongoTemplate.findById(new ObjectId(id), Message.class);
	}

	@Override
	public UpdateResult updateMessageState(ObjectId id, Integer status) {
		Query query = Query.query(Criteria.where("id").is(id));
		Update update = Update.update("status", status);
		if (status.intValue() == 1) {
			update.set("send_date", new Date());
		} else if (status.intValue() == 2) {
			update.set("read_date", new Date());
		}
		return mongoTemplate.updateFirst(query, update, Message.class);
	}

	@Override
	public Message saveMessage(Message message) {
		// 写入发送时间
		message.setSendDate(new Date());
		message.setStatus(1);
		message.setId(ObjectId.get());
		return mongoTemplate.save(message);
	}

	@Override
	public DeleteResult deleteMessage(String id) {
		Query query = Query.query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Message.class);
	}

}

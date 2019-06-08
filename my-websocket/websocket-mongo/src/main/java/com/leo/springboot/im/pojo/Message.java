package com.leo.springboot.im.pojo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message") // 指定表的名称
@Builder
public class Message {

	@Id
	private ObjectId id;
	private String msg;
	/**
	 * 消息状态，1-未读，2-已读
	 */
	@Indexed
	private Integer status;
	@Field("send_date")
	@Indexed
	private Date sendDate;
	@Field("read_date")
	private Date readDate;
	@Indexed
	private User from;
	@Indexed
	private User to;

	public static MessageBuilder builder() {
		return new MessageBuilder();
	}

	public static class MessageBuilder {
		private ObjectId id;
		private String msg;
		private Integer status;
		private Date sendDate;
		private Date readDate;
		private User from;
		private User to;

		public MessageBuilder id(ObjectId id) {
			this.id = id;
			return this;
		}

		public MessageBuilder msg(String msg) {
			this.msg = msg;
			return this;
		}

		public MessageBuilder status(Integer status) {
			this.status = status;
			return this;
		}

		public MessageBuilder sendDate(Date sendDate) {
			this.sendDate = sendDate;
			return this;
		}

		public MessageBuilder readDate(Date readDate) {
			this.readDate = readDate;
			return this;
		}

		public MessageBuilder from(User from) {
			this.from = from;
			return this;
		}

		public MessageBuilder to(User to) {
			this.to = to;
			return this;
		}

		public Message build() {
			return new Message(id, msg, status, sendDate, readDate, from, to);
		}

		public ObjectId getId() {
			return id;
		}

		public void setId(ObjectId id) {
			this.id = id;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Date getSendDate() {
			return sendDate;
		}

		public void setSendDate(Date sendDate) {
			this.sendDate = sendDate;
		}

		public Date getReadDate() {
			return readDate;
		}

		public void setReadDate(Date readDate) {
			this.readDate = readDate;
		}

		public User getFrom() {
			return from;
		}

		public void setFrom(User from) {
			this.from = from;
		}

		public User getTo() {
			return to;
		}

		public void setTo(User to) {
			this.to = to;
		}

		public MessageBuilder() {
			super();
		}

	}

	public Message() {
		super();
	}

	public Message(ObjectId id, String msg, Integer status, Date sendDate, Date readDate, User from, User to) {
		super();
		this.id = id;
		this.msg = msg;
		this.status = status;
		this.sendDate = sendDate;
		this.readDate = readDate;
		this.from = from;
		this.to = to;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", msg=" + msg + ", status=" + status + ", sendDate=" + sendDate + ", readDate="
				+ readDate + ", from=" + from + ", to=" + to + "]";
	}

}

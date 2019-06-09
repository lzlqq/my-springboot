package com.leo.springboot.im.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leo.springboot.im.pojo.Message;
import com.leo.springboot.im.service.MessageService;

@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {

	@Autowired
	private MessageService messageService;

	/**
	 * 拉取消息列表
	 *
	 * @param fromId
	 * @param toId
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping
	public List<Message> queryMessageList(@RequestParam("fromId") Long fromId, @RequestParam("toId") Long toId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows) {
		return this.messageService.queryMessageList(fromId, toId, page, rows);
	}

}

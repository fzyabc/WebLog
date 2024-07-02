package com.fzy.weblog.web;

import com.fzy.weblog.common.domain.dos.UserDO;
import com.fzy.weblog.common.domain.mapper.UserMapper;
import com.fzy.weblog.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class WeblogWebApplicationTests {
@Autowired
private UserMapper userMapper;
	@Test
	void contextLoads() {
	}
@Test
	void testLog(){
	log.info("这是一行 Info 级别日志");
	log.warn("这是一行 Warn 级别日志");
	log.error("这是一行 Error 级别日志");
	String author = "冯紫益";
	log.info("这是一行带有占位符日志，作者：{}", author);
	}
	@Test
	void insertTest(){
		// 构建数据库实体类
		UserDO userDO = UserDO.builder()
				.username("她")
				.password("123456")
				.createTime(new Date())
				.updateTime(new Date())
				.isDeleted(false)
				.build();

		userMapper.insert(userDO);

	}
}

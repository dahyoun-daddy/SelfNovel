package com.sist.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sn.msg.dao.MsgDao;
import com.sn.msg.domain.MsgVO;

@WebAppConfiguration("file:application/webapp")
@ContextConfiguration(  locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"	
})
@RunWith(SpringJUnit4ClassRunner.class)
public class testMain {

	@Test
	public void test01() {

	}
}

package com.sn.user.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sn.common.DTO;
import com.sn.user.domain.SMTPAuthenticator;
import com.sn.user.domain.UserVO;
import com.sn.user.service.UserSvc;

@Controller
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserSvc userSvc;
	
	@RequestMapping(value = "login_user.do", method = RequestMethod.POST)
	public String home(Locale locale, Model model) {
		return "member/login";
	}

	@RequestMapping(value = "register_user.do", method = RequestMethod.POST)
	public String register(Locale locale, Model model) {
		return "member/register";
	}
	
	@RequestMapping(value = "update_user.do", method = RequestMethod.POST)
	public String update(Locale locale, Model model) {
		return "member/update";
	}
	
	@RequestMapping(value="user/do_searchOne.do")
	public void do_searchOne(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_password(req.getParameter("u_password"));
		VO.setU_level(Integer.parseInt(req.getParameter("u_level")));
		
		VO = (UserVO) userSvc.do_searchOne(VO);
		if( VO == null) {
			res.getWriter().write("fail");
		} else {
			res.setCharacterEncoding("UTF-8");
			req.getSession().setAttribute("u_id", VO.getU_id());
			req.getSession().setAttribute("u_name", VO.getU_name());
			req.getSession().setAttribute("u_level", VO.getU_level());
			res.getWriter().write(new Gson().toJson(VO));
		}
	}
	
	@RequestMapping(value="user/do_save.do")
	public void do_save(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		
		File file = new File(path);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		log.debug("asdf: " + req.getParameter("u_id"));
		
		/*MultipartRequest mr = new MultipartRequest(req, path, 1024 * 1024 * 5, "utf-8",
				new DefaultFileRenamePolicy());*/
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_name(req.getParameter("u_name"));
		VO.setU_password(req.getParameter("u_password"));
		VO.setU_level(Integer.valueOf(req.getParameter("u_level")));
		
		int flag = userSvc.do_save(VO);
		
		if(flag <= 0) {
			res.getWriter().write("fail");
		} else {
			res.getWriter().write("success");
		}
	}
	
	@RequestMapping(value="user/do_chkId.do")
	public void do_chkId(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		
		VO = (UserVO) userSvc.do_chkId(VO);
		if( VO == null) {
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}
	
	@RequestMapping(value="user/do_preview.do")
	public void do_preview(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		
		VO = (UserVO) userSvc.do_chkId(VO);
		if( VO == null) {
			res.getWriter().write("fail");
		} else {
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(new Gson().toJson(VO));
		}
	}
	
	@RequestMapping(value="user/send_email.do")
	public void send_email(HttpServletRequest req, HttpServletResponse res) throws MessagingException, IOException {
		String from = "sty2003@naver.com";
		String to = req.getParameter("u_id");
		String subject = "[SelfNovel]인증 메일입니다.";
		
		// 인증 번호 생성
		Random random = new Random();
		int authNum = random.nextInt(10000) + 1000;
		if (authNum > 10000) {
			authNum = authNum - 1000;
		}

		String content = "인증 번호: [" + authNum + "]";
		Properties p = new Properties(); // 정보를 담을 객체

		p.put("mail.smtp.host", "smtp.naver.com"); // 네이버 SMTP
		p.put("mail.smtp.ssl.trust", "smtp.naver.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");

		Authenticator auth = new SMTPAuthenticator();
		Session ses = Session.getInstance(p, auth);
	
		ses.setDebug(true);
	
		MimeMessage msg = new MimeMessage(ses); // 메일의 내용을 담을 객체
		msg.setSubject(subject); // 제목
		System.out.println(msg.getSubject());
	
		Address fromAddr = new InternetAddress(from);
		msg.setFrom(fromAddr); // 보내는 사람
		System.out.println(msg.getFrom());
	
		Address toAddr = new InternetAddress(to);
		msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람
		System.out.println(msg.getAllRecipients());
	
		msg.setContent(content, "text/html;charset=UTF-8"); // 내용과 인코딩
		System.out.println(msg.getContent());
	
		Transport.send(msg); // 전송
	
		res.getWriter().write(""+authNum);
	}
	
	@RequestMapping(value="user/do_logout.do")
	public void do_logout(HttpServletRequest req) {
		req.getSession().removeAttribute("u_id");
		req.getSession().removeAttribute("u_name");
		req.getSession().removeAttribute("u_level");
	}
	
	@RequestMapping(value="user/do_update.do")
	public void do_update(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		
		MultipartRequest mr = new MultipartRequest(req, path, 1024 * 1024 * 5, "utf-8",
				new DefaultFileRenamePolicy());
		
		UserVO VO = new UserVO();
		String functionSep = mr.getParameter("functionSep");
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put("functionSep", functionSep);
		VO.setParam(param);
		
		VO.setU_id(mr.getParameter("u_id"));
		VO.setU_name(mr.getParameter("u_name"));
		VO.setU_password(mr.getParameter("u_password"));
		
		int flag = userSvc.do_update(VO);
		if(flag > 0) {
			req.getSession().setAttribute("u_name", VO.getU_name());
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}
	
	@RequestMapping(value="user/do_delete.do")
	public void do_delete(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		log.debug("asdf: " + VO.getU_id());
		
		int flag = userSvc.do_delete(VO);
		
		if(flag > 0) {
			req.getSession().removeAttribute("u_id");
			req.getSession().removeAttribute("u_name");
			req.getSession().removeAttribute("u_level");
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}		
	}
}

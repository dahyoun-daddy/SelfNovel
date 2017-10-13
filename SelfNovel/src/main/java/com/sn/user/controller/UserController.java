package com.sn.user.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sn.common.DTO;
import com.sn.user.domain.SMTPAuthenticator;
import com.sn.user.domain.UserVO;
import com.sn.user.naverlogin.NaverLoginBO;
import com.sn.user.naverlogin.NaverUser;
import com.sn.user.service.UserSvc;

@Controller
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserSvc userSvc;
	
	NaverLoginBO naverLoginBO = new NaverLoginBO();
	
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
	
	@RequestMapping(value="user/naver_login.do")
    public ModelAndView login(HttpSession session) {
        /* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        
        log.debug("asdf: " + naverAuthUrl);
        
        /* 생성한 인증 URL을 View로 전달 */
        return new ModelAndView(naverAuthUrl);
    }
	
	@RequestMapping(value="user/naver_callback.do")
	public ModelAndView naver_callback(@RequestParam String code, @RequestParam String state, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		OAuth20Service oauthService = naverLoginBO.getOauthService(session.getAttribute("state").toString());
		// Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		System.out.println(accessToken.toString());
    	// 발급받은 AccessToken을 이용하여 현재 로그인한 네이버의 사용자 프로필 정보를 조회할 수 있다. 
        NaverUser naverUser = naverLoginBO.getUserProfile(accessToken);
//        
        System.out.print(naverUser.toString());
        
        ModelAndView modelAndView = new ModelAndView();
        UserVO userVO = new UserVO();
        userVO.setU_naver(naverUser.getId());
        userVO = (UserVO) userSvc.do_chkNaver(userVO);
        session.setAttribute("isNaver", "true");
        
        if(userVO == null) {
        	modelAndView.addObject("nickName",naverUser.getNickname());
        	modelAndView.addObject("profileImage",naverUser.getProfileImage());
        	modelAndView.addObject("naverId",naverUser.getId());
        	modelAndView.setViewName("member/register");
        	return modelAndView;
        } else {
        	session.setAttribute("u_id", userVO.getU_id());
        	session.setAttribute("u_name", userVO.getU_name());
        	session.setAttribute("u_level", userVO.getU_level());
        	modelAndView.setViewName("home");
        	return modelAndView;
        }
        
//        log.debug("asdf: " + naverUser.getNickname());
//        
//         네이버 사용자 프로필 정보를 이용하여 가입되어있는 사용자를 DB에서 조회하여 가져온다. 
//        SnsUser snsUser = userBO.getUserByNaverUser(naverUser);
//        
//         만약 일치하는 사용자가 없다면 현재 로그인한 네이버 사용자 계정으로 회원가입이 가능하도록 가입페이지로 전달한다 
//        if( snsUser == null ) {
//        	return new ModelAndView("redirect:/join/naver");
//        }
        
//         만약 일치하는 사용자가 있다면 현재 세션에 사용자 로그인 정보를 저장 
//        session.setAttribute("SNS_USER",snsUser);
	}
	
	@RequestMapping(value="user/do_searchOne.do")
	public void do_searchOne(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().setAttribute("isNaver", "false");
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
		/*MultipartRequest mr = new MultipartRequest(req, path, 1024 * 1024 * 5, "utf-8",
				new DefaultFileRenamePolicy());*/
		UserVO VO = new UserVO();
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_name(req.getParameter("u_name"));
		VO.setU_password(req.getParameter("u_password"));
		VO.setU_level(Integer.valueOf(req.getParameter("u_level")));
		VO.setU_naver(req.getParameter("u_naver"));
		
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
	public void do_update(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
		UserVO VO = new UserVO();
		String functionSep = req.getParameter("functionSep");
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put("functionSep", functionSep);
		VO.setParam(param);
		
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_name(req.getParameter("u_name"));
		VO.setU_password(req.getParameter("u_password"));
		
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

package com.sist.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.DTO;
import com.sn.img.domain.ImgVO;
import com.sn.img.service.ImgSvc;

@Controller
public class pptTestController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ImgSvc imgSvc;
	
	
	@RequestMapping(value="ppt/pptUpload.do", method=RequestMethod.GET)
	public ModelAndView pptMainView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewTest/pptTest");
		return modelAndView;
	}
	
	@RequestMapping(value="ppt/pptUpload.do", method=RequestMethod.POST)
	public ModelAndView pptUpload(MultipartHttpServletRequest mReq) throws IOException,DataAccessException{
		ModelAndView modelAndView = new ModelAndView();
		
		
		/*********************************************************************/
		//1. ppt를 이미지로 만든다
		//2. 저장한 이미지 파일명(경로포함)을 돌려받는다
		//2-2. 저장하는 이미지는 ppt이름+번호.png로 한다.
		//2-3. 이미지 경로는 jsp에서 contextPath로 받는다
		//3. c:foreach문을 돌려 출력해본다
		
		//4. 위의 과정이 완료되었을 경우 슬라이드형으로 표현해보도록 한번 해본다
		List<DTO> list = imgSvc.do_saveMulti(mReq);
		//지금은 일단 기존에 쓰던걸 쓴다. 저장경로는 "c:\\file\\"이다.
		ImgVO imgVO = (ImgVO)list.get(0);
		//이건 이미지 경로+이름 리스트를 저장할 리스트임
		List<String> imgList = new ArrayList<String>();
		//이건 컨텍스트 경로 구하는 그것이다.
		String uploadFilepath = mReq.getSession().getServletContext().getRealPath("resources");
		log.debug("==========================================================");
		log.debug("uploadFilepath: "+uploadFilepath);
		log.debug("==========================================================");
		
		  //File file=new File("C://file//"+imgVO.get//imgVO.getSave_file_nm());
		  File file=new File("C://file//"+imgVO.getImg_path()+"//"+imgVO.getImg_sv_nm());
	      XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
	      
	      //getting the dimensions and size of the slide 
	      Dimension pgsize = ppt.getPageSize();
	      //List<XSLFSlide> slide = ppt.getSlides();
	      XSLFSlide[] slide = ppt.getSlides();
	      FileOutputStream out =null;
	      
	      for (int i = 0; i < slide.length; i++) {
	         BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
	         Graphics2D graphics = img.createGraphics();

	         //clear the drawing area
	         graphics.setPaint(Color.white);
	         graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

	         //render
	         slide[i].draw(graphics);
	         
	         //creating an image file as output
	         out = new FileOutputStream(uploadFilepath+"/images/ppt_image"+i+".png");
	         javax.imageio.ImageIO.write(img, "png", out);
	         ppt.write(out);
	         
	         //여기서 이미지 정보를 저장해서 넘겨줄거다. 앞부분 떼고 넘기는 이유는 부르는 부분의 context 경로로 줄 것이기 때문이다
	         imgList.add("images/ppt_image"+i+".png"); 
	         log.debug(i+"번째: "+uploadFilepath+"/images/ppt_image"+i+".png");
	         
	         out.close();
	      }
	      
	      System.out.println("Image successfully created");
	      out.close();	
	      /*********************************************************************/
		
	      
		modelAndView.setViewName("viewTest/pptTest");
		modelAndView.addObject("imgList",imgList);
		return modelAndView;
	}
}

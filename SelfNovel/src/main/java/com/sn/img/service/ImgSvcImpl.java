package com.sn.img.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.common.FileSaveUtil;
import com.sn.common.FileSaveVO;
import com.sn.common.StringUtil;
import com.sn.img.dao.ImgDao;
import com.sn.img.domain.ImgVO;

@Repository
public class ImgSvcImpl implements ImgSvc {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ImgDao imgDao;	
	String saveImgPath="images";
	
	/**
	 * do_savepptTx
	 * detail : ppt 화면을 캡쳐해 이미지로 저장하는 메소드. 저장 이후 이미지 아이디를 반환하므로, resume 저장 중이라면 아래의 메소드를 먼저 호출하고 나온 id값을 set해주면 된다.
	 * 
	 * 전달받아야 할 값: 저장한 ppt이름, ppt가 저장된 경로, context경로
	 * 반환하는 값	: images ID
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	/*
	 * 	외부에서의 사용법:
	 	//1. 먼저 파일유틸을 이용해 파일을 저장하고 파일명을 받는다.
		String savePath = "C://file//";
		String resourcesFilepath = mReq.getSession().getServletContext().getRealPath("resources");
		
		FileSaveUtil fileSaveUtil = new FileSaveUtil();
		List<FileSaveVO> fileList = fileSaveUtil.do_saveMulti(mReq, savePath);
		String saveFileName = "";
		
		if(fileList!=null) {
			FileSaveVO fileSaveVO = fileList.get(0);
			saveFileName = StringUtil.nvl(fileSaveVO.getSaveFileName(), "");
		}
		
		//2. 이미지 서비스 쪽에 파일의 저장경로를 넘겨준다. 그럼 저장하고 이미지 아이디를 반환한다.
		int img_id = this.imgSvc.do_savepptTx(saveFileName,savePath+saveFileName,resourcesFilepath);
		
		//3. 반환된 이미지 아이디를 가지고 리스트를 불러온다.
		ImgVO inVO = new ImgVO();
		inVO.setImg_id(img_id);		
		List<?> imgList = imgSvc.do_search(inVO); 		
	*/	
	@Override
	public int do_savepptTx(String fileName, String filePath, String resourcesFilepath) throws FileNotFoundException, IOException{
        log.debug("**********************do_savepptTx*************************");
        log.debug("fileName: "+fileName);
        log.debug("filePath: "+filePath);
        log.debug("resourcesFilepath: "+resourcesFilepath);
        log.debug("***********************************************");
		
		
		/*********************************************************************/
		//1. 받은 파일경로로 파일 가져옴
		//2. 이미지 아이디(시퀀스)를 가져옴
		//3. ppt를 이미지로 만든다
		//4. 저장한 이미지 파일명(경로포함)을 돌려받는다
		//4-2. 저장하는 이미지는 ppt이름+번호.png로 한다.
		//4-3. 이미지 경로는 jsp에서 contextPath로 받는다
		//5. c:foreach문을 돌려 출력해본다		
		/*********************************************************************/		
		FileOutputStream out =null;	
		
		
		//1. 받은 파일경로로 파일 가져옴
		File file=new File(filePath);
		
		//2. 이미지 아이디(시퀀스)를 가져옴
		int imgID = this.imgDao.do_getNextSeq();
		
		//3. ppt를 이미지로 만든다
		//4. 저장한 이미지 파일명(경로포함)을 돌려받는다
		
		//ppt slide
		XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
	    //getting the dimensions and size of the slide 
	    Dimension pgsize = ppt.getPageSize();	  
	    List<XSLFSlide> slide = ppt.getSlides();		
		
	    for (int i = 0; i < slide.size(); i++) {
	         BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
	         Graphics2D graphics = img.createGraphics();

	         //clear the drawing area
	         graphics.setPaint(Color.white);
	         graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

	         //render
	         slide.get(i).draw(graphics);
	         
	         //creating an image file as output
	         out = new FileOutputStream(resourcesFilepath+"/"+saveImgPath+"/"+"fileName"+i+".png");
	         javax.imageio.ImageIO.write(img, "png", out);
	         ppt.write(out);
	         
	         //여기서 이미지 정보를 저장해서 넘겨줄거다. 앞부분 떼고 넘기는 이유는 부르는 부분의 context 경로로 줄 것이기 때문이다
	         log.debug(i+"번째: "+resourcesFilepath+"/"+saveImgPath+"/"+"fileName"+i+".png");        	         
	         /**********************************************************/
	         //setVO
	         ImgVO imgVo = new ImgVO();
	         imgVo.setImg_id(imgID);
	         imgVo.setImg_num(i);
	         imgVo.setImg_org_nm("fileName"+i+".png");
	         imgVo.setImg_sv_nm("fileName"+i+".png");
	         imgVo.setImg_path(saveImgPath);
	         imgVo.setImg_use_yn(1);
	         
	         log.debug("***********************************************");
	         log.debug("imgVo: "+imgVo.toString());
	         log.debug("***********************************************");
	         
	         this.imgDao.do_save(imgVo);
	         /**********************************************************/
	         
	         out.close();
	      }
		
	    out.close();
		return imgID;
	}

	/**
	 * do_search
	 * detail : 이미지 다건조회
	 * 
	 * 전달받아야 할 값: images ID가 들어있는 dto
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public List<?> do_search(DTO dto) {
		return this.imgDao.do_search(dto);
	}	
}

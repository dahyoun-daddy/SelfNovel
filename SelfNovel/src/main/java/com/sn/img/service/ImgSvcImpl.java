package com.sn.img.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sn.common.DTO;
import com.sn.img.dao.ImgDao;
import com.sn.img.domain.ImgVO;

@Repository
public class ImgSvcImpl implements ImgSvc {
	@Autowired
	ImgDao imgDao;
	
	
	
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 파일 멀티 upload;
	 */
	@Override
	public List<DTO> do_saveMulti(MultipartHttpServletRequest mReq) throws IOException, DataAccessException {

		String uploadPath = "c:\\file\\";
		String work_div = mReq.getParameter("work_div");
		
		File fileDir = new File(uploadPath);
		if(fileDir.isDirectory()==false) {
			fileDir.mkdirs();
		}//파일 하나마다 저장할 수 있는 공간이 제한되어있기 때문에 원래대로라면 월별 폴더를 만들어줘야 한다.
		
		Iterator<String> iter =mReq.getFileNames();
		List<DTO> list = new ArrayList<DTO>();
		int fileNo = 1;
		while(iter.hasNext()) {
			ImgVO imgVO = new ImgVO();
			String uploadFileName = iter.next();
			String orgFileName 	= ""; //원본파일명
			String saveFileName = ""; //저장파일명
			String ext 			= ""; // 확장자
			long   fileSize 	= 0;  //파일 사이즈
			log.debug("==========================================");
			log.debug("uploadFileName: "+uploadFileName);
			log.debug("==========================================");
			
			MultipartFile mFile = mReq.getFile(uploadFileName);
			orgFileName = mFile.getOriginalFilename();
			//확장자가 없는 경우(혹은 파일 첨부가 되지 않은 경우) 아래 단계에서 오류가 난다.
			ext = orgFileName.substring(orgFileName.lastIndexOf("."));
			fileSize = mFile.getSize();
			
			saveFileName = currDate("yyyy-MM-dd")+"_"+getUUid()+ext;
			log.debug("==========================================");
			log.debug("saveFileName: "+saveFileName);
			log.debug("orgFileName: "+orgFileName);
			log.debug("fileSize: "+fileSize);
			log.debug("==========================================");
			
			if(orgFileName != null && !orgFileName.equals("")) {
				//올릴때는 예외처리
				try {
					mFile.transferTo(new File(uploadPath+saveFileName));//저장
					
					//fileVO.setFile_size(fileSize+"");
//					ImgVO.setNo(fileNo);
//					ImgVO.setOrg_file_nm(orgFileName);
//					ImgVO.setSave_file_nm(saveFileName);
					imgVO.setImg_org_nm(orgFileName);
					imgVO.setImg_sv_nm(saveFileName);
					//imgVO.setImg_path(img_path);
					
					//TODO: 세션에서 가지고 올것
					//fileVO.setReg_id(reg_id);//session
					//fileVO.setMod_id(mod_id);//session
					//imgVO.setWork_div(work_div);
					
					list.add(imgVO);
					
				}catch(IllegalStateException ie) {
					log.debug("====================================");
					log.debug("UploadSvcImpl-do_saveMulti-IllegalStateException");
					log.debug("====================================");
					throw ie;
				}
			}
			
			fileNo++;
		}
		return list;
	}

	/**
	 * yyyy-MM-dd 형태 날짜 처리
	 * @param type
	 * @return String 2017-09-11
	 */
	public String currDate(String type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		
		return sdf.format(date);
	}
	
	/**
	 * 32bit random함수
	 * @return String
	 */
	//저장파일명 뽑아내기
	public String getUUid() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}

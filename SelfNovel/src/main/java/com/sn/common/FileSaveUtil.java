package com.sn.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sn.img.domain.ImgVO;
import com.sn.user.controller.UserController;

/**
 * FileSaveUtil 
 * detail : 파일을 저장하고 파일정보가 담긴 리스트를 반환해주는 유틸클래스
 * 최초작성: 2017-09-27
 * 최종수정: 2017-09-27
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class FileSaveUtil {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * do_saveMulti
	 * detail : 다중파일 저장 메소드.
	 * 
	 * 전달받아야 할 값: MultipartHttpServletRequest, 저장경로
	 * 반환하는 값	: 파일정보 리스트
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public List<FileSaveVO> do_saveMulti(MultipartHttpServletRequest mReq, String savePath) 
			throws IOException, DataAccessException {

		List<FileSaveVO> fileInfo = new ArrayList<FileSaveVO>();
		String uploadPath = StringUtil.nvl(savePath, "C://file//");
		
		File fileDir = new File(uploadPath);
		if(fileDir.isDirectory()==false) {
			fileDir.mkdirs();
		}
		
		Iterator<String> iter =mReq.getFileNames();		
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
					FileSaveVO fsVO = new FileSaveVO();
					fsVO.setOrgFileName(orgFileName);
					fsVO.setSaveFileName(saveFileName);
					fsVO.setFileSize(fileSize);
					
					fileInfo.add(fsVO);					
				}catch(IllegalStateException ie) {
					log.debug("====================================");
					log.debug("UploadSvcImpl-do_saveMulti-IllegalStateException");
					log.debug("====================================");
					throw ie;
				}
			}
		}
		return fileInfo;
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

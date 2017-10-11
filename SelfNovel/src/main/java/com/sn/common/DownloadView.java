package com.sn.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//model: key,value구조로 들어와있는 맵.
		
		try {
			setResponseContentType(request, response);
			File downloadFile = (File) model.get("downloadFile");
			//테스트용 오리지널 파일이름
			String org_file_nm = (String) model.get("org_file_nm");
			
			log.debug("============================================================");
			log.debug("downloadFile: "+downloadFile.getName());
			log.debug("============================================================");
			
			setDownloadFileName(downloadFile.getName(),request,response);
			//setDownloadFileName(org_file_nm,request,response);
			response.setContentLength((int)downloadFile.length());
			downloadFile(downloadFile,request,response);
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	//헤더에 파일이름 세팅하는 부분인가보다. 브라우저도 검사하고 겸사겸사 인코딩도 하고
	private void setDownloadFileName(String fileName,HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		//여기서 한글이름으로 바꿔치기할 수 있다.
		String userAgent = request.getHeader("User-Agent");
		
		boolean isIe = userAgent.indexOf("MSIE")!=-1;
		
		//fileName="hello"; //<먹힘
		//fileName = "안녕.txt"; //확장자를 붙여야함		

		//get으로 파일을 넘기면 한글이 깨지는데 base64 인코딩했다가... base64방식으로 다시 디코딩해서 받으면 안깨지긴 한다고
		//일단 아래는 인코딩과정이다
		if(isIe) {
			fileName = URLEncoder.encode(fileName,"utf-8");
		}else {
			fileName = new String(fileName.getBytes("utf-8"));
		}
		
		//test
		fileName= new String(fileName.getBytes("utf-8"),"8859_1");//이걸 붙이면 한글명으로 나온다.

		
		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\";");
		//response.setHeader("Content-Disposition", "attachment; filename=\""+"안뇽"+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		log.debug("============================================================");
		log.debug("fileName: "+fileName);
		log.debug("============================================================");
	}
	
	private void downloadFile(File downloadFile,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(downloadFile);
		//이미지파일같은 경우는 리소스를 읽을 수 있는 resource 폴더에 넣어야한다고 들었던것같다.
		
		try {
			FileCopyUtils.copy(in, out);
			out.flush();
		}catch(Exception e) {
			throw e;
		}finally {
			try {
				if(in!=null) in.close();
			}catch(IOException ioe) {
				throw ioe;
			}
			try {
				if(out!=null) out.close();
			}catch(IOException ioe) {
				throw ioe;
			}
		}
		
	}

}

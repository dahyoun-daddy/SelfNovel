package com.sn.common;

/**
 * FileSaveVO 
 * detail : FileSave util에 쓰이는 vo. DB에 쓰이는 일은 없다.
 * 최초작성: 2017-09-27
 * 최종수정: 2017-09-27
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class FileSaveVO extends DTO {
	private String saveFileName;	//저장파일명
	private String orgFileName;		//원본파일명
	private String ext;				//확장자
	private long   fileSize;		//파일사이즈
		
	public FileSaveVO() {
		super();
	}	
	public FileSaveVO(String saveFileName, String orgFileName, String ext, long fileSize) {
		super();
		this.saveFileName = saveFileName;
		this.orgFileName = orgFileName;
		this.ext = ext;
		this.fileSize = fileSize;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "FileSaveVO [saveFileName=" + saveFileName + ", orgFileName=" + orgFileName + ", ext=" + ext
				+ ", fileSize=" + fileSize + "]";
	}

	
}

package com.sn.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExcelUtil 
 * detail : 엑셀 다운로드를 위한 엑셀유틸 클래스
 * 최초작성: 2017-10-10
 * 최종수정: 2017-10-10
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class ExcelUtil {
	private static Logger log= LoggerFactory.getLogger(ExcelUtil.class);
	private HSSFWorkbook workbook;
	private String filePath;
	private String excelFileName;
	private String changFileName;
	private static short firstRow = 5;
	private static short firstCol = 1;
	
	
	/**
	 * 
	 * @param filePath
	 * @param excelFileName
	 * @param header
	 * @param align
	 * @param data
	 * @throws IOException
	 */   
	public String writeExcel(String filePath,String excelFileName,List<?> data)throws IOException{
		this.filePath = filePath;
		this.excelFileName =excelFileName;
		FileOutputStream out = setFile(this.filePath,this.excelFileName);
	    
		// create a new workbook
		HSSFWorkbook  wb =  createExcel(data);
		try{
			wb.write(out);
		}finally{
			out.close();
			wb.close();
		}
		
		return changFileName;
	}	
	
	/**
	 * setFile
	 * @param filePath
	 * @param excelFileName
	 * @return FileOutputStream
	 * @throws FileNotFoundException
	 */
    private FileOutputStream setFile(String filePath, String excelFileName)throws FileNotFoundException{
        File dir = new File(filePath); 
        if(!dir.exists()) dir.mkdirs(); 
        //File존재하면
        String changeFileName = createFile(filePath,excelFileName);
        
        FileOutputStream fout = new FileOutputStream(filePath+"/"+changeFileName); 
        return fout;
    }
    
    /**
     * createFile
     * 파일 rename
     * @param filePath
     * @param excelFileName
     * @return
     */
    public String createFile(String filePath, String excelFileName){
        File file = new File(filePath, excelFileName);
        String changeFileName = excelFileName;
        if(file.isFile()){
            changeFileName=System.currentTimeMillis()+"_"+UUID.randomUUID().toString().replace("-", "")+"_"+excelFileName;
        }
        changFileName = changeFileName;
        return changeFileName;
     }
    
    
    
    
    
    
	
	/**
	 * 
	 * @param data
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createExcel(List<?> data){
	   workbook = new HSSFWorkbook();
	   HSSFSheet sheet = workbook.createSheet("UserList");
       
       // ## Font Setting
       // @HSSFFont : 폰트 설정
       //  - FONT_ARIAL : 기본
       HSSFFont font = workbook.createFont();
       font.setFontName(HSSFFont.FONT_ARIAL);
        
       // ## Title Style Setting
       // @HSSFColor : 셀 배경색
       //  - GREY_$_PERCENT : 회색 $ 퍼센트
       // @HSSFCellStyle
       //  - ALIGN_$ : $ 쪽 정렬
       HSSFCellStyle titleStyle = workbook.createCellStyle();
       titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
       //titleStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
       //titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       titleStyle.setFont(font);
        
       // ## Row Create
       // ? 가로열 생성
       HSSFRow row = sheet.createRow((short)firstRow);
        
       // ## Title Cell Create
       // @row.createCell((short)n) : n번째 셀 설정
       // @setCellValue(String) : n 번째 셀의 내용
       // @setCellStyle(style) : n 번째 셀의 스타일
       HSSFCell cell_0 = row.createCell((short)0+firstCol);
       cell_0.setCellValue("번호");
       cell_0.setCellStyle(titleStyle);
        
       HSSFCell cell_1 = row.createCell((short)1+firstCol);
       cell_1.setCellValue("아이디");
       cell_1.setCellStyle(titleStyle);
        
       HSSFCell cell_2 = row.createCell((short)2+firstCol);
       cell_2.setCellValue("이름");
       cell_2.setCellStyle(titleStyle);
        
       HSSFCell cell_3 = row.createCell((short)3+firstCol);
       cell_3.setCellValue("레벨");
       cell_3.setCellStyle(titleStyle);
       
       HSSFCell cell_4 = row.createCell((short)4+firstCol);
       cell_4.setCellValue("로그인");
       cell_4.setCellStyle(titleStyle);
       
       HSSFCell cell_5 = row.createCell((short)5+firstCol);
       cell_5.setCellValue("이메일");
       cell_5.setCellStyle(titleStyle);
       
       
       //  ObjectList 가 비어있으면 제목만 출력 후 종료
       if(data == null) return workbook;
        
       //  ObjectList 엑셀에 출력
       for(int i = 0; i < data.size(); i++){
           // 1번째 행은 제목이니 건너 뜀
           row = sheet.createRow((short)this.firstRow+(i+1));
           //UserVO user = (UserVO)data.get(i);
            
           cell_0 = row.createCell((short)0+firstCol);
           //cell_0.setCellValue(user.getNo());
           //cell_0.setCellStyle(styleCenter);
            
           cell_1 = row.createCell((short)1+firstCol);
           //cell_1.setCellValue(user.getId());
           //cell_1.setCellStyle(styleLeft);
            
           cell_2 = row.createCell((short)2+firstCol);
           //cell_2.setCellValue(user.getName());
           //cell_2.setCellStyle(styleLeft);
            
           cell_3 = row.createCell((short)3+firstCol);
           //cell_3.setCellValue(user.getU_Level());
           //cell_3.setCellStyle(styleRight);
           
           cell_3 = row.createCell((short)4+firstCol);
           //cell_3.setCellValue(user.getLogin());
           //cell_3.setCellStyle(styleRight);
           
           cell_3 = row.createCell((short)5+firstCol);
           //cell_3.setCellValue(user.getMail());
           //cell_3.setCellStyle(styleLeft);
       }
        
       //컬럼사이즈
       for(int i=0; i<7; i++){
    	   if(i==0){
    		   sheet.setColumnWidth(0,700);
    	   }else{
    		   sheet.autoSizeColumn((short)i);
    		   sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512 );  // 윗줄만으로는 컬럼의 width 가 부족하여 더 늘려야 함.
    	   }
       }
       
       
       return workbook;
   }
    
}

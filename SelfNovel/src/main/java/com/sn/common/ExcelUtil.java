package com.sn.common;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sn.resume.domain.ItmVO;

/**
 * ExcelUtil 
 * detail : 엑셀 다운로드를 위한 엑셀유틸 클래스
 * 최초작성: 2017-10-10
 * 최종수정: 2017-10-11
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class ExcelUtil {
	private static Logger log= LoggerFactory.getLogger(ExcelUtil.class);
	private HSSFWorkbook workbook;
	private String filePath;
	private String excelFileName;
	private String changFileName;
	private static short firstRow = 0;
	private static short firstCol = 0;
	private static short maxWidth = 10; //가로 최대길이
	
	/**
	 * 
	 * @param filePath
	 * @param excelFileName
	 * @param header
	 * @param align
	 * @param data
	 * @throws IOException
	 */   
	public String writeExcel(String filePath,String excelFileName,List<?> data,String u_name)throws IOException{
		this.filePath = filePath;
		this.excelFileName =excelFileName;
		FileOutputStream out = setFile(this.filePath,this.excelFileName);
	    
		// create a new workbook
		HSSFWorkbook  wb =  createExcel(data,u_name);
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
        //FileOutputStream fout = new FileOutputStream(filePath+"/"+excelFileName); 
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
	 * createExcel
	 * detail: user's resume => Excel. 유저 자기소개서로 엑셀 그리는 메소드
	 * 
	 * 기타사항:
	 * 1. 병합하는 모든 셀에 스타일을 준 이유는 병합이후 스타일이 전체에 상속되지 않기 때문이다.(예: 테두리)
	 * 2. 병합된 셀은 자동 개행 속성을 준다 하더라도 높이가 자동으로 조절되지 않는다. 때문에 임의로 주었다.
	 * 2-1. 1줄에 100char이라 가정하고, (전체 텍스트/100)+1 = row 수 => 기본 셀 높이*row 수
	 * 
	 * @param data
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createExcel(List<?> data,String u_name){
	   workbook = new HSSFWorkbook();
	   HSSFSheet sheet = workbook.createSheet("자기소개서");
	   int corRow=0;//현재 row
	   int corCol=0;//현재 col
        
       /*****************************************************/
       //스타일 지정
       /*****************************************************/      
       //제목 스타일(테두리, 정렬, 셀 색, 폰트)
       HSSFCellStyle titleStyle = workbook.createCellStyle();
       titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
       titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
       titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
       titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   

       titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			//셀 색 패턴
       titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);	//셀 색 지정
       
       titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);     			//가운데 정렬
       
       HSSFFont fontTitle = workbook.createFont();
       fontTitle.setBold(true);								//굵게
       //fontTitle.setFontHeightInPoints((short)20);			//크기 20
       titleStyle.setFont(fontTitle);
       
       //소제목 스타일(테두리, 정렬, 셀 색, 폰트)
       HSSFCellStyle sTitleStyle = workbook.createCellStyle();
       sTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
       sTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
       sTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
       sTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   

       sTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			//셀 색 패턴
       sTitleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);	//셀 색 지정
       
       sTitleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);     				//좌측 정렬
       
       HSSFFont fontSTitle = workbook.createFont();
       fontSTitle.setBold(true);								//굵게
       //fontTitle.setFontHeightInPoints((short)20);			//크기 20
       sTitleStyle.setFont(fontTitle);
       
       //본문 스타일(테두리)
       HSSFCellStyle contentStyle = workbook.createCellStyle();
       contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
       contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
       contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
       contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
       contentStyle.setWrapText(true);
       
       
       /*****************************************************/
       //최상단 타이틀
       /*****************************************************/
       // ## Row Create
       // 가로열 생성
       HSSFRow row = sheet.createRow((short)firstRow);       //상단 자기소개서 스타일
       
       //상단 자기소개서 텍스트
       HSSFCell titleCell = row.createCell(firstCol);
       titleCell.setCellValue("자기소개서");
       titleCell.setCellStyle(titleStyle);
       
       //병합될 셀들에 스타일을 줌
       for(int i=firstCol+1;i<=firstCol+maxWidth;i++) {
    	   HSSFCell cell = row.createCell(i);
    	   cell.setCellStyle(titleStyle);
       }
       
       //셀 병합
       sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow, firstCol, firstCol+maxWidth));

       //셀 높이 조정
       row.setHeightInPoints(sheet.getDefaultRowHeightInPoints()*3);
       
       //cor값 갱신
       corRow = firstRow+1;
       
       /*****************************************************/
       //이름
       /*****************************************************/
       corCol = firstCol;
       row = sheet.createRow((short)corRow); 
       
       //이름 텍스트 생성
       HSSFCell nameCell = row.createCell(corCol);
       nameCell.setCellValue("이 름: ");
       nameCell.setCellStyle(titleStyle);
       
       //이름 데이터 추가
       corCol++;
       nameCell = row.createCell(corCol);
       nameCell.setCellValue(u_name);
       nameCell.setCellStyle(contentStyle);
       
       //병합될 셀들에 스타일을 줌
       for(int i=corCol+1;i<=maxWidth;i++) {
    	   HSSFCell cell = row.createCell(i);
    	   cell.setCellStyle(contentStyle);
       }
       
       //셀 병합
       sheet.addMergedRegion(new CellRangeAddress(corRow, corRow, corCol, maxWidth));
       
       //cor값 갱신
       corRow++;
       

       /********************************************************************************/
       //item
       /********************************************************************************/
       //들어가기 전에 null 상태를 검사한다. list가 없을 경우 아무 항목도 나오지 않는다.
       int totalCnt=0;
       if(data!=null) {
    	   totalCnt=data.size();
       }       
       
       //소항목 for문. : 이 부분을 이후 item 리스트로 변경하면 된다.
       for(int cnt=0;cnt<totalCnt;cnt++) {
    	   ItmVO vo = (ItmVO) data.get(cnt);
    	   
           /*****************************************************/
           //소항목 제목 텍스트
           /*****************************************************/       
           corCol = firstCol;
           row = sheet.createRow((short)corRow); 
           
           //제목 텍스트 생성
           HSSFCell sTitleCell = row.createCell(corCol);             
           sTitleCell.setCellValue("항 목: ");
           sTitleCell.setCellStyle(titleStyle);
           
           
           //제목 데이터 추가
           corCol++;
           nameCell = row.createCell(corCol);
           String prd_id = StringUtil.nvl(vo.getItm_prd_id(), "");
           if(prd_id.equals("")) {
        	   nameCell.setCellValue(vo.getItm_title());
           }else {
        	   nameCell.setCellValue(" ㄴ 첨삭: "+vo.getItm_title());
           }
           nameCell.setCellStyle(sTitleStyle);
           
           //병합될 셀들에 스타일을 줌
           for(int i=corCol+1;i<=maxWidth;i++) {
        	   HSSFCell cell = row.createCell(i);
        	   cell.setCellStyle(sTitleStyle);
           }
           
           //셀 병합
           sheet.addMergedRegion(new CellRangeAddress(corRow, corRow, corCol, maxWidth));
           
           //cor값 갱신
           corRow++;
    	   
           /*****************************************************/
           //소항목 본문 텍스트
           /*****************************************************/              
           corCol = firstCol;
           row = sheet.createRow((short)corRow);
           
           //소항목 본문 테스트
           String text = vo.getItm_content();
           HSSFCell contentCell = row.createCell(corCol);
           contentCell.setCellValue(text);  
           contentCell.setCellStyle(contentStyle);
           
           //병합될 셀들에 스타일을 줌
           for(int i=corCol+1;i<=(maxWidth-corCol);i++) {
        	   HSSFCell cell = row.createCell(i);
        	   cell.setCellStyle(contentStyle);
           }
           
           //셀 병합
           sheet.addMergedRegion(new CellRangeAddress(corRow, corRow, corCol, maxWidth));
           
           /*****************************************************/
           //1줄에 100글자가 들어간다고 친다.
           //1. 먼저 텍스트 전체 문자 수를 구한다.
           //2. 기준인 50글자로 텍스트를 나눈다 = 곧 row값이다
           //3. 정해진 기준값대로 row의 높이를 늘린다
           //ps. 그런데 너무 빽빽해보여서 한줄 더 늘려주기로 했다.
           /*****************************************************/
           int cntText = text.length();
           int cntRow = (cntText/50)+1+1;//ps의 이유로 한줄 더 늘려준 부분
           row.setHeightInPoints(sheet.getDefaultRowHeightInPoints()*cntRow);
           
           //cor값 갱신
           corRow++;  	   
       }
       
       return workbook;
   }
    
	
	
	/**
	 * writeExcelGeneral
	 * 
	 * @param filePath
	 * @param excelFileName
	 * @param header
	 * @param align
	 * @param data
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */   
	public String writeExcelGeneral(String filePath,String excelFileName,
			String sheetName, String[] header, List<?> data,Class clazz,List<String> param)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
			NoSuchMethodException, SecurityException{
		this.filePath = filePath;
		this.excelFileName =excelFileName;
		FileOutputStream out = setFile(this.filePath,this.excelFileName);
	    
		// create a new workbook
		HSSFWorkbook  wb =  createExcelGeneral(sheetName,header,data,clazz,param);
		try{
			wb.write(out);
		}finally{
			out.close();
			wb.close();
		}
		
		return changFileName;
	}	
	
	/**
	 * createExcelGeneral
	 * detail: 일반화 엑셀 함수
	 * param: 
	 *  1. sheetName : 시트이름
	 * 	2. data: 출력할 리스트 데이터
	 *  3. clazz: 리스트의 클래스
	 *  4. param: 검색조건 리스트
	 * 
	 * @param data
	 * @return HSSFWorkbook
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	//헤더랑 ali조절이랑 넓이조절이랑 
	public HSSFWorkbook createExcelGeneral(String sheetName,String[] header, List<?> data,Class clazz,
			List<String> param) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
			NoSuchMethodException, SecurityException{
	   int corRow = this.firstRow;
	   int corCol = this.firstCol+1;
	   workbook = new HSSFWorkbook();
	   HSSFSheet sheet = workbook.createSheet(sheetName);
	   
       // 가로열 생성
       HSSFRow row = sheet.createRow((short)corRow);      
       //param 넣기
       HSSFCell paramCell = row.createCell((short)firstCol+1);
       paramCell.setCellValue("검색어: ");
       paramCell = row.createCell((short)firstCol+2);
       paramCell.setCellValue(param.toString());
       corRow+=2;
       
       /*************************************************************/
       //일반화 헤더 테스트
       //1. 갖고온 헤더 장착
       row = sheet.createRow((short)corRow);
	   for(int i=0;i<header.length;i++) {
	       HSSFCell cell = row.createCell((short)i+corCol);
	       cell.setCellValue(header[i]);
	       //cell.setCellStyle(titleStyle);  
	        
	       log.debug("in ExcelUtil: "+header[i]);
	   }    
	   //일반화 테스트_end
       /*************************************************************/       
       
       //  ObjectList 가 비어있으면 제목만 출력 후 종료
       if(data == null) 
    	   return workbook;
        
       //  ObjectList 엑셀에 출력
       for(int i = 0; i < data.size(); i++){
           // 1번째 행은 제목이니 건너 뜀
           row = sheet.createRow((short)corRow+(i+1));

         //1.먼저 data리스트 아이템 하나를 Object로 불러온다.
           Object dto = data.get(i);      
           
           /*************************************************************/
           //일반화 테스트           
           for(int j=0;j<header.length;j++) {
        	   //get+첫글자 대문자로 만든 필드변수명을 붙여 getter 메소드를 가져온다
        	   //*********************************************//
        	   /*
        	    * getDeclaredMethod와 getMethod차이
        	    * getDeclaredMethod: 하나의 클래스에 한정됨
        	    * getMethod		   : 없으면 super것도 찾아서 갖고오긴 하는데 public만 가져옴
        	    */
        	   //Method mtd = clazz.getDeclaredMethod("get"+UpperFirst(fields[j].getName()));
        	   Method mtd = clazz.getMethod("get"+UpperFirst(header[j]));
        	   log.debug("in ExcelUtil: name: "+"get"+UpperFirst(header[j]));
        	   
        	   //셀 하나 만들고 거기에 값 넣어줌
        	   HSSFCell cell = row.createCell((short)j+corCol);
        	   if(mtd.invoke(dto)!=null) {
        		   cell.setCellValue(mtd.invoke(dto).toString());
        		   log.debug("in ExcelUtil: dto: "+mtd.invoke(dto).toString());
        	   }else {
        		   cell.setCellValue("null");
        	   }
           }
           //일반화 테스트_end
           /*************************************************************/
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
	
	/**
	 * 첫글자만 대문자로
	 * @param word
	 * @return
	 */
	public static String UpperFirst(String word) {
		return WordUtils.capitalize(word);
	}
}

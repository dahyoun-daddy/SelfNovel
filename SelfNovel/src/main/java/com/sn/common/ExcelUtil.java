package com.sn.common;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.List;
import java.util.UUID;

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
	
	private String text ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	
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
        //String changeFileName = createFile(filePath,excelFileName);
        
        //FileOutputStream fout = new FileOutputStream(filePath+"/"+changeFileName); 
        FileOutputStream fout = new FileOutputStream(filePath+"/"+excelFileName); 
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
	public HSSFWorkbook createExcel(List<?> data){
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
       nameCell.setCellValue("안녕 나는 핑크빈");
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
           nameCell.setCellValue(vo.getItm_title()); 
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

       
       
       
       
       
       
       
       
       
       
       
       
       
       
//       HSSFFont cellFont = contentCell.getCellStyle().getFont(workbook);
//       int fontStyle = java.awt.Font.PLAIN;
//       if (cellFont.getBold())
//         fontStyle = java.awt.Font.BOLD;
//       if (cellFont.getItalic())
//         fontStyle = java.awt.Font.ITALIC;
//       java.awt.Font currFont = new java.awt.Font(cellFont.getFontName(), fontStyle,
//           cellFont.getFontHeightInPoints());
//       String cellText = contentCell.getStringCellValue();
//       log.debug("cellText:" + cellText);
//       
//       AttributedString attrStr = new AttributedString(cellText);
//       attrStr.addAttribute(TextAttribute.FONT, currFont);
//       // Use LineBreakMeasurer to count number of lines needed for the text
//       //
//       FontRenderContext frc = new FontRenderContext(null, true, true);
//       LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
//       int nextPos = 0;
//       int lineCnt = 1;
//       float columnWidthInPx = sheet.getColumnWidthInPixels(firstCol);
//       log.debug("columnWidthInPx:" + columnWidthInPx);
//       while (measurer.getPosition() < cellText.length()) {
//         nextPos = measurer.nextOffset(columnWidthInPx);
//         log.debug("nextPos:" + nextPos);
//         
//         lineCnt++;
//         measurer.setPosition(nextPos);
//       }
//       log.debug("lineCnt:" + lineCnt);
//       if (lineCnt > 1) {
//         row.setHeightInPoints(
//             //sheet.getDefaultRowHeightInPoints() * lineCnt * /* fudge factor */ 1f);
//        		13 * lineCnt * /* fudge factor */ 1f / 6);
//       }

       /*****************************************************/
       // 기준단위 = 병합된 셀의 가로길이/폰트크기
       // row 수 = 전체 텍스트 글자 수/기준단위...면 참 좋겠는데 그런 픽셀단위가 편리하게 나올 리가...
       /*****************************************************/
    
//        
//       //컬럼사이즈
//       for(int i=0; i<7; i++){
//    	   if(i==0){
//    		   sheet.setColumnWidth(0,700);
//    	   }else{
//    		   sheet.autoSizeColumn((short)i);
//    		   sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512 );  // 윗줄만으로는 컬럼의 width 가 부족하여 더 늘려야 함.
//    	   }
//       }
//       
       
       return workbook;
   }
    
}

package com.sn.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import javax.swing.text.ParagraphView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordUtil {
	private static Logger log= LoggerFactory.getLogger(ExcelUtil.class);
	private String filePath;
	private String wordFileName;
	private String changFileName;
	private XWPFDocument document;
	
	private String text ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	
	public String writeWord(String filePath,String wordFileName,List<?> data)throws IOException{
		this.filePath = filePath;
		this.wordFileName =wordFileName;
		FileOutputStream out = setFile(this.filePath,this.wordFileName);
	    
		// create a new workbook
		XWPFDocument  wb =  createWord(data);
		try{
			wb.write(out);
		}finally{
			out.close();
			wb.close();
		}
		
		return wordFileName;
	}	
	
	/**
	 * setFile
	 * @param filePath
	 * @param excelFileName
	 * @return FileOutputStream
	 * @throws FileNotFoundException
	 */
    private FileOutputStream setFile(String filePath, String wordFileName)throws FileNotFoundException{
        File dir = new File(filePath); 
        if(!dir.exists()) dir.mkdirs(); 
        //File존재하면
        //String changeFileName = createFile(filePath,excelFileName);
        
        //FileOutputStream fout = new FileOutputStream(filePath+"/"+changeFileName); 
        FileOutputStream fout = new FileOutputStream(filePath+"/"+wordFileName); 
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
	public XWPFDocument createWord(List<?> data){
		document = new XWPFDocument();
		
		//create paragraph
//		XWPFParagraph paragraph = document.createParagraph();
//		//상하좌우 테두리 설정
//		paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
//		paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
//		paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
//		paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
//		
//		XWPFRun run = paragraph.createRun();
//		run.setText("테스트용 텍스트입니다.");
//		run.addBreak();
		
		
//		XWPFParagraph paragraph2 = document.createParagraph();
//		paragraph2.setBorderBottom(Borders.BASIC_BLACK_DASHES);
//		paragraph2.setBorderLeft(Borders.BASIC_BLACK_DASHES);
//		paragraph2.setBorderRight(Borders.BASIC_BLACK_DASHES);
//		paragraph2.setBorderTop(Borders.BASIC_BLACK_DASHES);
//		
//		run = paragraph2.createRun();
//		run.setText("테스트용 텍스트입니다2.");
//		run.addBreak();
		//end create paragraph
		
		
		
//		XWPFTable table = document.createTable();
//		XWPFTableRow tableTitleRow = table.getRow(0);
//		tableTitleRow.getCell(0).setText("소제목1");
//		XWPFTableRow tableSContentRow = table.createRow();
//		tableSContentRow.getCell(0).setText("내용");		
		
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		
				

	        XWPFTable table = document.createTable();
	        //테이블 가로길이 정하는 부분
	        CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
	        width.setType(STTblWidth.PCT);
	        width.setW(BigInteger.valueOf(5000));
	        
	        
	        XWPFTableRow tableRowOne = table.getRow(0);
	        tableRowOne.getCell(0).setText("   자기소개서   ");
	        tableRowOne.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
	        tableRowOne.setHeight(1000);
	        tableRowOne.getCell(0).setColor("000011");
	        tableRowOne.getCell(1);
	        setCellSpan(tableRowOne.getCell(0), 2);
	        //남아있는 찌꺼기 셀 지우는 부분
	        //참조링크: https://stackoverflow.com/questions/31563698/how-to-concatinate-2-cells-in-a-xwpftablerow
	        CTRow ctRow = tableRowOne.getCtRow(); //row being a XWPFTableRow
	        CTTc[] ctTcs = new CTTc[1]; //the column count of the remaining elements 
	        //Fill the elements leaving out the third element
	        for(int i = 0; i < ctRow.sizeOfTcArray(); i++) { 
	        	ctTcs[i] = ctRow.getTcArray(i);
	        }
	        ctRow.setTcArray(ctTcs);

	        
	        
	        for(int i=0;i<10;i++) {
	        	//소제목
		        XWPFTableRow tableRowTwo = table.createRow();
		        tableRowTwo.getCell(0).setText("   소제목:   ");
		        tableRowTwo.createCell().setText("   소제목   ");
		        tableRowTwo.getCell(0).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.PCT);
		        tableRowTwo.getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1000));
		        tableRowTwo.getCell(0).setColor("000011");
		        //setCellSpan(tableRowTwo.getCell(1), 2);

		        //본문
		        XWPFTableRow tableRowThree = table.createRow();
		        tableRowThree.getCell(0).setText(text);
		        setCellSpan(tableRowThree.getCell(0), 2);	        	
	        }
	        
		return document;
	}
	
    public static void setCellSpan(XWPFTableCell cell, int span) {
        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }
        if (cell.getCTTc().getTcPr().getGridSpan() == null) {
            cell.getCTTc().getTcPr().addNewGridSpan();
        }
        cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long) span));
    }
}

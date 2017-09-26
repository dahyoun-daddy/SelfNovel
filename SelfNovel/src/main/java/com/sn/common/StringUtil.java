package com.sn.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger log = LoggerFactory.getLogger("");
	
	//uuid생성
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/**
	 * nvl
	 * detail: replace null,"" to default Value
	 * date: 17-09-07
	 * 
	 * @param str
	 * @param defVal
	 * @return
	 */
	public static String nvl(String str,String defVal){
		String retStr = "";
		if(str == null || str.equals("") ){
			retStr = defVal;
		}else{
			retStr = str.trim();  
		}
		return retStr;
	}
	
		
	/**
	 * createParam
	 * detail: return param HashMap(setting parameter from request)
	 * request를 통째로 건네받아 parameter 이름들과 값을 전부 뽑아내는 함수이다
	 * 
	 * date: 17-09-07
	 * 
	 * @param request
	 * @return Hashtable
	 */
	public static Hashtable<String,String> createParam(HttpServletRequest request){
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		Enumeration<String> ent = request.getParameterNames();		
		Hashtable<String, String> param = new Hashtable<String, String>();
		while(ent.hasMoreElements()) {
			String key = ent.nextElement();
			param.put(key, nvl(request.getParameter(key),""));
			
			System.out.print("param: "+param.toString());
		}
		return param;
	}
	
	
	public static String createUserPaging(List<?> list, HttpServletRequest request, int bottomCount_i,
		    String url_i, String scriptName_i) {
		
		int total_num = 0;
		if(!list.isEmpty() && list!=null) {
			DTO user = (DTO)list.get(0);
			total_num = user.getTotalNo();			
		}
		int currPageNoIn_i = Integer.parseInt(nvl(request.getParameter("page_num"),"1"));
		int rowsPerPage_i = Integer.parseInt(nvl(request.getParameter("page_size"),"10"));
		return renderPaging(total_num,currPageNoIn_i,rowsPerPage_i,bottomCount_i,url_i,scriptName_i);
	}
	
	/**
	   * Paging처리 
	   * @param maxNum_i
	   * @param currPageNoIn_i
	   * @param rowsPerPage_i
	   * @param bottomCount_i
	   * @param url_i
	   * @param scriptName_i
	   * @return
	   */
	  public static String renderPaging(int maxNum_i, int currPageNoIn_i, int rowsPerPage_i, int bottomCount_i,
	    String url_i, String scriptName_i) {
	   int maxNum = 0; // 총 갯수
	   int currPageNo = 1; // 현재 페이지 번호 : page_num
	   int rowPerPage = 10; // 한페이지에 보여질 행수 : page_size
	   int bottomCount = 10; // 바닥에 보여질 페이지 수: 10

	   maxNum = maxNum_i;
	   currPageNo = currPageNoIn_i;
	   rowPerPage = rowsPerPage_i;
	   bottomCount = bottomCount_i;

	   String url = url_i; // 호출 URL
	   String scriptName = scriptName_i; // 호출 자바스크립트

	   int maxPageNo = ((maxNum - 1) / rowPerPage) + 1;
	   int startPageNo = ((currPageNo - 1) / bottomCount) * bottomCount + 1;//
	   int endPageNo = ((currPageNo - 1) / bottomCount + 1) * bottomCount;
	   int nowBlockNo = ((currPageNo - 1) / bottomCount) + 1;
	   int maxBlockNo = ((maxNum - 1) / bottomCount) + 1;

	   int inx = 0;
	   StringBuilder html = new StringBuilder();
	   if (currPageNo > maxPageNo) {
	    return "";
	   }
	   
	   html.append("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">   \n");
	   html.append("<tr>                       \n");
	   html.append("<td align=\"center\">                                                                    \n");
	   //html.append("<ul class=\"pagination pagination-sm\">                                                  \n");
	   html.append("<nav aria-label=\"Page navigation\">");
	   html.append("<ul class=\"pagination\">");
	   // <<
	   
	   if (nowBlockNo > 1 && nowBlockNo <= maxBlockNo) {
	    html.append("<li>");
	    html.append("<a href=\"javascript:" + scriptName + "( '" + url+ "', 1 );\">  \n");
	    html.append("&laquo;   \n");
	    html.append("</a>      \n");
	    html.append("</li>");
	   }
	   

	   // <
	   
	   if (startPageNo > bottomCount) {
	    html.append("<li>");
	    html.append("<a href=\"javascript:" + scriptName + "( '" + url + "'," + (startPageNo - 1)+ ");\"> \n");
	    html.append("<        \n");
	    html.append("</a>     \n");
	    html.append("</li>");
	   }
	   


	   // 1 2 3 ... 10 (숫자보여주기)
	   for (inx = startPageNo; inx <= maxPageNo && inx <= endPageNo; inx++) {
	    
	    if (inx == currPageNo) {
	  html.append("<li class=\"active\">");   
	  html.append("<a href=\"javascript:" + scriptName + "('" + url + "'," + inx+ ");\" >" + inx + "</a> &nbsp;&nbsp; \n");
	     html.append("</li>");
	    } else {
	  html.append("<li>");
	     html.append("<a href=\"javascript:" + scriptName + "('" + url + "'," + inx+ ");\" >" + inx + "</a> &nbsp;&nbsp; \n");
	     html.append("</li>");
	    }
	   }
	   
	   // >
	   if (maxPageNo >= inx) {
	    html.append("<li>"); 
	    html.append("<a href=\"javascript:" + scriptName + "('" + url + "',"+ ((nowBlockNo * bottomCount) + 1) + ");\"> \n");
	    html.append(">                       \n");
	    html.append("</a>              \n");
	    html.append("</li>");
	   }

	   // >>
	   if (maxPageNo >= inx) {
	    html.append("<li>");
	    html.append("<a href=\"javascript:" + scriptName + "('" + url + "'," + maxPageNo+ ");\">      \n");
	    html.append("&raquo;     \n");
	    html.append("</a>    \n");
	    html.append("</li>");
	   }

	   html.append("</td>   \n");
	   html.append("</tr>   \n");
	   html.append("</ul>");
	   html.append("</nav>");
	   html.append("</table>   \n");      

	   return html.toString();
	  }
}

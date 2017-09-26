package com.sn.common;

public class OrdersUtil {
	
	
	public static String makeStateBtn(String ord_state) {
		String ord_stateNVL = StringUtil.nvl(ord_state, "");
		
		if(ord_stateNVL.equals("10")) {
			//첨삭 거절
			return "<input type='button' name='rejectTest' value='거절' disabled='disabled'/>";
		}else if(ord_stateNVL.equals("20")) {
			//첨삭 대기
			return "  <input type='button' name='signTest' value='수락'/>\r\n" + 
				   "  <input type='button' name='rejectTest' value='거절'/>	";
		}else if(ord_stateNVL.equals("30")) {
			//첨삭중
			return "<input type='button' name='signTest' value='첨삭 완료'/>";
		}else if(ord_stateNVL.equals("40")) {
			//첨삭 완료
			return "<input type='button' name='signTest' value='첨삭 완료' disabled='disabled'/>";
		}else if(ord_stateNVL.equals("50")) {
			//의뢰 완료
			return "<input type='button' name='signTest' value='의뢰 완료' disabled='disabled'/>";
		}else {
			return "";
		}
	}
}

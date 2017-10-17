package com.sn.common;

import java.lang.reflect.Method;
import java.net.InetAddress;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sn.log.domain.LogVO; 
import com.sn.log.service.LogSvc;
/**
 * TransactionAdvice 
 * detail : 트랜잭션 처리 : Advice
 * 최초작성: 2017-09-22
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class TransactionAdvice implements MethodInterceptor {  
	/***********************************************/
	//field
	/***********************************************/
	private static Logger log=Logger.getLogger(TransactionAdvice.class);
    PlatformTransactionManager transactionManager;
    /* for save log */
    LogSvc logSvc; 
    
    
	/***********************************************/
	//setter
	/***********************************************/
    /**
     * setLogSvc
     * TransactionAdvice는 mvc패턴의 일부가 아니기 때문에(상단의 annotation이 없으므로), 별도의 setter로 넣어주어야 동작한다.
     * 최초작성: 2017-09-22
     * @param logSvc
     */
    public void setLogSvc(LogSvc logSvc) {
    	this.logSvc = logSvc; 
    }
    
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	/***********************************************/
	//method
	/***********************************************/
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		InetAddress ip = InetAddress.getLocalHost();
		Object ret 	 = null;
		Method mtd 	 = null;
		Object[] arg = null;
		String param = "";
		long startTime = 0;
		long endTime = 0;
		long time = 0;
		
		TransactionStatus status=
		this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			//시간측정 시작시간
			startTime = System.currentTimeMillis();			
			
			log.debug("******************in transaction******************");
			ret = invocation.proceed();
			mtd = invocation.getMethod();
			arg = invocation.getArguments();
			this.transactionManager.commit(status);
			log.debug("ret: "+ret.toString());
			log.debug("mtd: "+mtd.toString());
			for(int i=0;i<arg.length;i++) {
				param += "arg["+i+"]: "+arg[i].toString()+",";
			}
			log.debug("******************after commit********************");
			
			//시간측정 끝난시간
			endTime = System.currentTimeMillis();
			time 	= (endTime-startTime);
			log.debug("성능측정 - 소요시간: "+time+"ms");
			setLog(new LogVO(mtd.toString(), "", param, ip.getHostAddress(), "",time));
			
			return ret;
		}catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			log.debug("******************after rollback********************");
			endTime = System.currentTimeMillis();
			time 	= (endTime-startTime);
			log.debug("성능측정 - 소요시간: "+(endTime-startTime)+"ms");
			setErrorLog(new LogVO(mtd.toString(), "", arg.toString(), ip.getHostAddress(), e.getMessage(),time));
			
			throw e;
		}
	}
	
	private void setLog(LogVO inVO) {
		log.debug("invo: "+inVO.toString());
		this.logSvc.debug(inVO);
	}
	
	private void setErrorLog(LogVO inVO) {
		log.debug("invo: "+inVO.toString());
		this.logSvc.error(inVO);
	}
}

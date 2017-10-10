package com.sn.common;

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
		TransactionStatus status=
		this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			//시간측정 시작시간
			long startTime = System.currentTimeMillis();			
			
			log.debug("******************in transaction******************");
			Object ret = invocation.proceed();
			this.transactionManager.commit(status);
			log.debug("ret: "+ret.toString());
			log.debug("******************after commit********************");
			
			//시간측정 끝난시간
			long endTime = System.currentTimeMillis();
			log.debug("성능측정 - 소요시간: "+(endTime-startTime)+"ms");
			this.logSvc.debug(new LogVO("클래스패스", "성능측정 - 소요시간: "+(endTime-startTime)+"ms", "파람", "아이디", ""));
			
			return ret;
		}catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			log.debug("******************after rollback********************");
			this.logSvc.error(new LogVO("클래스패스", "에러", "파람", "아이디", e.getMessage()));
			
			throw e;
		}
	}

}

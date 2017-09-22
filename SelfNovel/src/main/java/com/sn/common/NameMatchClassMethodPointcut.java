package com.sn.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

/**
 * NameMatchClassMethodPointcut 
 * detail : 포인트컷
 * 최초작성: 2017-09-22
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
	private static Logger log = LoggerFactory.getLogger(NameMatchClassMethodPointcut.class);
	
	public void setMappedClassName(String mappedClassName) {
		this.setClassFilter(new SimpleClassFilter(mappedClassName));
	}
	
	static class SimpleClassFilter implements ClassFilter{
		String mappedName;
		private SimpleClassFilter(String mappedName) {
			this.mappedName = mappedName;
		}
		@Override
		public boolean matches(Class<?> clazz) {
			log.debug("1.pointcut mappedName: "+mappedName);
			log.debug("2.pointcut clazz.getSimpleName(): "+clazz.getSimpleName());
			return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
		}
		
	}
}

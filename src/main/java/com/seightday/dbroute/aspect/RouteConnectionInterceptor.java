package com.seightday.dbroute.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.seightday.dbroute.datasource.DbContextHolder;

@Aspect
@Component
public class RouteConnectionInterceptor implements Ordered {
    private static final Logger log = LoggerFactory.getLogger(RouteConnectionInterceptor.class);

	private int order;

	@Value("20")
	public void setOrder(int order) {
        log.debug("RouteConnectionInterceptor >>> setOrder = {}",order);
        this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

    @Pointcut(value="execution(public * *(..))")
    public void anyPublicMethod() { }

	@Around("@annotation(routeConnection)")
	public Object proceed(ProceedingJoinPoint pjp,
			RouteConnection routeConnection) throws Throwable {
		try {
			String value = routeConnection.value();
			log.debug("route connection is {}",value);
            DbContextHolder.setDb(value);
            return pjp.proceed();
		} finally {
            DbContextHolder.clearDb();
		}
	}
}

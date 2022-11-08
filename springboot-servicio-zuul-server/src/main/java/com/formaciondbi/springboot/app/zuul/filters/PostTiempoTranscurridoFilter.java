package com.formaciondbi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest httpServletRequest = ctx.getRequest();

		log.info(" entrando en post filter ");

		Long tiempoInicio = (Long) httpServletRequest
				.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long timepoTranscurrido = tiempoFinal - tiempoInicio;

		log.info(String.format("Tiempo transcurrido en segundos %s seg.", 
				timepoTranscurrido.doubleValue() / 1000.00));
		log.info(String.format("Tiempo transcurrido en milegundos %s ms.", 
				timepoTranscurrido));

		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}

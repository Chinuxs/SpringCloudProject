package com.formaciondbi.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered{
	
	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("ejecutando el filtro pre");
		//Empezamos a manejar el PRE
		//en general los request son inmutable pero con esta funcionalidad podriamos modificarlo
		exchange.getRequest().mutate().headers(h -> h.add("token","123456"));
		
		//Hasta antes del return manejamos lo que seria el pre filter - END PRE
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			logger.info("ejecutando el filtro post");
			
			//Expresiones landas, programacion funcional del core de Java 8
			//un IF usando Landas....
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("token", valor);
			});
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		// Orden = -1(Alta prioridad) --> el response es de soloLectura, no nos dejaria modificar el response
		// X > 0, mayor que cero para ser de baja prioridad.
		return 10;
	}
	
	
}

package com.formaciondbi.springboot.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion>{
	

	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);
	
	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}	
	
	@Override
	public String name() {
		// Puede cambiar o setear el nombre del filtro que voy a usar en el archivo YML
		return "EjemploCookie";
	}


	@Override
	public List<String> shortcutFieldOrder() {
		// este metodo nos ayuda a setear el orden de los campos a utilizar en el archivo yml
		//Debemos seguir el mismos orden para que funcione.
		return Arrays.asList("mensaje","cookieNombre","cookieValor");
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		// vamos a cambiar a otra implementacion del mismo filtro GatewayFilter pero Ordenado.
		//Para eso necesitamos una clase nueva
		//return (exchange, chain) -> {
		return new OrderedGatewayFilter((exchange, chain) -> {
			logger.info("ejecutando pre gateway filter factory: "+config.mensaje);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() ->{
				//tengamos en cuenta que esta evaluando si estan los valores presentes, si no estan en el file YML o estan mal setado no crearia la cookie.
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				
				logger.info("ejecutando post gateway filter factory: "+config.mensaje);
				
			}));
		}, 2);
	}
	
	
	
	public static class Configuracion {
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}		
	}	
	
}

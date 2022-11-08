package com.formaciondbi.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

/**
 * @author Administrator
 * @description - en lugar de usar un file de mapeo xml, usa un clase anotado con el name-space que se especifica
 * Configuro el contenedor de Beans via una clase java.
 */
@Configuration
public class AppConfig {
	
	/**
	 * LoadBalanced, le indicamos que ribbon tiene que balancear ese servicio 
	 * y trabaja con restTemplate para tener esas instancias
	 */
	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * @return id >> hace referencia al CBFactory que seteamos en ItemController: 
	 * cbFactory.create("items")
	 * 
	 * Esta configuracion va a quedar descartada una vez que se setee el application.yml con Resillience4J
	 * va a tomar como principal lo que encuentre en ese archivo.
	 */
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id ->{
			return new Resilience4JConfigBuilder(id)
						.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10)
							.failureRateThreshold(50)
							.waitDurationInOpenState(Duration.ofSeconds(10L))
							.permittedNumberOfCallsInHalfOpenState(5)
							.slowCallRateThreshold(50)
							.slowCallDurationThreshold(Duration.ofSeconds(2L)) // aunque no tire ningun error por es solo una llamada lenta, se contabiliza como un falla interna
							.build())							
						.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2L)).build())// Aqui seteo el limite de timeout
						.build();			
		});
		
	}

}

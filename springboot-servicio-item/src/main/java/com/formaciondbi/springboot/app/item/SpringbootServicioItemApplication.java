package com.formaciondbi.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Administrator
 * @desc: commentamos el anotetion de ribbon por que eureka cliente ya viene con
 *        ribbon y de esto se va a encargar el servidor de eureka.
 *        //@RibbonClient(name="servicio-productos")
 *        
 * El balanceador de carga usan el algoritmo round robin:
 * 
 * 
 *        --> EnableCircuitBreaker link:
 *        https://apiumhub.com/es/tech-blog-barcelona/patron-circuit-breaker/
 * 
 * 
 * 
 */

/**@EnableCircuitBreaker
 * @desc: se quita esta dependiencia por que ahora vamos a usar Resilience4J
 * @author Administrator
 *
 */

/**@@EnableEurekaClient
 * @desc: esta configuracion es opcional ya que con solo tener la dependencia de que es un cliente de eureka ya estariamos
 * @author Administrator
 *
 */

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}

}

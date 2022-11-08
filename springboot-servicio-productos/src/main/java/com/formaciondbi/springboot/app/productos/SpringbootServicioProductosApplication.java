package com.formaciondbi.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Administrator
 * @EntityScan({"com.formaciondbi.springboot.app.commons.models.entity"}) -->  
 * Como Spring por defecto escanea los beans definido dentro del mismo paquete y antes de meterlos en el contexto
 * vamos a necesitar indicarle que tambien escanee aparte del package por defecto que tambien tome uno que le indiquemos dentro de las llaves.
 */
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formaciondbi.springboot.app.commons.models.entity"})
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}

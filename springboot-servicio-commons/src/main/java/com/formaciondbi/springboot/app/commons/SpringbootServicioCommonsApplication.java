package com.formaciondbi.springboot.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Administrator
 * @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) --> por defecto Spring va a tratar de levantar una base 
 * y buscar un driver de conexion por eso tenemos que indicarle que excluya la auto configuracion de la base de datos.
 * 
 * Por otro lado tambien borramos el metodo Main, por que este es solo un proyecto de librerias o Utilerias.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServicioCommonsApplication {
	
	//Notar que quitamos el metodo main ya que esta no es una aplicacion, sino un proyecto 
	//comun para otros servicios

}

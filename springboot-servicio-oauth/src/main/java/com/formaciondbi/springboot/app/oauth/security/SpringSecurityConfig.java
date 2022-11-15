package com.formaciondbi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author cesar.augusto.romero
 * Esta clase nos permite configurar la seguridada en base a nuestras propias reglas de negocio, es decir
 * puedo configurar una forma de obtener los detalles del usuario, utilizando un DAO o un services en particular y explicandole a Spring Security:
 * Che, toma este objeto o clase customizada y reemplazala por tu elemento default.
 *
 */
@Configuration		
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
		
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEventPublisher eventPublisher;

	/**
	 * @return
	 * Este punto es interesante, crea una instancia de BCrypt, y al anotarlo con @Bean sube la instancia al contenedor de Spring, esto permite que cualquier
	 * otra clase pueda inyectarlo
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * haciendo una sobre escritura de la configuracion, puedo detallar que reemplaza el objeto userDetailServices por uno custom
	 * 
	 * @Autowired - este autowire permite que se inyecte el objeto AuthenticationManagerBuilder, y manipularlo en le metodo
	 */
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder())
		.and().authenticationEventPublisher(eventPublisher);// Le indicamos como manejar los eventas de falla o exito del login del usuario
	}

	/**
	 *
	 * en este punto hace lo mismos que con el BCrypt, mete un bean en el contenedor para usarlo despues.
	 *
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}



	

}

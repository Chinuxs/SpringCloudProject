package com.formaciondbi.springboot.app.zuul.oauth;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author cesar.augusto.romero
 * Permite establecer un servidor de recursos => @EnableResourceServer
 *
 */
@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	/**
	 * configurar/validar el token, deberemos usar los mismo Beans o metodos para gestionar el token en el servidor de autorizacion
	 * Necesitamos tener la misma firma del token para validarlo por eso se copia igual la SecretKey
	 * 
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	/**
	 * Gestiona el acceso a los endpoints, pefiles, roles a cada API
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/listar", "/api/items/listar", "/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", 
				"/api/items/ver/{id}/cantidad/{cantidad}", 
				"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}

	/**
	 * permite la interaccion con frontEndApp y el intercambio en distintos dominios.
	 *  Luego tendre que agregar esta configuracion de CORS a la seguridad del nuestro servidor de recursos en los endpoints de cada Servicio.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		// "*" nos permite agregar de forma automatica cualquier origen
		// Tambien permite indicar el origen o root de las aplicaciones frontend que van a utilizar nuestras API, x ej: Angular, react, etc
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(true);
		// le indico que headers tiene permitido enviarme las frontend app a mi Gateway.
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization","Content-type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		
		
		return source;
	}
	
	/**
	 * @return
	 * @Description: no global dentro del proyecto en este caso de zuul, pero me refería a que se ejecuta en un filtro 
	 * http para todas las rutas en zuul de forma global, que al final son las rutas de los demás microservicios ya que es un gateway, 
	 * pero siempre pasando por zuul.
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	//tenemos que tener estos 2 metodos iguales que en la clase que implemento el authorization server:
	// com.formaciondbi.springboot.app.oauth.security.AuthorizationServerConfig --> debemos tener la misma clave para manejar el token
	@Bean
	public JwtTokenStore tokenStore() {		
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(jwtKey.getBytes()));
		return tokenConverter;
	}
	
	
	
}

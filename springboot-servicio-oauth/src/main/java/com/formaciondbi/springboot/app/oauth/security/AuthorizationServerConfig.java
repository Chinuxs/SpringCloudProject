package com.formaciondbi.springboot.app.oauth.security;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired(required=true)
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	/**
	 * Son los permisos que van a tener nuestros endpoints del servidor de autorizacion Oauth2
	 * para generar el token y validar el token
	 * 
	 * --> se le da seguridad a los endpoints.
	 * --> permitAll() : permite que todos accedan al endpoint POST: /oauth/token
	 * --> isAuthenticated(): estos ultimos 2 metodos son de spring-security
	 * --> todo estos accesos estan asegurados con una autenticacion basica en el header
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		;
	}

	/**
	 * Este nos permite determinar que clientes van a poder acceder a la API
	 * seteando su client_id, secret_id
	 * y va a autenticar no solo los clientes que accedern o se logean a nuestro backend
	 * sino que tambien tiene una doble autenticacion por parte de la aplicacion que la utiliza.
	 * 
	 * --> refresh_token: se obtiene un nuevo token antes que caduque el original
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))  //credenciales de la aplicacion
		.scopes("read","write")
		.authorizedGrantTypes("password","refresh_token") // credenciales del login de usuario
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600)
		;
	}

	/**
	 * Me permite setear el endpoint, por donde va a recibir el pedido de creacion de token y validacion
	 * en aouth/token
	 * Por ende se encarga de la creacion y almacenarmiento del mismo, como de su encoded.
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain);
	}
	@Bean
	public JwtTokenStore tokenStore() {		
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(env.getProperty("config.security.oauth.jwt.key").getBytes()));
		return tokenConverter;
	}
	
	
}

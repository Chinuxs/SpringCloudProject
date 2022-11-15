package com.formaciondbi.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formaciondbi.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;
import feign.FeignException.FeignClientException;

/**
 * @author cesar.augusto.romero
 * Esta clase va a manejar el fracaso o el exito de la autenticacion/autorizacion
 * 
 * Problematica: como en el request tenemos 2 usuarios, la app y el usuario registrado, va a intentar correr 2 veces este evento por cada account en el request
 * 
 *
 */
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{
	
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;

	/**
	 * Claramente este metodo nos ayuda a hacer algo luego que el usuario haya sido logeado correctamente.
	 * 
	 */
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		//en teoria atrapa la instancia del cliente webapp
		//esquivamos el usuario de la app
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
		//if (authentication.getName().equalsIgnoreCase("frontendapp")) {	
			return;
		}
		
		UserDetails user = (UserDetails)authentication.getPrincipal();
		String mensaje = "Success Login: "+ user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);
		
		//reseteamos los intentos fallidos de login del usuario.
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

	/**
	 *  Por el otro lado, este metodo nos permite, hacer algo luego que el registro del usuario haya fallado
	 */
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		
		String mensaje = "Error en el login: "+ exception.getMessage();
		log.error(mensaje);
		System.out.println(mensaje);
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensage);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			log.info("Intentos actual es de : "+usuario.getIntentos());
			
			usuario.setIntentos(usuario.getIntentos()+1);
			
			log.info("Intentos despues es de : "+usuario.getIntentos());
			
			if (usuario.getIntentos()>=3) {
				log.error(String.format("El usuario %s deshabilitado por maximos intentos", authentication.getName()));
				usuario.setEnable(false);
			}
			
			usuarioService.update(usuario, usuario.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
	}

}

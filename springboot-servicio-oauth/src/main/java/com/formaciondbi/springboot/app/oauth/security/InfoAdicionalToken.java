package com.formaciondbi.springboot.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formaciondbi.springboot.app.oauth.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private IUsuarioService iUsuarioService;
	
	/**
	 * esto serian los claims, donde se agregan campos al token.
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<String, Object>();
		
		Usuario usuario = iUsuarioService.findByUsername(authentication.getName());
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("correo", usuario.getEmail());
		
		//Accedo a un clase concreta que me habilita la opcion de setear info adicional
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		// y devuelvo el access token modificado
		return accessToken;
	}

}

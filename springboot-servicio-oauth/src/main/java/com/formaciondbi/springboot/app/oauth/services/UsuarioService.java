package com.formaciondbi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formaciondbi.springboot.app.oauth.clients.UsuarioFeignClient;

import brave.Tracer;
import feign.FeignException;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService{
	
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	UsuarioFeignClient client;
	
	@Autowired 
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			Usuario usuario = client.findByUsername(username);
			
			
			List<GrantedAuthority> authorities = usuario.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());
			
			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnable(), true, true, true, authorities);
			
		} catch (FeignException e) {
			String error = "Error en el login, no existe el usuario '"+username+"' en el sistema";
			log.error(error);
			tracer.currentSpan().tag("error.mensaje", error+": "+e.getMessage());
			throw new UsernameNotFoundException("Error en el login, no existe el usuario '"+username+"' en el sistema");
		}
		

	}

	/**
	 * creo una interface solo para sacar un metodo aparte y podes solo atraves de la Interface obtener el metodo concreto.?
	 */
	@Override
	public Usuario findByUsername(String username) {
		Usuario usuario = client.findByUsername(username);
		return usuario;
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}
	
	
	

}

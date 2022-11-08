package com.formaciondbi.springboot.app.usuarios.models.dao;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;

@RepositoryRestResource(path="usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
	
	/**
	 * JPQL = select u from Usuarios where u.username = ?
	 * https://docs.spring.io/spring-data/jpa/docs/2.5.6/reference/html/#repositories.query-methods
	 * 
	 */
	@RestResource(path="buscar-username")
	public Usuario findByUsername(@Param("nombre") String username); 
	
	/**
	@Query("select u from usuarios u where u.username=?1")
	public Usuario obtenerPorUsername (String name);**/

}

package com.formaciondbi.springboot.app.usuarios.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;

/**
 * @author cesar.augusto.romero
 * Crud completo en forma automatica con un componente de spring,exportar el crud repositorio a un endpoind
 * sin tener que escribir el controlador, ni el services, ideal en microservicios
 *  --> Agregar como dependencia: Rest Repositories (data-rest) => @RepositoryRestResource
 *  
 *  Todo se exporta a nuestra API Rest.Ya podriamos routear los servicios los endpoints en Zuul, 
 *  para tener un servicio escalable y que pase por un balanceador de cargas
 *
 */
@RepositoryRestResource(path="usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {
	
	/**
	 * JPQL = select u from Usuarios where u.username = ?
	 * https://docs.spring.io/spring-data/jpa/docs/2.5.6/reference/html/#repositories.query-methods
	 * 
	 */
	@RestResource(path="buscar-username")
	public Usuario findByUsername(@Param("username") String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);

}

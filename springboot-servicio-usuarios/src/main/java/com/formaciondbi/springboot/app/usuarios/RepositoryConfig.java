package com.formaciondbi.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Role;
import com.formaciondbi.springboot.app.commons.usuarios.models.entity.Usuario;

/**
 * @author cesar.augusto.romero
 * para exponer los ID de las entidades necesitamos implementar RepositoryRestConfigurer
 * nos permite manejar todo el repositorio, tenemos que implementar el metodo configureRepositoryRestConfiguration
 * 
 * con el metodo: exposeIdsFor, indicamos la entidades que necesitan exponer sus ids.
 * 
 *
 */
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Usuario.class, Role.class);
	}

}

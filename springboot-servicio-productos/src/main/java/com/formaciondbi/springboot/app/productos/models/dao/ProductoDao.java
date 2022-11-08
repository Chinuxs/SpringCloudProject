package com.formaciondbi.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.formaciondbi.springboot.app.commons.models.entity.Producto;

/**
 * @author Administrator
 * DAO = Repository, son lo mismo podria poner el mismo nombre como nombre del paquete.
 *Al usar Spring-Data, ya nos genera la solucion de no tener que escribir el CRUD de cada 
 *entidad o repositorio.
 *
 *Por otro lado esta interface no tienen ningun anottation ya que CrudRepositoriy si 
 *esta anotado comoun bean de spring
 */
public interface ProductoDao extends CrudRepository<Producto, Long>{

}

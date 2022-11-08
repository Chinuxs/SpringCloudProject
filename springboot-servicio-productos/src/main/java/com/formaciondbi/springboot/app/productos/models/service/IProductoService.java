package com.formaciondbi.springboot.app.productos.models.service;

import java.util.List;

import com.formaciondbi.springboot.app.commons.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> finAll();
	public Producto findById(Long id);
	
	public Producto save (Producto producto);
	public void deleteById(Long id) ;

}

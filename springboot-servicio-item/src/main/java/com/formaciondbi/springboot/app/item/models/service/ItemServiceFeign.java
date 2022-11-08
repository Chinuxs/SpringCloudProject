package com.formaciondbi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formaciondbi.springboot.app.item.clientes.ProductoClienterRest;
import com.formaciondbi.springboot.app.item.models.Item;
import com.formaciondbi.springboot.app.commons.models.entity.Producto;

/**
 * @author Administrator
 * @Descrip: puedo usar un nombre de bean en miniscula para indicarle a Spring cual es la implementacion posta de la interfase
 * Pero tambien puede indicarselo utilizando la notacion @primary este flag le indica que seria la implementacion primaria a utilizar.
 */
@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService{
	
	@Autowired
	private ProductoClienterRest productoClienterRest;

	@Override
	public List<Item> findAll() {
		return productoClienterRest.listar().stream().map(p-> new Item(p,1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item (productoClienterRest.detalle(id),cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		return productoClienterRest.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		return productoClienterRest.editar(producto, id);
	}

	@Override
	public void delete(Long id) {
		productoClienterRest.eliminar(id);
	}


}

package com.formaciondbi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formaciondbi.springboot.app.commons.models.entity.Producto;
import com.formaciondbi.springboot.app.productos.models.dao.ProductoDao;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> finAll() {
		
		return (List<Producto>)productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		// desde la vesion Java 8 podemos tener otros metodos para validar 
		//la existencia del resultado
		//en este caso sino viene, puede devolver otro objeto, en este caso null
		return productoDao.findById(id).orElse(null);
	}

	/**
	 *@Transactional --> sin el readOnly por que ahora son de escritura
	 *productoDao hereda de CRUDReposity implementacion de CRUD de springdata, 
	 *viene construido por debajo y definido todas las operaciones de CRUD.
	 */
	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	/**
	 *
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		productoDao.deleteById(id);
		
	}

}

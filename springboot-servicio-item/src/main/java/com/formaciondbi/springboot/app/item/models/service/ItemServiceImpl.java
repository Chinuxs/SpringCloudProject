package com.formaciondbi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formaciondbi.springboot.app.item.models.Item;
import com.formaciondbi.springboot.app.commons.models.entity.Producto;

/**
 * @author Administrator
 * @Service -- lo primero es que es una anotacion hija de component con todo lo que eso implica
 * pero por otro lado la palabra Service es una semantica para indicar que la clase es una fachada
 * para acceder a los datos y se traduce desde la definicion de Bussines Service Facade
 *
 */
@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays
				.asList(clienteRest
				.getForObject("http://servicio-productos/listar", Producto[].class));

		// Bondades de Java 8, streams & Landas, y collectors? que hace?
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		Producto producto = clienteRest.
				getForObject("http://servicio-productos/ver/{id}", Producto.class,
				pathVariables);

		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		HttpEntity<Producto> body = new  HttpEntity<Producto>(producto);
		ResponseEntity<Producto> responseEntity = clienteRest.exchange("http://servicio-productos/crear", HttpMethod.POST, body, Producto.class);
		Producto productoResponse = responseEntity.getBody();
		return productoResponse;
	}
	
	@Override
	public Producto update(Producto producto, Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<Producto> body = new  HttpEntity<Producto>(producto);
		ResponseEntity<Producto> responseEntity = clienteRest.exchange("http://servicio-productos/editar/{id}", HttpMethod.PUT, body, Producto.class, pathVariables);
		Producto productoResponse = responseEntity.getBody();
		return productoResponse;
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		clienteRest.delete("http://servicio-productos/eliminar/{id}",pathVariables);
	}

}

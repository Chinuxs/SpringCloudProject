package com.formaciondbi.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formaciondbi.springboot.app.commons.models.entity.Producto;
import com.formaciondbi.springboot.app.productos.models.service.IProductoService;

/**
 * @author Administrator
 *@RestController --> permite convertir a JSON los metodos handler del controlador
 */
@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	
	/**
	 * Value nos permite inyectar algun valor seteada en el application.properties.
	 */
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.finAll().stream().map(producto ->{
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException{
		
		
		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado!");
		}
		
		//Simulamos un timeout, por defecto tarda 1seg, y se considera como un error
		//Por lo tanto entra en la maquina de estados de Resilien4J >> CircuitBreak
		if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
		
		/*
		 * if (true) { //Simulamos un timeout de 2 Sec. try { Thread.sleep(8000L); }
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		
		
		return producto;		
	}
	
	/**
	 * @param producto --> se va a poblar con un request JSON 
	 * y el binding lo hacemos con Request Body anottation
	 * @return
	 * @ResponseStatus - Tipo de respuesta (code 201)
	 */
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar (@RequestBody Producto producto, @PathVariable Long id) {
		
		Producto productoDB = productoService.findById(id);
		
		productoDB.setNombre(producto.getNombre());
		productoDB.setPrecio(producto.getPrecio());
		
		return productoService.save(productoDB);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar (@PathVariable Long id) {
		productoService.deleteById(id);
	}
	
}

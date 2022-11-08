package com.formaciondbi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.formaciondbi.springboot.app.commons.models.entity.Producto;

/**
 * @author Administrator
 * @desc: otra forma de crear un Rest Template pero con Feign, donde basicamente seteamos 1 vez los endopints
 * y la API a la que queres llegar y detallo los metodos del servicio que necesito obtener.
 * 
 * Notar que la firma de los metodos tiene que ser identica a la firma del metodo en la API que deseamos consumir.
 * Aqui el binding entre el cliente rest de feign se bindea de forma abstracta con la API que seteamos.
 */
@FeignClient(name= "servicio-productos")
public interface ProductoClienterRest {
	
	@GetMapping("/listar")
	public List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);
	
	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping("/editar/{id}")
	public Producto editar (@RequestBody Producto producto, @PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar (@PathVariable Long id);

}

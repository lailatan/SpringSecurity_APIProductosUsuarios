package com.example.springsecurity_apiproductosusuarios.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Crear una API que gestione el ciclo de vida de los productos de una tienda.

Los productos tienen: nombre:string, precio:double, stock:integer.

La API permite a todos los usuarios registrados:
- listar todos los productos
- listar un producto por ID
- listar todos los productos que contengan un string en el nombre

Además, los administradores pueden:
- modificar un producto existente
- crear nuevos productos
- eliminar un producto por ID

Utilizar autenticación básica en la API.

 */
@RestController
@RequestMapping("api/productos")

public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> buscarProductos(){
        return productoService.findAll();
    }

    @GetMapping("/buscar/{nombre}")
    @PreAuthorize("hasAnyAuthority('producto:write','producto:read')")
    public List<Producto> buscarProductosPorNombre(@PathVariable String nombre){
        return productoService.findAllByNombre(nombre);
    }

    @GetMapping("/{producto_id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_SELLER')")
    public Producto buscarProductosPorId(@PathVariable Integer producto_id){
        return productoService.findById(producto_id);
    }

    @DeleteMapping("/{producto_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void borrarProductosPorId(@PathVariable Integer producto_id){
        productoService.borrarById(producto_id);
    }

    @PutMapping(path="/guardar", consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private void modificarProducto(@RequestBody Producto producto){
        productoService.updateProducto(producto);
    }

    @PostMapping(path="/guardar", consumes = "application/json")
    @PreAuthorize("hasAuthority('producto:write')")
    private void nuevoProducto(@RequestBody Producto producto){
        productoService.saveProducto(producto);
    }

    @GetMapping("/mi-rol")
    public String getMiRol(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getAuthorities().toString();
    }
}

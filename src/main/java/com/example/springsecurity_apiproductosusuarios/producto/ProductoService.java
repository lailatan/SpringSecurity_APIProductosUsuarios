package com.example.springsecurity_apiproductosusuarios.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findAllByNombre(String nombre) {
        return productoRepository.findProductosByNombreContaining(nombre);
    }

    public Producto findById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void borrarById(Integer id) {
        productoRepository.deleteById(id);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Producto producto) {
        if(producto.getProducto_id()!=null) {
            Producto productoExistente = productoRepository.findById(producto.getProducto_id()).orElse(null);
            if (productoExistente != null) {
                if (producto.getNombre() != null) productoExistente.setNombre(producto.getNombre());
                if (producto.getPrecio() != null) productoExistente.setPrecio(producto.getPrecio());
                if (producto.getStock() != null) productoExistente.setStock(producto.getStock());
                return productoRepository.save(productoExistente);
            }else producto.setProducto_id(null);
        }
        return productoRepository.save(producto);
    }
}

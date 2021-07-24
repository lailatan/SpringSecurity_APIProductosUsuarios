package com.example.springsecurity_apiproductosusuarios.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    List<Producto> findProductosByNombreContaining(String nombre);
}

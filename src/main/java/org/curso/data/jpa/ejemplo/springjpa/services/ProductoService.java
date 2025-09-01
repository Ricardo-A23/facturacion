package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    List<Producto> findAll();
    Producto findById(Long id);
    void save(Producto producto);
    void deleteById(Long id);
    Page<Producto> findAll (Pageable pageable);
    List<Producto> findByNombre(String term);
}

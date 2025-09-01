package org.curso.data.jpa.ejemplo.springjpa.services;


import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFacturaService {

    List<Factura> findAll();
    Factura findById(Long id);
    void save(Factura factura);
    void deleteById(Long id);
    Page<Factura> findAll (Pageable pageable);
}

package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();
    Cliente findById(Long id);
    void save(Cliente cliente);
    void deleteById(Long id);
    Page<Cliente> findAll (Pageable pageable);
    List<Producto> findByNombre(String term);
    Cliente fetchByIdWithFacturas(Long id);
}

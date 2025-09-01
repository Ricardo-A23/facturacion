package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.curso.data.jpa.ejemplo.springjpa.repositories.FacturaReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private FacturaReporsitory facturaReporsitory;

    @Override
    public List<Factura> findAll() {
        return (List<Factura>) facturaReporsitory.findAll();
    }

    @Override
    public Factura findById(Long id) {
        return facturaReporsitory.findById(id).get();
    }

    @Override
    public void save(Factura factura) {
        facturaReporsitory.save(factura);
    }

    @Override
    public void deleteById(Long id) {
        facturaReporsitory.deleteById(id);
    }

    @Override
    public Page<Factura> findAll(Pageable pageable) {
        return facturaReporsitory.findAll(pageable);
    }
}

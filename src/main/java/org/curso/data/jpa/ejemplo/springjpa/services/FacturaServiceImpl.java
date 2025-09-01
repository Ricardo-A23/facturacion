package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.curso.data.jpa.ejemplo.springjpa.repositories.FacturaReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private FacturaReporsitory facturaReporsitory;

    @Transactional(readOnly=true)
    @Override
    public List<Factura> findAll() {
        return (List<Factura>) facturaReporsitory.findAll();
    }

    @Transactional(readOnly=true)
    @Override
    public Factura findById(Long id) {
        return facturaReporsitory.findById(id).get();
    }

    @Transactional()
    @Override
    public void save(Factura factura) {
        facturaReporsitory.save(factura);
    }

    @Transactional()
    @Override
    public void deleteById(Long id) {
        facturaReporsitory.deleteById(id);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Factura> findAll(Pageable pageable) {
        return facturaReporsitory.findAll(pageable);
    }

    @Transactional(readOnly=true)
    @Override
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaReporsitory.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }
}

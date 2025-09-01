package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.curso.data.jpa.ejemplo.springjpa.repositories.ClienteRepository;
import org.curso.data.jpa.ejemplo.springjpa.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Transactional()
    @Override
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional()
    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> findByNombre(String term) {
        return productoRepository.findByNombre(term);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteRepository.fetchByIdWithFacturas(id);
    }
}

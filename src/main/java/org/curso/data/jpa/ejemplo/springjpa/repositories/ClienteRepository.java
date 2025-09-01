package org.curso.data.jpa.ejemplo.springjpa.repositories;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Query("SELECT c FROM Cliente c left join fetch c.facturas f WHERE c.id =?1")
    public Cliente fetchByIdWithFacturas(Long id);
}

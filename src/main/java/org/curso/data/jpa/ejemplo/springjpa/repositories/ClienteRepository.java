package org.curso.data.jpa.ejemplo.springjpa.repositories;

import org.curso.data.jpa.ejemplo.springjpa.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}

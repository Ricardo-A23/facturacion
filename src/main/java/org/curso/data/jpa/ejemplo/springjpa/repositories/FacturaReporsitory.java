package org.curso.data.jpa.ejemplo.springjpa.repositories;

import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaReporsitory extends JpaRepository<Factura, Long> {
}

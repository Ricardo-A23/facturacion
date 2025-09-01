package org.curso.data.jpa.ejemplo.springjpa.repositories;

import org.curso.data.jpa.ejemplo.springjpa.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaReporsitory extends JpaRepository<Factura, Long> {

    //haciendo un join fetch para mejorar las consultas y no hacerlas con lazy
    @Query("SELECT f FROM Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto WHERE f.id =?1")
     public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}

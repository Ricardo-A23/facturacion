package org.curso.data.jpa.ejemplo.springjpa.repositories;

import org.curso.data.jpa.ejemplo.springjpa.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.nombre like %?1% ")
    public List<Producto> findByNombre(String term);
}

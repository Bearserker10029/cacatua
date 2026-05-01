package org.example.cacatua.repository;

import org.example.cacatua.model.TipoMadera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tipoMaderaRepository extends JpaRepository<TipoMadera, Integer> {
    Optional<TipoMadera> findByNivelResistencia(int nivelResistencia);
}
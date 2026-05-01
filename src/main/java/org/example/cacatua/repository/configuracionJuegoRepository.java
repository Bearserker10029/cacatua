package org.example.cacatua.repository;

import org.example.cacatua.model.ConfiguracionJuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface configuracionJuegoRepository extends JpaRepository<ConfiguracionJuego, Integer> {
}
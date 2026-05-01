package org.example.cacatua.repository;

import org.example.cacatua.model.TableroCelda;
import org.example.cacatua.model.TableroCeldaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tableroCeldaRepository extends JpaRepository<TableroCelda, TableroCeldaId> {

    @Query("SELECT t FROM TableroCelda t WHERE t.id.posX = :posX AND t.id.posY = :posY")
    Optional<TableroCelda> findByPosXAndPosY(@Param("posX") int posX, @Param("posY") int posY);

    @Query("SELECT t FROM TableroCelda t WHERE t.id.posY = :y ORDER BY t.id.posX ASC")
    List<TableroCelda> findByPosYOrderByPosXAsc(@Param("y") int y);

    @Modifying
    @Query("UPDATE TableroCelda t SET t.nivelResistencia = :nivel WHERE t.id.posX = :posX AND t.id.posY = :posY")
    void updateNivelResistencia(@Param("posX") int posX, @Param("posY") int posY, @Param("nivel") int nivel);

    @Query("SELECT t FROM TableroCelda t ORDER BY t.id.posY ASC, t.id.posX ASC")
    List<TableroCelda> findAllOrderByPosYAndPosX();
}
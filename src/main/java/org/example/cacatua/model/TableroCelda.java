package org.example.cacatua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tablero_celdas", schema = "cacatua_sulfurea", indexes = {@Index(name = "nivel_resistencia",
        columnList = "nivel_resistencia")})
public class TableroCelda {
    @EmbeddedId
    private TableroCeldaId id;

    @Column(name = "nivel_resistencia", nullable = false)
    private Integer nivelResistencia;

    @ColumnDefault("0")
    @Column(name = "es_cielo")
    private boolean esCielo;

    public TableroCelda() {}

    public TableroCelda(TableroCeldaId id, Integer nivelResistencia, Boolean esCielo) {
        this.id = id;
        this.nivelResistencia = nivelResistencia;
        this.esCielo = esCielo;
    }

    public Integer getPosX() {
        return id != null ? id.getPosX() : null;
    }

    public Integer getPosY() {
        return id != null ? id.getPosY() : null;
    }
}
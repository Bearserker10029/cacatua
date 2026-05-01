package org.example.cacatua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tablero_inicial_backup", schema = "cacatua_sulfurea")
public class TableroInicialBackup {
    @EmbeddedId
    private TableroInicialBackupId id;

    @Column(name = "nivel_resistencia", nullable = false)
    private Integer nivelResistencia;

    public TableroInicialBackup() {}

    public TableroInicialBackup(TableroInicialBackupId id, Integer nivelResistencia) {
        this.id = id;
        this.nivelResistencia = nivelResistencia;
    }

    public Integer getPosX() {
        return id != null ? id.getPosX() : null;
    }

    public Integer getPosY() {
        return id != null ? id.getPosY() : null;
    }
}
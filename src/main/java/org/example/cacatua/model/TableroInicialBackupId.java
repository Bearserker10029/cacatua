package org.example.cacatua.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class TableroInicialBackupId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "pos_x", nullable = false)
    private Integer posX;

    @Column(name = "pos_y", nullable = false)
    private Integer posY;

    public TableroInicialBackupId() {}

    public TableroInicialBackupId(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
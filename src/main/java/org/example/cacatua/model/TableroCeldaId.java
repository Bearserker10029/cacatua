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
public class TableroCeldaId implements Serializable {
    private static final long serialVersionUID = -189666035212057074L;

    @Column(name = "pos_x", nullable = false)
    private Integer posX;

    @Column(name = "pos_y", nullable = false)
    private Integer posY;

    public TableroCeldaId() {}

    public TableroCeldaId(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
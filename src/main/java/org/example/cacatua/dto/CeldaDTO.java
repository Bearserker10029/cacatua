package org.example.cacatua.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CeldaDTO {
    private int posX;
    private int posY;
    private int nivelResistencia;
    private String colorHex;
    private boolean esCielo;
    private boolean esCacatua;
    private boolean esSemilla;

    public CeldaDTO() {}

    public CeldaDTO(int posX, int posY, int nivelResistencia, String colorHex,
                    boolean esCielo, boolean esCacatua, boolean esSemilla) {
        this.posX = posX;
        this.posY = posY;
        this.nivelResistencia = nivelResistencia;
        this.colorHex = colorHex;
        this.esCielo = esCielo;
        this.esCacatua = esCacatua;
        this.esSemilla = esSemilla;
    }
}
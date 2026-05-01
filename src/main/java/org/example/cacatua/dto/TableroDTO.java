package org.example.cacatua.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableroDTO {
    private List<CeldaDTO> celdas;
    private int cacatuaPosX;
    private int cacatuaPosY;
    private int piensoPosX;
    private int piensoPosY;
    private int picotazosRestantes;
    private int filas;
    private int columnas;
    private String estadoJuego;

    public TableroDTO() {}

    public TableroDTO(List<CeldaDTO> celdas, int cacatuaPosX, int cacatuaPosY,
                     int piensoPosX, int piensoPosY, int picotazosRestantes,
                     int filas, int columnas, String estadoJuego) {
        this.celdas = celdas;
        this.cacatuaPosX = cacatuaPosX;
        this.cacatuaPosY = cacatuaPosY;
        this.piensoPosX = piensoPosX;
        this.piensoPosY = piensoPosY;
        this.picotazosRestantes = picotazosRestantes;
        this.filas = filas;
        this.columnas = columnas;
        this.estadoJuego = estadoJuego;
    }
}
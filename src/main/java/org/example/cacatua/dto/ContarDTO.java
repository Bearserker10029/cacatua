package org.example.cacatua.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContarDTO {
    private int filas;
    private int columnas;
    private int picotazosMaximos;
    private int picotazosRestantes;
    private int picotazos;

    public ContarDTO() {}

    public ContarDTO(int filas, int columnas, int picotazosMaximos,
                     int picotazosRestantes, int picotazos) {
        this.filas = filas;
        this.columnas = columnas;
        this.picotazosMaximos = picotazosMaximos;
        this.picotazosRestantes = picotazosRestantes;
        this.picotazos = picotazos;
    }
}
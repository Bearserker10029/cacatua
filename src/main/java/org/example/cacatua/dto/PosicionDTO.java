package org.example.cacatua.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosicionDTO {
    private int piensoPosX;
    private int piensoPosY;
    private int cacatuaPosX;
    private int cacatuaPosY;

    public PosicionDTO() {}

    public PosicionDTO(int piensoPosX, int piensoPosY, int cacatuaPosX, int cacatuaPosY) {
        this.piensoPosX = piensoPosX;
        this.piensoPosY = piensoPosY;
        this.cacatuaPosX = cacatuaPosX;
        this.cacatuaPosY = cacatuaPosY;
    }
}
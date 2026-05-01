package org.example.cacatua.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_madera", schema = "cacatua_sulfurea")
public class TipoMadera {
    @Id
    @Column(name = "nivel_resistencia", nullable = false)
    private Integer nivelResistencia;

    @Column(name = "nombre_capa", length = 50)
    private String nombreCapa;

    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    public TipoMadera() {}

    public TipoMadera(Integer nivelResistencia, String nombreCapa, String colorHex, String descripcion) {
        this.nivelResistencia = nivelResistencia;
        this.nombreCapa = nombreCapa;
        this.colorHex = colorHex;
        this.descripcion = descripcion;
    }
}
package org.example.cacatua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "configuracion_juego", schema = "cacatua_sulfurea")
public class ConfiguracionJuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "filas", nullable = false)
    private Integer filas;

    @Column(name = "columnas", nullable = false)
    private Integer columnas;

    @Column(name = "picotazos_maximos", nullable = false)
    private Integer picotazosMaximos;

    @Column(name = "picotazos_restantes", nullable = false)
    private Integer picotazosRestantes;

    @Column(name = "cacatua_pos_x", nullable = false)
    private Integer cacatuaPosX;

    @Column(name = "cacatua_pos_y", nullable = false)
    private Integer cacatuaPosY;

    @Column(name = "pienso_pos_x", nullable = false)
    private Integer piensoPosX;

    @Column(name = "pienso_pos_y", nullable = false)
    private Integer piensoPosY;

    @ColumnDefault("'EN_CURSO'")
    @Column(name = "estado_juego", length = 20)
    private String estadoJuego;


}
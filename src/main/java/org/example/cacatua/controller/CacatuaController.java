package org.example.cacatua.controller;

import org.example.cacatua.dto.CeldaDTO;
import org.example.cacatua.dto.MovimientoDTO;
import org.example.cacatua.dto.TableroDTO;
import org.example.cacatua.model.ConfiguracionJuego;
import org.example.cacatua.model.TableroCelda;
import org.example.cacatua.model.TableroInicialBackup;
import org.example.cacatua.model.TipoMadera;
import org.example.cacatua.repository.configuracionJuegoRepository;
import org.example.cacatua.repository.tableroCeldaRepository;
import org.example.cacatua.repository.tableroInicialBackupRepository;
import org.example.cacatua.repository.tipoMaderaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cacatua_sulfurea")
public class CacatuaController {

    private final configuracionJuegoRepository configuracionJuegoRepository;
    private final tableroCeldaRepository tableroCeldaRepository;
    private final tableroInicialBackupRepository tableroInicialBackupRepository;
    private final tipoMaderaRepository tipoMaderaRepository;

    public CacatuaController(
            configuracionJuegoRepository configuracionJuegoRepository,
            tableroCeldaRepository tableroCeldaRepository,
            tableroInicialBackupRepository tableroInicialBackupRepository,
            tipoMaderaRepository tipoMaderaRepository) {
        this.configuracionJuegoRepository = configuracionJuegoRepository;
        this.tableroCeldaRepository = tableroCeldaRepository;
        this.tableroInicialBackupRepository = tableroInicialBackupRepository;
        this.tipoMaderaRepository = tipoMaderaRepository;
    }

    @GetMapping("/")
    public String getGame(Model model) {
        loadGameState(model);
        return "cacatua_sulfurea/index";
    }

    @PostMapping("/mover")
    @Transactional
    public String mover(@RequestParam("direccion") String direccionStr, Model model) {
        MovimientoDTO direccion = MovimientoDTO.valueOf(direccionStr);

        ConfiguracionJuego config = configuracionJuegoRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No se encontro configuracion del juego"));

        if (!"EN_CURSO".equals(config.getEstadoJuego())) {
            loadGameState(model);
            return "cacatua_sulfurea/index";
        }

        int nuevaX = config.getCacatuaPosX();
        int nuevaY = config.getCacatuaPosY();

        switch (direccion) {
            case ARRIBA -> nuevaY = config.getCacatuaPosY() - 1;
            case ABAJO -> nuevaY = config.getCacatuaPosY() + 1;
            case IZQUIERDA -> nuevaX = config.getCacatuaPosX() - 1;
            case DERECHA -> nuevaX = config.getCacatuaPosX() + 1;
        }

        if (nuevaX < 1 || nuevaX > config.getColumnas() || nuevaY < 1 || nuevaY > config.getFilas()) {
            loadGameState(model);
            return "cacatua_sulfurea/index";
        }

        TableroCelda celdaDestino = tableroCeldaRepository.findByPosXAndPosY(nuevaX, nuevaY)
                .orElse(null);

        if (celdaDestino == null) {
            loadGameState(model);
            return "cacatua_sulfurea/index";
        }

        config.setPicotazosRestantes(config.getPicotazosRestantes() - 1);

        if (celdaDestino.getNivelResistencia() == 0) {
            config.setCacatuaPosX(nuevaX);
            config.setCacatuaPosY(nuevaY);
        } else {
            int nuevoNivel = celdaDestino.getNivelResistencia() - 1;
            celdaDestino.setNivelResistencia(nuevoNivel);
            tableroCeldaRepository.save(celdaDestino);
        }

        if (config.getCacatuaPosX() == config.getPiensoPosX()
                && config.getCacatuaPosY() == config.getPiensoPosY()
                && config.getPicotazosRestantes() > 0) {
            config.setEstadoJuego("VICTORIA");
        } else if (config.getPicotazosRestantes() <= 0) {
            config.setEstadoJuego("DERROTA");
        }

        configuracionJuegoRepository.save(config);
        loadGameState(model);
        return "cacatua_sulfurea/index";
    }

    @PostMapping("/reiniciar")
    @Transactional
    public String reiniciar(Model model) {
        List<TableroInicialBackup> backupCeldas = tableroInicialBackupRepository.findAll();

        for (TableroInicialBackup backup : backupCeldas) {
            TableroCelda celda = tableroCeldaRepository.findByPosXAndPosY(backup.getPosX(), backup.getPosY())
                    .orElse(null);
            if (celda != null) {
                celda.setNivelResistencia(backup.getNivelResistencia());
                tableroCeldaRepository.save(celda);
            }
        }

        ConfiguracionJuego config = configuracionJuegoRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No se encontro configuracion del juego"));

        config.setPicotazosRestantes(config.getPicotazosMaximos());
        config.setCacatuaPosX(3);
        config.setCacatuaPosY(1);
        config.setEstadoJuego("EN_CURSO");

        configuracionJuegoRepository.save(config);
        loadGameState(model);
        return "cacatua_sulfurea/index";
    }

    private void loadGameState(Model model) {
        ConfiguracionJuego config = configuracionJuegoRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No se encontro configuracion del juego"));

        List<TableroCelda> celdas = tableroCeldaRepository.findAll();

        Map<String, CeldaDTO> celdasByPos = new HashMap<>();
        for (TableroCelda celda : celdas) {
            TipoMadera madera = tipoMaderaRepository.findByNivelResistencia(celda.getNivelResistencia())
                    .orElse(null);
            String colorHex = madera != null ? madera.getColorHex() : "#FFFFFF";

            boolean esCacatua = celda.getPosX() == config.getCacatuaPosX()
                    && celda.getPosY() == config.getCacatuaPosY();
            boolean esSemilla = celda.getPosX() == config.getPiensoPosX()
                    && celda.getPosY() == config.getPiensoPosY();

            CeldaDTO celdaDTO = new CeldaDTO(
                    celda.getPosX(),
                    celda.getPosY(),
                    celda.getNivelResistencia(),
                    colorHex,
                    celda.isEsCielo(),
                    esCacatua,
                    esSemilla
            );

            String key = celda.getPosX() + "-" + celda.getPosY();
            celdasByPos.put(key, celdaDTO);
        }

        TableroDTO tableroDTO = new TableroDTO(
                celdas.stream().map(c -> celdasByPos.get(c.getPosX() + "-" + c.getPosY()))
                        .collect(Collectors.toList()),
                config.getCacatuaPosX(),
                config.getCacatuaPosY(),
                config.getPiensoPosX(),
                config.getPiensoPosY(),
                config.getPicotazosRestantes(),
                config.getFilas(),
                config.getColumnas(),
                config.getEstadoJuego()
        );

        model.addAttribute("tableroDTO", tableroDTO);
        model.addAttribute("celdasByPos", celdasByPos);
    }
}
# 🦜 El Juego de la Cacatúa Sulfúrea y la Semilla de Girasol

> Aplicación web desarrollada con Spring Boot y Thymeleaf como parte del 4to laboratorio del curso Gestión de Servicios de TICs (PUCP). El simulador representa cómo la Cacatúa Sulfúrea perfora capas de madera de distinta dureza para alcanzar una semilla de girasol oculta en el tronco.

## 📋 Tabla de Contenidos

- [Descripción](#-descripción-del-proyecto)
- [Tecnologías](#-tecnologías-usadas)
- [Estructura](#-estructura-principal)
- [Flujo Funcional](#-flujo-funcional-implementado)
- [Lógica del Juego](#-lógica-del-juego)
- [Estado](#-estado-frente-a-la-consigna)
- [Ejecución](#-cómo-ejecutar)

---

## 📝 Descripción del Proyecto

Un simulador educativo donde el usuario controla a la Cacatúa Sulfúrea en un tronco representado por una matriz. La aplicación permite:

✅ Desplazar al ave mediante un panel de dirección (Arriba, Abajo, Izquierda, Derecha)  
✅ Picotear bloques de madera de distinta dureza (Nivel 1, 2 y 3)  
✅ Visualizar los niveles de desgaste de cada capa de madera  
✅ Consumir picotazos con cada acción (mover o picar)  
✅ Persistir el progreso exacto de la partida en base de datos  
✅ Determinar la victoria (encuentra la semilla) o la derrota (agota sus movimientos)  
✅ Restaurar el tablero a su configuración original con el botón Reiniciar

## 💻 Tecnologías Usadas

| Tecnología | Uso |
|-----------|-----|
| Java | Lenguaje base |
| Spring Boot | Framework MVC |
| Spring MVC | Controladores HTTP |
| Spring Data JPA | Persistencia y mapeo de entidades |
| Thymeleaf | Motor de plantillas y fragmentos |
| MySQL | Base de datos (script `cacatua_sulfurea.sql`) |
| Maven | Gestor de dependencias |
| Lombok | Anotaciones de código |
| Bootstrap | Estilos de la interfaz |

## 📂 Estructura Principal

```
cacatua/
├── src/
│   ├── main/
│   │   ├── java/org/example/cacatua/
│   │   │   ├── CacatuaApplication.java
│   │   │   ├── controller/
│   │   │   │   └── CacatuaController.java
│   │   │   ├── dto/
│   │   │   │   ├── CeldaDTO.java
│   │   │   │   ├── ContarDTO.java
│   │   │   │   ├── MovimientoDTO.java
│   │   │   │   ├── PosicionDTO.java
│   │   │   │   └── TableroDTO.java
│   │   │   ├── model/
│   │   │   │   ├── ConfiguracionJuego.java
│   │   │   │   ├── TableroCelda.java
│   │   │   │   ├── TableroCeldaId.java
│   │   │   │   ├── TableroInicialBackup.java
│   │   │   │   ├── TableroInicialBackupId.java
│   │   │   │   └── TipoMadera.java
│   │   │   └── repository/
│   │   │       ├── configuracionJuegoRepository.java
│   │   │       ├── tableroCeldaRepository.java
│   │   │       ├── tableroInicialBackupRepository.java
│   │   │       └── tipoMaderaRepository.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       └── templates/
│   │           └── cacatua_sulfurea/
│   │               ├── index.html
│   │               ├── _tablero.html
│   │               ├── _celda.html
│   │               └── _panel.html
│   └── test/
├── cacatua_sulfurea.sql
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔄 Flujo Funcional Implementado

```
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO DEL JUEGO                          │
├─────────────────────────────────────────────────────────────┤
│  1. GET /cacatua_sulfurea                                   │
│     ↓ Carga estado actual desde la BD y muestra el tablero  │
│                                                             │
│  2. POST /cacatua_sulfurea/mover?direccion=...              │
│     ↓ Valida límites, desplaza o picotea, descuenta energía │
│                                                             │
│  3. Renderiza index con fragmentos:                         │
│     ↓ _tablero :: tablero (matriz completa)                 │
│     ↓ _celda :: celda (contenido de cada casilla)           │
│     ↓ _panel :: panel (energía, estado, botones)            │
│                                                             │
│  4. POST /cacatua_sulfurea/reiniciar                        │
│     ↓ Restaura celdas desde tablero_inicial_backup y        │
│       recupera energía máxima y posición inicial            │
└─────────────────────────────────────────────────────────────┘
```

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/cacatua_sulfurea/` | GET | Muestra el estado actual del juego |
| `/cacatua_sulfurea/mover` | POST | Procesa la acción según la dirección indicada |
| `/cacatua_sulfurea/reiniciar` | POST | Restaura el tablero a su configuración inicial |

## 🧠 Lógica del Juego

### Niveles de Resistencia

| Nivel | Tipo | Picotazos necesarios | Color (Hex) |
|-------|------|---------------------|-------------|
| 0 | Hueco/Cielo | 0 (desplazamiento libre) | `#FFFFFF` |
| 1 | Madera Clara | 1 | `#a67b5b` |
| 2 | Madera Media | 2 | `#6f4e37` |
| 3 | Madera Dura | 3 | `#4b3621` |

### Regla de Movimiento

```math
accion(destino):
  si destino fuera de límites          → ignorar (sin consumo de energía)
  si destino es cielo (nivel 0)        → mover cacatúa
  si destino tiene madera (nivel 1-3)  → picotear (nivel - 1), no se mueve
  cada acción válida                    → picotazosRestantes - 1
```

**Condiciones de finalización:**
- 🏆 **Victoria:** la cacatúa llega a la casilla de la semilla (posición inicial: 5,6) con al menos 1 picotazo disponible.
- 💀 **Derrota:** se agotan los 45 picotazos antes de alcanzar la semilla.

### Persistencia Progresiva

Todo el estado vive en la base de datos:
- `tablero_celdas`: nivel de desgaste actual de cada casilla.
- `configuracion_juego`: posición de la cacatúa, energía restante y estado de la partida.
- `tablero_inicial_backup`: respaldo del estado inicial para el botón Reiniciar.
- `tipo_madera`: catálogo de niveles con colores y descripciones.

## ✅ Estado frente a la Consigna

### ✔️ Implementado

- [x] Conexión y mapeo de entidades JPA (esquema `cacatua_sulfurea`)
- [x] Persistencia del avance del juego en base de datos
- [x] DTOs definidos para resolver el juego (TableroDTO, CeldaDTO, MovimientoDTO, PosicionDTO, ContarDTO)
- [x] Fragmentos de Celda, Tablero y Panel con Thymeleaf
- [x] Validación de que la cacatúa no salga de los límites del tronco
- [x] Movimiento según botones: se mueve si la casilla está vacía, picotea si tiene madera
- [x] Actualización visual de los niveles tras cada picotazo
- [x] Detección de victoria y derrota
- [x] Botón Reiniciar que restaura la configuración inicial desde la tabla de respaldo
- [x] Interfaz con diseño similar al propuesto en la consigna

### 💡 Observación de Mejora

⚠️ La posición inicial de la cacatúa está hardcodeada en el reinicio (`setCacatuaPosX(3)` / `setCacatuaPosY(1)`), aunque la consigna pide que toda la información provenga de la base de datos.

**Recomendación:** Para cumplir estrictamente con la consigna, se sugiere:
- Almacenar la posición inicial de la cacatúa en la tabla `configuracion_juego` (o una tabla de configuración inicial) y recuperarla desde ahí al reiniciar, en lugar de usar valores fijos en el código.

## 🚀 Cómo Ejecutar

### Requisitos Previos

- Java 17+ (o compatible con tu versión de Spring Boot)
- Maven 3.6+ (incluido como wrapper)
- MySQL 8+

### Configurar la Base de Datos

Ejecuta el script SQL en tu servidor MySQL:

```sql
source cacatua_sulfurea.sql;
```

Esto crea la base de datos `cacatua_sulfurea` con el tablero inicial (6 filas × 5 columnas, 45 picotazos), los tipos de madera y la tabla de respaldo.

### Ejecutar la Aplicación

**En Windows (PowerShell):**

```powershell
.\mvnw.cmd spring-boot:run
```

**En Linux/macOS:**

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

### Acceder a la Aplicación

Una vez iniciada, abre tu navegador en:

```
http://localhost:8080/cacatua_sulfurea
```

**Puerto por defecto:** 8080

---

## 📚 Recursos Adicionales

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Guide](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

---

## 📄 Licencia

Este proyecto es de uso académico y educativo como parte de un laboratorio de curso universitario.

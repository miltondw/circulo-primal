## Círculo Primal – Batallas Elementales

Juego de batallas por turnos con interfaz gráfica JavaFX inspirado en duelos estilo Pokémon. Elige un elemento y enfréntate a oponentes controlados por la computadora en combates visuales con animaciones y efectos.

### 🎮 Características

- **Interfaz Gráfica** con JavaFX: menús visuales, animaciones de batalla y efectos
- **Sistema de Combate por Turnos**: ataques con efectividad elemental y variación de daño
- **Animaciones**: movimientos de ataque, sacudidas al recibir daño, efectos de victoria/derrota
- **Sistema de Rachas**: bonificaciones permanentes por victorias consecutivas
- **Log de Combate**: historial detallado de cada acción durante la batalla

### Elementos
Cuatro tipos básicos:

- fuego 🔥
- agua 💧
- aire 🌪️
- tierra 🌍

Relaciones (ventaja):

```
fuego > tierra > aire > agua > fuego (ciclo)
```

Si tu elemento tiene ventaja haces más daño (x1.5). Si el enemigo tiene ventaja haces menos daño (x0.75).

### Estadísticas Base
Cada elemento empieza con:

- HP: 50
- Ataque: 12

El ataque causa un daño base +/- pequeña variación (0–5) modificado por el tipo de ataque y la efectividad elemental.

### Tipos de Ataque
- r (rápido): 100% precisión, daño normal.
- e (elemental): 80% precisión, +20% daño. Puede fallar (daño 0).

### Rachas y Bonos
Se lleva una racha de victorias por cada elemento que uses.

- Al llegar a 3 victorias seguidas: +3 ataque permanente para ese elemento durante la sesión.
- Cada 5 victorias adicionales (5,10,15,...) después de la tercera: +2 ataque acumulativo.
- Perder reinicia la racha de ese elemento (los bonos ya ganados se mantienen).

### Cómo Jugar

#### Requisitos
- Java 11 o superior con JavaFX
- En Ubuntu/Debian: `sudo apt install openjdk-21-jdk openjfx`

#### Compilar y Ejecutar (Versión Gráfica)

```bash
./run-javafx.sh
```

O manualmente:

```bash
javac --module-path /usr/share/openjfx/lib \
      --add-modules javafx.controls,javafx.fxml \
      -d bin $(find src -name '*.java')

java --module-path /usr/share/openjfx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp bin MainApp
```

#### Compilar y Ejecutar (Versión Consola - legacy)

```bash
javac -d bin $(find src -name '*.java')
java -cp bin App
```

#### Controles del Juego

**Versión Gráfica (JavaFX):**
1. **Menú Principal**: Haz clic en "INICIAR BATALLA"
2. **Selección de Elemento**: Haz clic en uno de los 4 elementos disponibles
3. **Durante la Batalla**: 
   - Haz clic en "⚡ ATAQUE RÁPIDO" (100% precisión, daño normal)
   - Haz clic en "💥 ATAQUE ELEMENTAL" (80% precisión, +20% daño)
4. Observa las barras de vida y el log de combate
5. Al finalizar, regresa al menú principal para otra batalla

**Versión Consola (legacy):**
1. Elige un elemento válido (fuego, agua, aire, tierra)
2. En cada turno selecciona tipo de ataque: `r` o `e`
3. Observa las barras de vida y adapta tu estrategia
4. Decide si continuar tras cada batalla

### Ejemplo de Sesión (Consola)
```
🔥💧🌪️🌍 Bienvenido al juego de los elementos (modo combate) 🌍🌪️💧🔥
Elige un elemento (fuego, agua, aire, tierra): fuego
Tu personaje: Elemento{nombre='fuego', hp=50/50, atk=12}
Enemigo: Elemento{nombre='tierra', hp=50/50, atk=12}
Elige ataque (r = rápido, e = elemental): r
Tu rapido hace 21 de daño.
...
🏆 ¡Ganaste la batalla!
¿Jugar otra batalla? (s/n): s
```

### Estructura del Proyecto

```
src/
  MainApp.java                    # Punto de entrada JavaFX
  App.java                        # Punto de entrada consola (legacy)
  domain/
    Elemento.java                 # Modelo del personaje + lógica de daño
  services/
    JuegoService.java             # Lógica del loop de juego (consola)
  ui/
    GameController.java           # Controlador principal de vistas
    MainMenuView.java             # Vista del menú principal
    ElementSelectionView.java     # Vista de selección de elementos
    BattleView.java               # Vista de batalla con animaciones
  resources/
    styles/
      game.css                    # Estilos visuales del juego
```

### 🎨 Características Visuales

- **Gradientes de fondo** oscuros para ambiente de batalla
- **Colores por elemento**: cada elemento tiene su paleta única
- **Animaciones fluidas**: 
  - Movimiento de ataque hacia adelante
  - Sacudida del objetivo al recibir daño
  - Efectos de escala en victoria
  - Fade out en derrota
- **Barras de HP animadas** con colores según jugador/enemigo
- **Log de combate** con scroll automático
- **Efectos hover** en todos los botones

### Próximas Mejoras Ideas (no implementadas aún)
- Diferentes stats por elemento (ej: agua más HP, aire más precisión)
- Ataques especiales con cooldown y efectos visuales únicos
- Guardar progreso de rachas en archivo
- Música y efectos de sonido
- Modo multijugador local
- Partículas y efectos visuales avanzados

---

### 📸 Screenshots

La interfaz incluye:
- **Menú Principal**: Botones grandes con gradientes y hover effects
- **Selección de Elemento**: 4 botones visuales con emojis gigantes
- **Pantalla de Batalla**: Sprites de personajes, barras HP animadas, botones de ataque
- **Log de Combate**: Panel lateral con historial detallado

---

¡Disfruta y experimenta! Aporta ideas con un PR si quieres ampliar el juego.

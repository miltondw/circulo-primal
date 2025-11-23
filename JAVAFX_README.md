# 🔥 Círculo Primal - Interfaz Gráfica con JavaFX

## ✨ Mejoras Implementadas

Se ha transformado completamente el juego a una **interfaz gráfica moderna con JavaFX** que incluye:

### 🎨 Características Visuales

1. **Menú Principal Animado**
   - Gradientes de fondo oscuros con efectos visuales
   - Botones grandes con hover effects y escalado
   - Diseño moderno y atractivo

2. **Pantalla de Selección de Elementos**
   - 4 botones visuales con emojis gigantes (🔥💧🌪️🌍)
   - Colores únicos por elemento:
     - Fuego: `#ff4500` (naranja-rojo)
     - Agua: `#1e90ff` (azul)
     - Aire: `#87ceeb` (celeste claro)
     - Tierra: `#8b4513` (marrón)
   - Efectos de borde blanco al pasar el mouse
   - Animación de escala (1.1x) en hover

3. **Pantalla de Batalla con Animaciones**
   - **Sprites de personajes**: círculos con aura y efectos visuales
   - **Barras de HP animadas**: 
     - Verde/Cyan para jugador
     - Rojo para enemigo
     - Actualizaciones suaves en tiempo real
   - **Botones de ataque**:
     - ⚡ Ataque Rápido (100% precisión)
     - 💥 Ataque Elemental (80% precisión, +20% daño)
   - **Log de combate lateral**: historial detallado con scroll automático
   - **Animaciones de combate**:
     - Movimiento hacia adelante del atacante
     - Sacudida del objetivo al recibir daño
     - Efectos de escala en victoria (1.3x)
     - Fade out en derrota (opacidad 0.3)

### ⚔️ Sistema de Combate

- **Efectividad Elemental**: Fuego > Tierra > Aire > Agua > Fuego (ciclo)
  - Ventaja: x1.5 daño
  - Desventaja: x0.75 daño
- **Variación de Daño**: 0-5 puntos aleatorios por ataque
- **Sistema de Rachas**:
  - 3 victorias consecutivas: +3 ataque permanente
  - Cada 5 victorias adicionales: +2 ataque acumulativo
  - Se reinicia al perder (bonos ya ganados se mantienen)

### 📁 Nueva Estructura de Archivos

```
src/
  MainApp.java                      # Punto de entrada JavaFX ⭐ NUEVO
  App.java                          # Punto de entrada consola (legacy)
  domain/
    ElementoBatalla.java            # Clase de batalla con HP/ataque ⭐ NUEVO
    Elemento.java                   # Clase simple para sistema de rondas
    EntidadJuego.java              
    Jugador.java                    
    ModoJuego.java                  
  services/
    JuegoService.java               # Lógica consola (legacy)
  ui/                               ⭐ NUEVO DIRECTORIO
    GameController.java             # Controlador principal de vistas
    MainMenuView.java               # Vista del menú principal
    ElementSelectionView.java       # Vista de selección
    BattleView.java                 # Vista de batalla con animaciones
  resources/                        ⭐ NUEVO DIRECTORIO
    styles/
      game.css                      # Estilos visuales
```

## 🚀 Cómo Ejecutar

### Opción 1: Script Automático (Recomendado)

```bash
./run-javafx-simple.sh
```

Este script:
- Detecta JavaFX automáticamente (local o sistema)
- Compila todos los archivos
- Copia recursos
- Ejecuta la aplicación

### Opción 2: Manual

```bash
# 1. Compilar
javac -cp "./javafx-sdk-21/javafx-sdk-21.0.1/lib/*" \
      -d bin \
      $(find src -name "*.java")

# 2. Copiar recursos
cp -r src/resources bin/

# 3. Ejecutar
java -Djava.library.path="./javafx-sdk-21/javafx-sdk-21.0.1" \
     -cp "bin:./javafx-sdk-21/javafx-sdk-21.0.1/lib/*" \
     MainApp
```

### Opción 3: Versión Consola (Legacy)

```bash
javac -d bin $(find src -name "*.java")
java -cp bin App
```

## 📋 Requisitos

- **Java 11 o superior**
- **JavaFX SDK** (incluido en el proyecto o instalar con `sudo apt install openjfx`)
- **Entorno gráfico** (X11, Wayland, etc.)

## 🎮 Controles del Juego

1. **Menú Principal**: Click en "⚔️ INICIAR BATALLA"
2. **Selección**: Click en uno de los 4 elementos
3. **Batalla**:
   - Click en "⚡ ATAQUE RÁPIDO" para ataque seguro
   - Click en "💥 ATAQUE ELEMENTAL" para ataque poderoso (puede fallar)
   - Observa las barras de vida y el log de combate
4. **Finalizar**: Click en "🏠 Menú Principal" para volver

## 🎯 Próximas Mejoras Sugeridas

- [ ] Efectos de sonido para ataques y victorias
- [ ] Música de fondo
- [ ] Partículas visuales en ataques elementales
- [ ] Modo multijugador local
- [ ] Persistencia de rachas en archivo
- [ ] Diferentes stats por elemento (agua +HP, aire +precisión, etc.)
- [ ] Ataques especiales con cooldown
- [ ] Sistema de niveles y experiencia
- [ ] Torneo/campaña con múltiples enemigos

## 🐛 Solución de Problemas

### Error: "JavaFX runtime components are missing"
```bash
# Asegúrate de que JavaFX esté en el classpath
# El script automático debería manejarlo, si no:
export JAVAFX_PATH="./javafx-sdk-21/javafx-sdk-21.0.1/lib"
```

### Error: "cannot find symbol" al compilar
```bash
# Limpia y recompila
rm -rf bin/*
./run-javafx-simple.sh
```

### No se ven gráficos
```bash
# Verifica que estés en un entorno gráfico
echo $DISPLAY

# Si está vacío, no tienes servidor X
# Prueba la versión de consola:
java -cp bin App
```

## 📸 Capturas de Pantalla

### Menú Principal
- Fondo degradado oscuro (azul-morado)
- Título con emojis gigantes
- Botones con efectos hover

### Selección de Elemento
- 4 botones grandes con colores únicos
- Información de ventajas elementales
- Botón de regreso

### Pantalla de Batalla
- Sprites circulares con colores por elemento
- Barras de HP arriba (enemigo) y abajo (jugador)
- 2 botones de ataque centrales
- Log de combate a la derecha
- Animaciones fluidas

---

**Nota**: Este proyecto mantiene compatibilidad con la versión de consola anterior (`App.java`). Puedes usar ambas versiones según tus necesidades.

¡Disfruta del juego! 🎮🔥💧🌪️🌍

## Círculo Primal – Batallas Elementales

Juego de consola simple en Java inspirado en duelos por turnos al estilo Pokémon. Elige un elemento y enfréntate a oponentes controlados por la computadora. Gana varias batallas seguidas para potenciar a tu personaje.

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
Compila y ejecuta:

```bash
javac -d bin $(find src -name '*.java')
java -cp bin App
```

Luego:
1. Elige un elemento válido.
2. En cada turno selecciona tipo de ataque: `r` o `e`.
3. Observa las barras de vida y adapta tu estrategia.
4. Decide si continuar tras cada batalla.

### Ejemplo de Sesión
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
	App.java                # Punto de entrada
	domain/Elemento.java    # Modelo del personaje + lógica de daño
	services/JuegoService.java # Lógica del loop de juego y rachas
```

### Próximas Mejores Ideas (no implementadas aún)
- Diferentes stats por elemento (ej: agua más HP, aire más precisión).
- Ataques especiales con cooldown.
- Guardar progreso de rachas en archivo.

¡Disfruta y experimenta! Aporta ideas con un PR si quieres ampliar el juego.

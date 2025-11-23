**Circulo Primal**

**Descripción**
- **Proyecto:**: Implementación de un juego (Java) llamado "Circulo Primal".
- **Propósito:**: Repositorio con lógica de dominio, servicios, manejo de excepciones y una UI simple.


**Estructura del proyecto**
- **`src/`**: Código fuente Java.
  - **`App.java`**: Punto de entrada.
  - **`domain/`**: Modelos de dominio (`Elemento.java`, `Jugador.java`, `ModoJuego.java`, etc.).
  - **`services/`**: Lógica de servicios (`InputService.java`, `JuegoService.java`, `RondaService.java`, `ResultadoService.java`).
  - **`exceptions/`**: Excepciones personalizadas (`ElementoInvalidoException.java`, `NombreInvalidoException.java`, `OpcionInvalidaException.java`).
  - **`ui/`**: Servicios de interfaz (`UIService.java`).

**Responsabilidades**
- **UI:** Edwin
- **Domain:** Emily
- **Services:** Milton
- **Exceptions:** Juan


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
Luego:
1. Elige un elemento válido.
2. En cada turno selecciona tipo de ataque: `r` o `e`.
3. Observa las barras de vida y adapta tu estrategia.
4. Decide si continuar tras cada batalla.
`

¡Disfruta y experimenta! Aporta ideas con un PR si quieres ampliar el juego.

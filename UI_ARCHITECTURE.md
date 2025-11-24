# 🏗️ Arquitectura de la Interfaz Gráfica

## 📂 Estructura del Paquete `ui/`

```
ui/
├── GameController.java          # Controlador principal de navegación
│
├── components/                   # Componentes reutilizables
│   ├── ElementButton.java       # Botón de elemento estilizado
│   ├── CombatLogPanel.java      # Panel de log de combate
│   └── StyledButton.java        # Botón genérico estilizado
│
├── views/                        # Vistas de las pantallas
│   ├── MainMenuView.java        # Menú principal
│   ├── ElementSelectionView.java# Selección de elemento (batalla infinita)
│   ├── BattleView.java          # Vista de batalla infinita
│   ├── TournamentSetupView.java # Configuración del torneo
│   └── TournamentGameView.java  # Vista del juego de torneo
│
├── controllers/                  # Lógica de negocio de UI
│   └── TournamentController.java# Controlador del modo torneo
│
└── utils/                        # Utilidades
    └── ElementUtils.java        # Utilidades para elementos
```

## 🎯 Principios de Diseño

### **Separación de Responsabilidades**

1. **Views** (`views/`): Solo manejan la presentación visual
   - Crean y organizan componentes UI
   - Delegan lógica a los controladores
   - No contienen lógica de negocio

2. **Controllers** (`controllers/`): Manejan la lógica de negocio
   - Procesan interacciones del usuario
   - Coordinan entre el modelo (domain) y la vista
   - Mantienen el estado de la aplicación

3. **Components** (`components/`): Componentes reutilizables
   - Encapsulan estilos y comportamientos comunes
   - Pueden ser usados en múltiples vistas
   - Reducen duplicación de código

4. **Utils** (`utils/`): Funciones auxiliares
   - Lógica compartida que no pertenece a ninguna clase específica
   - Funciones puras sin estado

### **GameController** 
Actúa como el router/navegador de la aplicación:
- Gestiona las transiciones entre pantallas
- Crea las escenas de JavaFX
- Mantiene referencias a las vistas actuales

## 📋 Flujo de Navegación

```
MainMenuView
    ├─→ BATALLA INFINITA → ElementSelectionView → BattleView
    ├─→ TORNEO (vs CPU) → TournamentSetupView → TournamentGameView
    ├─→ 2 JUGADORES → TournamentSetupView → TournamentGameView
    └─→ SALIR
```

## 🔄 Ejemplo de Flujo: Modo Torneo

1. **Usuario selecciona modo torneo**
   - `MainMenuView` llama a `GameController.showTournamentSetup()`

2. **Usuario configura el torneo**
   - `TournamentSetupView` valida inputs
   - Llama a `GameController.showTournamentGame(player1, player2, rounds)`

3. **Juego de torneo**
   - `TournamentGameView` crea un `TournamentController`
   - Usuario selecciona elementos
   - `TournamentController` procesa la lógica
   - `TournamentGameView` actualiza la UI con los resultados

4. **Fin del torneo**
   - `TournamentController.getFinalResult()` calcula ganador
   - `TournamentGameView` muestra resultados
   - Usuario vuelve al menú principal

## 🎨 Componentes Reutilizables

### **ElementButton**
```java
new ElementButton("🔥", "FUEGO", "#ff4500", 
    () -> handleElementSelection("fuego"))
```

### **CombatLogPanel**
```java
CombatLogPanel logPanel = new CombatLogPanel();
logPanel.addLog("🎮 ¡Torneo iniciado!");
```

### **StyledButton**
```java
StyledButton button = new StyledButton("Texto", "#4ecdc4", 250, 50);
button.setOnAction(e -> handleAction());
```

## 🧪 Testing

Con esta arquitectura es más fácil probar:

- **Controllers**: Pueden ser testeados sin UI
- **Utils**: Funciones puras fáciles de testear
- **Views**: Se pueden mockear los controllers

## 🔧 Extensibilidad

Para agregar una nueva pantalla:

1. Crear vista en `views/`
2. Si necesita lógica compleja, crear controller en `controllers/`
3. Agregar método en `GameController` para mostrarla
4. Conectar desde la vista que la invoca

## 💡 Buenas Prácticas Aplicadas

✅ **Bajo acoplamiento**: Las vistas no conocen la lógica de negocio  
✅ **Alta cohesión**: Cada clase tiene una responsabilidad clara  
✅ **DRY**: Componentes reutilizables evitan duplicación  
✅ **Composición**: Las vistas usan componentes en lugar de herencia  
✅ **Single Responsibility**: Cada clase tiene una sola razón para cambiar

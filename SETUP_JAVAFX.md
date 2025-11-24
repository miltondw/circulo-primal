# 📦 Guía de Instalación y Configuración de JavaFX

## ✅ Configuración Actual (RECOMENDADA)

JavaFX está instalado en la carpeta **`lib/`** del proyecto siguiendo el estándar profesional de Java.

### Estructura:
```
proyecto/
├── lib/
│   ├── javafx.base.jar          ✅ Incluido en git
│   ├── javafx.controls.jar      ✅ Incluido en git
│   ├── javafx.fxml.jar          ✅ Incluido en git
│   ├── javafx.graphics.jar      ✅ Incluido en git
│   ├── javafx.media.jar         ✅ Incluido en git
│   ├── javafx.swing.jar         ✅ Incluido en git
│   ├── javafx-swt.jar           ✅ Incluido en git
│   ├── javafx.web.jar           ✅ Incluido en git
│   └── *.so                     ⚠️  Ignorado en git (específico del SO)
├── src/
├── bin/
└── run-javafx.sh
```

### **Ventajas de este método:**
- ✅ **Portable**: Funciona en cualquier máquina sin configuración extra
- ✅ **Autocontenido**: No depende de instalaciones del sistema
- ✅ **Versionado**: Controlas la versión exacta de JavaFX
- ✅ **Estándar**: Método común en proyectos Java profesionales
- ✅ **Funciona con Maven/Gradle**: Compatible con herramientas de build

## 🚀 Cómo Ejecutar

### Opción 1: Script automático (Linux/Mac)
```bash
./run-javafx.sh
```

### Opción 2: Comandos manuales
```bash
# Compilar
javac -cp "lib/*" -d bin $(find src -name "*.java")

# Copiar recursos
cp -r src/resources bin/

# Ejecutar
java --module-path lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp "bin:lib/*" \
     MainApp
```

## 📝 Configuración de VS Code

El archivo `.vscode/settings.json` ya está configurado:

```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

Esto permite que VS Code reconozca automáticamente las bibliotecas JavaFX.

## 🔄 Otras Formas de Instalar JavaFX (alternativas)

### Opción 2: Instalación del Sistema
```bash
# Ubuntu/Debian
sudo apt install openjfx

# Arch Linux
sudo pacman -S java-openjfx

# Fedora
sudo dnf install java-openjfx
```

**Ventajas**: No ocupa espacio en el proyecto  
**Desventajas**: Puede haber conflictos de versiones entre proyectos

### Opción 3: Maven/Gradle (para proyectos grandes)

**Maven (pom.xml):**
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21.0.1</version>
    </dependency>
</dependencies>
```

**Gradle (build.gradle):**
```gradle
dependencies {
    implementation 'org.openjfx:javafx-controls:21.0.1'
    implementation 'org.openjfx:javafx-fxml:21.0.1'
}
```

## 🐛 Solución de Problemas

### Error: "JavaFX runtime components are missing"
**Causa**: Falta el `--module-path` al ejecutar  
**Solución**: Usa el script `run-javafx.sh` o agrega `--module-path lib`

### Error: "The import javafx cannot be resolved"
**Causa**: VS Code no reconoce las bibliotecas  
**Solución**: 
1. Verifica que `lib/*.jar` existan
2. Recarga VS Code: `Ctrl+Shift+P` > "Java: Clean Java Language Server Workspace"

### Las bibliotecas .so no están en git
**Esto es correcto**: Las bibliotecas nativas (`.so`, `.dll`, `.dylib`) son específicas del sistema operativo y se descargan automáticamente cuando ejecutas el script.

## 📚 Recursos

- [Documentación oficial de JavaFX](https://openjfx.io/)
- [Guía de Getting Started](https://openjfx.io/openjfx-docs/)
- [JavaFX API Docs](https://openjfx.io/javadoc/21/)

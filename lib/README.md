# 📚 Directorio de Bibliotecas JavaFX

Este directorio contiene las bibliotecas de JavaFX necesarias para ejecutar la aplicación.

## 📦 Contenido

### ✅ Incluido en Git (Archivos JAR)
- `javafx.base.jar`
- `javafx.controls.jar`
- `javafx.fxml.jar`
- `javafx.graphics.jar`
- `javafx.media.jar`
- `javafx.swing.jar`
- `javafx-swt.jar`
- `javafx.web.jar`

### ⚠️ NO incluido en Git (Bibliotecas Nativas)
Los siguientes archivos **NO están en el repositorio** porque son específicos del sistema operativo y muy grandes (+100MB):

- `*.so` (Linux)
- `*.dll` (Windows)
- `*.dylib` (macOS)
- `javafx.properties`

## 🚀 Instalación Automática

No te preocupes, **las bibliotecas nativas se descargan automáticamente** cuando ejecutas:

```bash
./run-javafx.sh
```

El script detecta si faltan las bibliotecas nativas y las descarga del sitio oficial de OpenJFX.

## 🔧 Instalación Manual

Si prefieres instalar manualmente:

```bash
# Descargar JavaFX SDK 21 para tu sistema operativo
wget https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_linux-x64_bin-sdk.zip

# Extraer
unzip openjfx-21.0.1_linux-x64_bin-sdk.zip

# Copiar archivos nativos a lib/
cp javafx-sdk-21.0.1/lib/*.so lib/
cp javafx-sdk-21.0.1/lib/javafx.properties lib/

# Limpiar
rm -rf javafx-sdk-21.0.1 openjfx-21.0.1_linux-x64_bin-sdk.zip
```

## ℹ️ Por qué no están en Git

Las bibliotecas nativas son:
- 📊 Muy grandes (>100MB en total)
- 🖥️ Específicas del sistema operativo
- 🔄 Fáciles de descargar automáticamente

Los archivos JAR son:
- 📦 Portables entre sistemas
- 🎯 Necesarios para compilación
- ✅ Incluidos en el repositorio (~8MB total)

#!/bin/bash

# Script de compilación y ejecución con JavaFX

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}🔥 Compilando Círculo Primal con JavaFX...${NC}"

# Limpiar compilación anterior
rm -rf bin/*
mkdir -p bin

# Detectar Java
JAVA_HOME=${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}

# Buscar JavaFX en el directorio lib del proyecto (estándar)
JAVAFX_PATH="./lib"

# Si no está en lib, buscar en ubicaciones del sistema
if [ ! -f "$JAVAFX_PATH/javafx.controls.jar" ]; then
    JAVAFX_PATH="/usr/share/openjfx/lib"
    
    if [ ! -d "$JAVAFX_PATH" ] || [ ! -f "$JAVAFX_PATH/javafx.controls.jar" ]; then
        JAVAFX_PATH="$JAVA_HOME/lib"
    fi
fi

# Si aún no se encuentra, descargar JavaFX SDK a lib/
if [ ! -f "$JAVAFX_PATH/javafx.controls.jar" ]; then
    echo -e "${YELLOW}⚠️  JavaFX no encontrado. Descargando a lib/...${NC}"
    
    mkdir -p ./lib
    
    # Descargar JavaFX SDK 21 (Linux x64)
    echo -e "${YELLOW}📥 Descargando JavaFX SDK 21...${NC}"
    wget -q --show-progress https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_linux-x64_bin-sdk.zip -O javafx.zip
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}📦 Extrayendo JavaFX SDK...${NC}"
        unzip -q javafx.zip
        cp javafx-sdk-21.0.1/lib/*.jar lib/
        cp javafx-sdk-21.0.1/lib/*.so lib/ 2>/dev/null || true
        rm -rf javafx-sdk-21.0.1 javafx.zip
        echo -e "${GREEN}✅ JavaFX instalado en lib/${NC}"
    else
        echo -e "${RED}❌ Error al descargar JavaFX${NC}"
        echo -e "${YELLOW}💡 Instala JavaFX manualmente:${NC}"
        echo -e "${YELLOW}   sudo apt install openjfx${NC}"
        exit 1
    fi
    
    JAVAFX_PATH="./lib"
fi

echo -e "${GREEN}📍 Usando JavaFX en: $JAVAFX_PATH${NC}"

# Compilar
echo -e "${GREEN}📦 Compilando código fuente...${NC}"

# Buscar JARs de JavaFX
JAVAFX_JARS=""
for jar in "$JAVAFX_PATH"/*.jar; do
    if [ -f "$jar" ]; then
        if [ -z "$JAVAFX_JARS" ]; then
            JAVAFX_JARS="$jar"
        else
            JAVAFX_JARS="$JAVAFX_JARS:$jar"
        fi
    fi
done

if [ -z "$JAVAFX_JARS" ]; then
    echo -e "${RED}❌ No se encontraron JARs de JavaFX en $JAVAFX_PATH${NC}"
    exit 1
fi

javac -cp "$JAVAFX_JARS" \
      -d bin \
      $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Compilación exitosa${NC}"
    
    # Copiar recursos
    echo -e "${GREEN}📋 Copiando recursos...${NC}"
    cp -r src/resources bin/ 2>/dev/null || true
    
    # Ejecutar
    echo -e "${GREEN}🚀 Ejecutando Círculo Primal...${NC}"
    echo ""
    
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         -cp "bin:$JAVAFX_JARS" \
         MainApp
else
    echo -e "${RED}❌ Error en la compilación${NC}"
    exit 1
fi

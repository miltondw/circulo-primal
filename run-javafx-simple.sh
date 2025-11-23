#!/bin/bash

# Script simplificado de compilación y ejecución con JavaFX

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}🔥 Compilando Círculo Primal con JavaFX...${NC}"

# Limpiar compilación anterior
rm -rf bin/*
mkdir -p bin

# Buscar JavaFX localmente primero
JAVAFX_PATH=""

# Buscar en directorio local
LOCAL_FX=$(find . -maxdepth 3 -type d -path "*/javafx-sdk-*/lib" 2>/dev/null | head -1)
if [ ! -z "$LOCAL_FX" ] && [ -d "$LOCAL_FX" ]; then
    JAVAFX_PATH="$LOCAL_FX"
    echo -e "${GREEN}📍 Usando JavaFX local en: $JAVAFX_PATH${NC}"
else
    # Buscar en sistema
    if [ -d "/usr/share/openjfx/lib" ]; then
        JAVAFX_PATH="/usr/share/openjfx/lib"
        echo -e "${GREEN}📍 Usando JavaFX del sistema${NC}"
    else
        echo -e "${RED}❌ JavaFX no encontrado${NC}"
        echo -e "${YELLOW}💡 Descarga JavaFX SDK desde: https://gluonhq.com/products/javafx/${NC}"
        echo -e "${YELLOW}   O instala: sudo apt install openjfx${NC}"
        exit 1
    fi
fi

# Construir classpath con todos los JARs de JavaFX
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

# Compilar
echo -e "${GREEN}📦 Compilando código fuente...${NC}"
javac --module-path "$JAVAFX_PATH" \
      --add-modules javafx.controls,javafx.graphics \
      -d bin \
      $(find src -name "*.java" 2>/dev/null)

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Compilación exitosa${NC}"
    
    # Copiar recursos
    if [ -d "src/resources" ]; then
        echo -e "${GREEN}📋 Copiando recursos...${NC}"
        cp -r src/resources bin/ 2>/dev/null || true
    fi
    
    # Ejecutar
    echo -e "${GREEN}🚀 Ejecutando Círculo Primal...${NC}"
    echo ""
    
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.graphics \
         -cp bin \
         MainApp
    
    EXIT_CODE=$?
    if [ $EXIT_CODE -ne 0 ]; then
        echo ""
        echo -e "${RED}❌ Error al ejecutar (código: $EXIT_CODE)${NC}"
        echo -e "${YELLOW}💡 Si ves errores de display, verifica que estés en un entorno gráfico${NC}"
        echo -e "${YELLOW}💡 O prueba la versión de consola: java -cp bin App${NC}"
    fi
else
    echo -e "${RED}❌ Error en la compilación${NC}"
    exit 1
fi

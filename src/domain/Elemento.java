package domain;

import exceptions.ElementoInvalidoException;
import exceptions.NombreInvalidoException;

public class Elemento extends EntidadJuego {

    public Elemento(String nombre) throws ElementoInvalidoException, NombreInvalidoException {
        super(validarYNormalizarElemento(nombre));
    }

    private static String validarYNormalizarElemento(String nombre) throws ElementoInvalidoException, NombreInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ElementoInvalidoException("El nombre del elemento no puede estar vacío");
        }

        String nombreLower = nombre.toLowerCase().trim();
        if (!nombreLower.equals("fuego") && !nombreLower.equals("agua") && 
            !nombreLower.equals("aire") && !nombreLower.equals("tierra")) {
            throw new ElementoInvalidoException("Elemento inválido: '" + nombre + "'. " +
                    "Los elementos válidos son: fuego, agua, aire, tierra");
        }
        
        return nombreLower;
    }

    @Override
    public String toString() {
        return "Elemento [nombre=" + nombre + "]";
    }
}


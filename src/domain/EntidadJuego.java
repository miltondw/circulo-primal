package domain;

import exceptions.NombreInvalidoException;

public abstract class EntidadJuego {
    protected String nombre;

    public EntidadJuego(String nombre) throws NombreInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "EntidadJuego [nombre=" + nombre + "]";
    }

    public boolean ganaA(EntidadJuego otro) {
        if (otro == null) {
            return false;
        }
        
        switch (nombre) {
            case "fuego":
                return otro.nombre.equals("tierra");
            case "agua":
                return otro.nombre.equals("fuego");
            case "aire":
                return otro.nombre.equals("agua");
            case "tierra":
                return otro.nombre.equals("aire");
            default:
                return false;
        }
    }
}


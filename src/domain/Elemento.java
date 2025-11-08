package domain;

public class Elemento {
    private final String nombre;

    public Elemento(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Elemento [nombre=" + nombre + "]";
    }

    public boolean ganaA(Elemento otro) {
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

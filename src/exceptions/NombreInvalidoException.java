package exceptions;

public class NombreInvalidoException extends Exception {
    public NombreInvalidoException(String mensaje) {
        super(mensaje);
    }

    public NombreInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

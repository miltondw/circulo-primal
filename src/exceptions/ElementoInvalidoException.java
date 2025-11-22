package exceptions;

public class ElementoInvalidoException extends Exception {
    public ElementoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public ElementoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

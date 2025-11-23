package exceptions;

public class OpcionInvalidaException extends Exception {
    public OpcionInvalidaException(String mensaje) {
        super(mensaje);
    }

    public OpcionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

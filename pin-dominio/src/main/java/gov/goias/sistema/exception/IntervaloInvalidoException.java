package gov.goias.sistema.exception;

/**
 * Created by kenta on 6/17/16.
 */
public class IntervaloInvalidoException extends RuntimeException {
    public IntervaloInvalidoException() {
    }

    public IntervaloInvalidoException(String message) {
        super(message);
    }

    public IntervaloInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntervaloInvalidoException(Throwable cause) {
        super(cause);
    }
}

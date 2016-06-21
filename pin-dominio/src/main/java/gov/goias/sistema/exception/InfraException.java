package gov.goias.sistema.exception;

/**
 * Created by kenta on 6/16/16.
 */
public class InfraException extends RuntimeException {

    public InfraException() {
    }

    public InfraException(String message) {
        super(message);
    }

    public InfraException(Throwable cause) {
        super(cause);
    }

    public InfraException(String message, Throwable cause) {
        super(message, cause);
    }
}

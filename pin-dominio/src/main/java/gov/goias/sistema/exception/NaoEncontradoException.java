package gov.goias.sistema.exception;

/**
 * Created by kenta on 6/16/16.
 */
public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException() {
    }

    public NaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaoEncontradoException(Throwable cause) {
        super(cause);
    }

    public NaoEncontradoException(String message) {
        super(message);
    }
}

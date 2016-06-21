package gov.goias.sistema.api.mappers;

import gov.goias.sistema.exception.NaoEncontradoException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NaoEncontradoExceptionMapper implements ExceptionMapper<NaoEncontradoException> {
	private static final Logger LOGGER = Logger.getLogger(NaoEncontradoExceptionMapper.class);

	@Override
	public Response toResponse(final NaoEncontradoException exception) {
		LOGGER.debug(exception);
		return Response.status(Response.Status.NOT_FOUND).entity(getErrorModel(exception)).build();
	}

	protected String getErrorModel(final NaoEncontradoException exception) {
		return exception.getMessage();
	}

	protected Response.Status getErrorStatus(final NaoEncontradoException exception) {
		return Response.Status.NOT_FOUND;
	}

}

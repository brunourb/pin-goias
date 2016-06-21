package gov.goias.sistema.api.mappers;

import gov.goias.sistema.exception.InfraException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InfraExceptionMapper implements ExceptionMapper<InfraException> {
	private static final Logger LOGGER = Logger.getLogger(InfraExceptionMapper.class);

	@Override
	public Response toResponse(final InfraException exception) {
		LOGGER.debug(exception);
		return Response.status(Response.Status.NOT_FOUND).entity(getErrorModel(exception)).build();
	}

	protected String getErrorModel(final InfraException exception) {
		return exception.getMessage();
	}

	protected Response.Status getErrorStatus(final InfraException exception) {
		return Response.Status.INTERNAL_SERVER_ERROR;
	}

}

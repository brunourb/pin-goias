package gov.goias.sistema.api.controllers;

import gov.goias.sistema.api.mappers.AlunoModelMapper;
import gov.goias.sistema.api.view.model.Aluno;
import gov.goias.sistema.exception.InfraException;
import gov.goias.sistema.negocio.AlunoService;
import io.swagger.annotations.*;
import javaslang.control.Try;
import jersey.repackaged.com.google.common.io.ByteStreams;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/aluno")
@Api(value = "Operações de CRUD em Aluno", description = "Operações CRUD de Aluno")
public class AlunoCmds {
    private static final Logger log = Logger.getLogger(AlunoCmds.class);

    final int tamanhoPagina = 10;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AlunoService alunoService;

    @Context
    private HttpServletRequest request;

    private DozerBeanMapper mapper = AlunoModelMapper.getMapper();

    /**
     * Armazena um Aluno no sistema.
     *
     * @param aluno Informações do aluno
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Armazena o registro do aluno.", notes = "Armazena o registro do aluno na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response salvar(Aluno aluno) {
        gov.goias.sistema.entidades.Aluno a = mapper.map(aluno, gov.goias.sistema.entidades.Aluno.class);
        alunoService.salvar(a);

        return Response.ok().build();
    }

    /**
     * Atualiza um Aluno no sistema.
     *
     * @param id Identificador do aluno
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Atualiza o registro do aluno.", notes = "Atualiza o registro do aluno a partir do ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response alterar(Aluno aluno, @PathParam("id") Integer id) {
        gov.goias.sistema.entidades.Aluno a = mapper.map(aluno, gov.goias.sistema.entidades.Aluno.class);
        a.setId(id);
        alunoService.alterar(a);

        return Response.ok().build();
    }

    /**
     * Remove um Aluno no sistema
     *
     * @param id Identificador do aluno
     */
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Exclui o aluno.", notes = "Exclui o aluno a partir do ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response excluir(@PathParam("id") Integer id) {
        alunoService.excluir(id);

        return Response.ok().build();
    }

    /**
     * Remove um Aluno no sistema
     *
     * @param id                  ID do aluno
     * @param uploadedInputStream Arquivo enviado
     * @param fileDetail          Detalhes do arquivo
     */
    @POST
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Upload de documentos do aluno.", notes = "Envio de documentos do aluno, como o RG, Declarações, etc.", extensions = {
            @Extension(name = "x-mask", properties = {
                    @ExtensionProperty(name = "file", value = "pdf, doc, jpg")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response enviarDocumento(
            @ApiParam(value = "ID do aluno", required = true) @PathParam("id") Integer id,
            @ApiParam(value = "Arquivo para upload", required = true) @FormDataParam("file") InputStream uploadedInputStream,
            @ApiParam(value = "Detalhes do arquivo", hidden = true) @FormDataParam("file") FormDataContentDisposition fileDetail) {

        alunoService.armazenaArquivo(id, fileDetail.getFileName(), uploadedInputStream);

        return Response.ok().build();
    }

    @GET
    @Path("/download")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    @ApiOperation(value = "Download do logo do governo de Goiás.", notes = "Download do logo do governo de Goiás", extensions = {
            @Extension(name = "x-mask", properties = {
                    @ExtensionProperty(name = "Response", value = "png, jpg, gif")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response download() {
        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                try {
                    InputStream is = getClass().getResourceAsStream("/logo_goias.png");
                    byte[] data = new byte[is.available()];
                    is.read(data);
                    output.write(data);
                    output.flush();
                } catch (Exception e) {
                    throw new InfraException(e);
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = logo_goias.png")
                .build();
    }

    /**
     * Operação para retorno de Erro de Acesso
     */
    @DELETE
    @Path("/noaccess")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Apresenta um erro de permissão ao requisitante.", notes = "Apresenta um erro de permissão.")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Não autorizado."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    @RolesAllowed(value = "usuarioSemPermissao")
    public Response semPermissao() {
        return Response.ok().build();
    }


}
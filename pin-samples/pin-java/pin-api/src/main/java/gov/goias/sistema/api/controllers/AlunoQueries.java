package gov.goias.sistema.api.controllers;

import gov.goias.sistema.api.mappers.AlunoModelMapper;
import gov.goias.sistema.api.view.model.Aluno;
import gov.goias.sistema.exception.NaoEncontradoException;
import gov.goias.sistema.negocio.AlunoService;
import io.swagger.annotations.*;
import org.dozer.DozerBeanMapper;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/aluno")
@Api(value = "Query em Aluno", description = "Operações consulta de Aluno")
public class AlunoQueries {

    @Context
    protected HttpServletRequest request;

    @Inject
    private AlunoService alunoService;

    private DozerBeanMapper mapper = AlunoModelMapper.getMapper();

    /**
     * Consulta a Aluno
     *
     * @param id Identificador do aluno
     * @return HTTP 200 - OK
     * Retorna Aluno
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtem o aluno.", notes = "Obtem o aluno a partir do ID.", extensions = {
            @Extension(name = "x-mask", properties = {
                    @ExtensionProperty(name = "nascimento", value = "dd/MM/yyyy"),
                    @ExtensionProperty(name = "id", value = "00000000")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = Aluno.class),
            @ApiResponse(code = 404, message = "Aluno não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response consultar(@PathParam("id") Integer id) {
        Aluno alunoVo = new Aluno();
        gov.goias.sistema.entidades.Aluno aluno = alunoService.consultar(id).orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado."));

        mapper.map(aluno, alunoVo);
        return Response.ok(alunoVo).build();
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtem uma lista de alunos.", notes = "Obtém uma lista de alunos paginada.", extensions = {
            @Extension(name = "x-mask", properties = {
                    @ExtensionProperty(name = "nascimento", value = "dd/MM/yyyy")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sem paginação (todos os dados foram retornados)."),
            @ApiResponse(code = 204, message = "Sem retorno de dados."),
            @ApiResponse(code = 206, message = "Existe paginação (a quantidade total é maior que o limit informado)."),
            @ApiResponse(code = 406, message = "Intervalo solicitado inválido."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public Response list(@QueryParam("filtroPesquisaNome") final String filtroPesquisaNome,
                         @QueryParam("offset") final Integer offset,
                         @QueryParam("limit") final Integer limit) {

        Integer total = alunoService.obtemQuantidadeTotalRegistros();
        Optional<List<gov.goias.sistema.entidades.Aluno>> listaAluno = alunoService.listarAlunos(offset, limit);

        List<Aluno> alunos = converteListaAluno(listaAluno.orElseGet(() -> new ArrayList<gov.goias.sistema.entidades.Aluno>()));

        Integer queryOffset = Optional.ofNullable(offset).orElseGet(() -> new Integer(0));

        Response.Status status = (alunos.size() == 0 ? Response.Status.NO_CONTENT : ((alunos.size() + queryOffset < total ? Response.Status.PARTIAL_CONTENT : Response.Status.OK)));

        return Response.status(status).header("Content-Range", formatResponsePageRange(queryOffset, queryOffset + listaAluno.get().size(), total)).entity(alunos).build();
    }

    private String formatResponsePageRange(Integer start, Integer end, Integer total) {
        return start + "-" + end + "/" + total;
    }

    private List<Aluno> converteListaAluno(List<gov.goias.sistema.entidades.Aluno> listaAluno) {
        List<Aluno> res = new ArrayList<Aluno>();

        listaAluno.forEach(a -> {
            Aluno alunoView = new Aluno();
            mapper.map(a, alunoView);
            res.add(alunoView);
        });

        return res;
    }

}
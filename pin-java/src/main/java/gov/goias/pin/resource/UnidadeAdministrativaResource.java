package gov.goias.pin.resource;

import gov.goias.pin.entity.UnidadeAdministrativa;
import gov.goias.pin.repository.UnidadeAdministrativaRepository;
import gov.goias.pin.service.UnidadeAdminstrativaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@RestController
@Api(value = "Operações de CRUD em Unidades Administrativas")
@RequestMapping(value = "/api/unidades", path = "/api/unidades")
public class UnidadeAdministrativaResource {

    @Autowired
    private UnidadeAdminstrativaService service;

    @Autowired
    private UnidadeAdministrativaRepository repository;

    @PostMapping
    @ApiResponse(code = 201, message = "Criado.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Armazena o registro da unidade.", notes = "Armazena o registro da unidade na base de dados.")
    public UnidadeAdministrativa create(@RequestBody UnidadeAdministrativa unidade,HttpServletResponse response) {
        log.trace("Criando Unidade Administrativa {}", unidade);
        unidade.setId(null);
        unidade = service.save(unidade);
        response.addHeader(HttpHeaders.LOCATION,"/api/unidades/" + unidade.getId());
        return unidade;
    }

    @PutMapping(path = "/{id}")
    @ApiResponse(code = 200, message = "Alterado.")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Altera o registro da unidade administrativa.", notes = "Altera o registro da unidade administrativa na base de dados.")
    public void update(@PathVariable Long id,  @RequestBody UnidadeAdministrativa unidade){
        log.trace("Alterando Unidade Administrativa {}", unidade);
        unidade.setId(id);
        service.save(unidade);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(code = 204, message = "Removido.")
    @ApiOperation(value = "Remove o registro da unidade administrativa.", notes = "Remove o registro da unidade administrativa na base de dados.")
    public void delete(@PathVariable Long id) {
        log.trace("Removendo Unidade Administrativa {}", id);
        service.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "OK.")
    @ApiOperation(value = "Listar todas as Unidades Administrativas em ordem alfabética.", notes = "Listar todas as Unidades Administrativas.")
    public List<UnidadeAdministrativa> list() {
        log.trace("Removendo Unidade Administrativa");
        return repository.findAll(new Sort(Sort.Direction.ASC, "nome"));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "OK.")
    @ApiOperation(value = "Retorna a unidade administrativa pelo seu Identificador.", notes = "Obter unidade administrativa por Identificador.")
    public UnidadeAdministrativa findById(@PathVariable  Long id) {
        log.trace("Buscando Unidade Administrativa por identificador {}", id);
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("UnidadeAdministrativa %d", id)));
    }

}
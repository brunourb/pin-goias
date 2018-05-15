package gov.goias.pin.resource;

import gov.goias.pin.entity.Colaborador;
import gov.goias.pin.repository.ColaboradorRepository;
import gov.goias.pin.service.ColaboradorService;
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
@Api(value = "Operações de CRUD em Colaborador")
@RequestMapping(value = "/api/colaboradores", path = "/api/colaboradores")
public class ColaboradorResource {

    @Autowired
    private ColaboradorService service;

    @Autowired
    private ColaboradorRepository repository;

    @PostMapping
    @ApiResponse(code = 201, message = "Criado.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Armazena o registro do colaborador.", notes = "Armazena o registro do colaborador na base de dados.")
    public Colaborador create(@RequestBody Colaborador colaborador,HttpServletResponse response) {
        log.trace("Criando colaborador {}", colaborador);
        colaborador.setId(null);
        colaborador = service.save(colaborador);
        response.addHeader(HttpHeaders.LOCATION,"/api/colaboradores/" + colaborador.getId());
        return colaborador;
    }

    @PutMapping(path = "/{id}")
    @ApiResponse(code = 200, message = "Alterado.")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Altera o registro do colaborador.", notes = "Altera o registro do colaborador na base de dados.")
    public void update(@PathVariable Long id,  @RequestBody Colaborador colaborador){
        log.trace("Alterando colaborador {}", colaborador);
        colaborador.setId(id);
        service.save(colaborador);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(code = 204, message = "Removido.")
    @ApiOperation(value = "Remove o registro do colaborador.", notes = "Remove o registro do colaborador na base de dados.")
    public void delete(@PathVariable Long id) {
        log.trace("Removendo colaborador {}", id);
        service.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "OK.")
    @ApiOperation(value = "Listar todos Colaboradores em ordem alfabética.", notes = "Listar todos Colaboradores.")
    public List<Colaborador> list() {
        log.trace("Listando todos o Colaboradores");
        return repository.findAll(new Sort(Sort.Direction.ASC, "nome"));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "OK.")
    @ApiOperation(value = "Retorna o colaborador pelo seu Identificador.", notes = "Obter colaborador por Identificador.")
    public Colaborador findById(@PathVariable  Long id) {
        log.trace("Buscando colaborador por identificador {}", id);
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Colaborador %d", id)));
    }

}
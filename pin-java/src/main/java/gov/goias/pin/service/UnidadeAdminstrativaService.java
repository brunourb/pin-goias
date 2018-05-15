package gov.goias.pin.service;

import gov.goias.pin.entity.UnidadeAdministrativa;
import gov.goias.pin.repository.UnidadeAdministrativaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(rollbackFor = Throwable.class)
public class UnidadeAdminstrativaService {

    @Autowired
    private UnidadeAdministrativaRepository repository;

    public UnidadeAdministrativa save(UnidadeAdministrativa colaborador){
        log.trace("Persistindo Unidade Administrativa");
        return repository.save(colaborador);
    }

    public void delete(Long id){
        log.trace("removendo Unidade Administrativa" + id);
        repository.deleteById(id);
    }

}
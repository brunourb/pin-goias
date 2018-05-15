package gov.goias.pin.service;

import gov.goias.pin.entity.Colaborador;
import gov.goias.pin.repository.ColaboradorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(rollbackFor = Throwable.class)
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository repository;

    public Colaborador save(Colaborador colaborador){
        log.trace("persistindo Colaborador");
        return repository.save(colaborador);
    }

    public void delete(Long id){
        log.trace("removendo Colaborador" + id);
        repository.deleteById(id);
    }

}
package gov.goias.pin.repository;

import gov.goias.pin.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repositório de Colaborador
 *
 * @see JpaRepository
 *
 */
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> , JpaSpecificationExecutor {
    Optional<Colaborador> findById(Long id);
}
package gov.goias.pin.repository;

import gov.goias.pin.entity.UnidadeAdministrativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repositório de Unidade Administrativa
 *
 * @see JpaRepository
 *
 */
public interface UnidadeAdministrativaRepository extends JpaRepository<UnidadeAdministrativa, Long> , JpaSpecificationExecutor {
    Optional<UnidadeAdministrativa> findById(Long id);
}
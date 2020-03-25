package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.Incidente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Incidente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long>, JpaSpecificationExecutor<Incidente> {

    @Query("select incidente from Incidente incidente where incidente.operador.login = ?#{principal.username}")
    List<Incidente> findByOperadorIsCurrentUser();
}

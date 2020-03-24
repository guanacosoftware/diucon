package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.HistorialEstado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the HistorialEstado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long>, JpaSpecificationExecutor<HistorialEstado> {

    @Query("select historialEstado from HistorialEstado historialEstado where historialEstado.usuario.login = ?#{principal.username}")
    List<HistorialEstado> findByUsuarioIsCurrentUser();
}

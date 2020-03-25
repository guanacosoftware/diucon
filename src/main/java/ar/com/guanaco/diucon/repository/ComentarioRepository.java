package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.Comentario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Comentario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>, JpaSpecificationExecutor<Comentario> {

    @Query("select comentario from Comentario comentario where comentario.usuario.login = ?#{principal.username}")
    List<Comentario> findByUsuarioIsCurrentUser();
}
